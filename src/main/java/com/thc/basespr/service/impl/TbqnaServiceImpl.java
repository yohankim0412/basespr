package com.thc.basespr.service.impl;

import com.thc.basespr.domain.Tbqna;
import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbqnaDto;
import com.thc.basespr.dto.TbqnafileDto;
import com.thc.basespr.exception.NoAuthorizationException;
import com.thc.basespr.exception.NoMatchingDataException;
import com.thc.basespr.mapper.TbqnaMapper;
import com.thc.basespr.repository.TbqnaRepository;
import com.thc.basespr.service.TbqnaService;
import com.thc.basespr.service.TbqnafileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//2024-07-08 추가(클래스 처음 추가함)
@Service
public class TbqnaServiceImpl implements TbqnaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbqnaRepository tbqnaRepository;
    private final TbqnaMapper tbqnaMapper;
    private final TbqnafileService tbqnafileService;
    public TbqnaServiceImpl(
            TbqnaRepository tbqnaRepository
            , TbqnaMapper tbqnaMapper
            , TbqnafileService tbqnafileService
    ) {
        this.tbqnaRepository = tbqnaRepository;
        this.tbqnaMapper = tbqnaMapper;
        this.tbqnafileService = tbqnafileService;
    }

    /**/

    public TbqnaDto.CreateResDto create(TbqnaDto.CreateServDto param){
        Tbqna tbqna = tbqnaRepository.save(param.toEntity());
        
        //첨부파일 등록
        String[] arrayCate = param.getCates();
        String[] arrayUrl = param.getUrls();
        for(int i=0;i<arrayCate.length;i++){
            TbqnafileDto.CreateServDto createServDto = TbqnafileDto.CreateServDto.builder()
                    .tbqnaId(tbqna.getId())
                    .cate(arrayCate[i])
                    .url(arrayUrl[i])
                    .build();
            tbqnafileService.create(createServDto);
        }
        return tbqna.toCreateResDto();
    }
    public TbqnaDto.CreateResDto update(TbqnaDto.UpdateServDto param){
        //권한 확인
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }
        Tbqna tbqna = tbqnaRepository.findById(param.getId()).orElseThrow(() -> new NoMatchingDataException(""));
        if(param.getDeleted() != null) {
            tbqna.setDeleted(param.getDeleted());
        }
        if(param.getProcess() != null) {
            tbqna.setProcess(param.getProcess());
        }
        if(param.getTitle() != null) {
            tbqna.setTitle(param.getTitle());
        }
        if(param.getContent() != null) {
            tbqna.setContent(param.getContent());
        }
        if(param.getAnswer() != null) {
            tbqna.setAnswer(param.getAnswer());
        }
        return tbqnaRepository.save(tbqna).toCreateResDto();
    }
    public TbqnaDto.CreateResDto delete(CommonDto.DeleteServDto param){
        TbqnaDto.UpdateServDto newParam = TbqnaDto.UpdateServDto.builder().id(param.getId()).deleted("Y").isAdmin(param.isAdmin()).reqTbuserId(param.getReqTbuserId()).build();
        return update(newParam);
    }
    public TbqnaDto.CreateResDto deletes(CommonDto.DeletesServDto param){
        int count = 0;
        for(String each : param.getIds()){
            TbqnaDto.UpdateServDto newParam = TbqnaDto.UpdateServDto.builder().id(each).deleted("Y").isAdmin(param.isAdmin()).reqTbuserId(param.getReqTbuserId()).build();
            TbqnaDto.CreateResDto result = update(newParam);
            if(!(result.getId()).isEmpty()) {
                count++;
            }
        }
        return TbqnaDto.CreateResDto.builder().id(count + "").build();
    }
    public TbqnaDto.SelectResDto detail(CommonDto.SelectServDto param){
        TbqnaDto.SelectResDto selectDto = tbqnaMapper.detail(param.getId());
        if(selectDto == null){ throw new NoMatchingDataException(""); }
        //권한 확인
        if(!param.isAdmin() && !(param.getReqTbuserId()).equals(selectDto.getTbuserId())){ throw new NoAuthorizationException(""); }

        //날짜 표기 추가
        String createdAt = selectDto.getCreatedAt();
        selectDto.setCreatedAtOnlyDate(createdAt.substring(0, 19));

        //파일 리스트 추가
        TbqnafileDto.ListServDto listServDto = TbqnafileDto.ListServDto.builder().tbqnaId(selectDto.getId()).deleted("N").build();
        List<TbqnafileDto.SelectResDto> files = tbqnafileService.list(listServDto);
        selectDto.setFiles(files);

        return selectDto;
    }

    public List<TbqnaDto.SelectResDto> list(TbqnaDto.ListServDto param){
        List<TbqnaDto.SelectResDto> list = tbqnaMapper.list(param);
        return addListDetails(list, CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build());
    }

    public List<TbqnaDto.SelectResDto> moreList(TbqnaDto.MoreListServDto param){
        //내가 쓴 글 조희
        if("my".equals(param.getTbuserId())){
            param.setTbuserId(param.getReqTbuserId());
        }

        param.init();
        return addListDetails(tbqnaMapper.moreList(param), CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build());
    }

    public CommonDto.PagedListResDto<TbqnaDto.SelectResDto> pagedlist(TbqnaDto.PagedListServDto param){
        CommonDto.PagedListResDto<TbqnaDto.SelectResDto> returnDto = new CommonDto.PagedListResDto<>();
        TbqnaDto.PagedListServDto newParam = new TbqnaDto.PagedListServDto();
        newParam.init(tbqnaMapper.pagedListCount(param), param);
        return returnDto.init(addListDetails(tbqnaMapper.pagedList(newParam), CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build()), newParam);
    }

    public List<TbqnaDto.SelectResDto> addListDetails(List<TbqnaDto.SelectResDto> a_list, CommonDto.DetailServDto detailServDto){
        List<TbqnaDto.SelectResDto> result_list = new ArrayList<>();
        for(TbqnaDto.SelectResDto each : a_list){
            result_list.add(detail(CommonDto.SelectServDto.builder().id(each.getId()).reqTbuserId(detailServDto.getReqTbuserId()).isAdmin(detailServDto.isAdmin()).build()));
        }
        return result_list;
    }

}
