package com.thc.basespr.service.impl;

import com.thc.basespr.domain.Tbpopup;
import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbpopupDto;
import com.thc.basespr.exception.NoAuthorizationException;
import com.thc.basespr.exception.NoMatchingDataException;
import com.thc.basespr.mapper.TbpopupMapper;
import com.thc.basespr.repository.TbpopupRepository;
import com.thc.basespr.service.TbpopupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//2024-07-09 추가(클래스 처음 추가함)
@Service
public class TbpopupServiceImpl implements TbpopupService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbpopupRepository tbpopupRepository;
    private final TbpopupMapper tbpopupMapper;
    public TbpopupServiceImpl(
            TbpopupRepository tbpopupRepository
            , TbpopupMapper tbpopupMapper
    ) {
        this.tbpopupRepository = tbpopupRepository;
        this.tbpopupMapper = tbpopupMapper;
    }


    public TbpopupDto.CreateResDto sequence(TbpopupDto.SequenceServDto param){
        //권한 확인
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }

        Tbpopup tbpopup = tbpopupRepository.findById(param.getId()).orElseThrow(() -> new NoMatchingDataException(""));
        int nowSequence = tbpopup.getSequence(); // 현재 순번을 알아본다. // 2

        int targetSequence = nowSequence; // 2
        if("up".equals(param.getWay())){
            targetSequence++; // 3!!
        } else {
            targetSequence--; // 1
        }
        if(targetSequence == 0 || targetSequence > tbpopupMapper.pagedListCount(new TbpopupDto.PagedListServDto())){
            return null;
        } else {
            //잠시 순번에서 제외
            update(TbpopupDto.UpdateServDto.builder().id(tbpopup.getId()).sequence(-1).isAdmin(param.isAdmin()).build()); // 2-> -1
            //바꾸고자 하는 순번의 정보에 이전 순번 저장
            Tbpopup targetTbpopup = tbpopupRepository.findBySequence(targetSequence); // 현재 3번에 위치한 애 정보 가져오기
            update(TbpopupDto.UpdateServDto.builder().id(targetTbpopup.getId()).sequence(nowSequence).isAdmin(param.isAdmin()).build()); // 3번애 있는 애를 2번으로 업데이트
            //다시 수정하고 리턴
            return update(TbpopupDto.UpdateServDto.builder().id(tbpopup.getId()).sequence(targetSequence).isAdmin(param.isAdmin()).build()); //2번에 있는 애를 3번으로 업데이트
        }
    }

    /**/

    public TbpopupDto.CreateResDto create(TbpopupDto.CreateServDto param){
        //권한 확인
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }
        param.setSequence(tbpopupMapper.pagedListCount(new TbpopupDto.PagedListServDto()) + 1);
        return tbpopupRepository.save(param.toEntity()).toCreateResDto();
    }
    public TbpopupDto.CreateResDto update(TbpopupDto.UpdateServDto param){
        //권한 확인
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }
        Tbpopup tbpopup = tbpopupRepository.findById(param.getId()).orElseThrow(() -> new RuntimeException(""));
        if(param.getDeleted() != null) {
            tbpopup.setDeleted(param.getDeleted());
        }
        if(param.getSequence() != null){
            tbpopup.setSequence(param.getSequence());
        }
        if(param.getTitle() != null) {
            tbpopup.setTitle(param.getTitle());
        }
        if(param.getContent() != null) {
            tbpopup.setContent(param.getContent());
        }
        if(param.getImg() != null) {
            tbpopup.setImg(param.getImg());
        }
        return tbpopupRepository.save(tbpopup).toCreateResDto();
    }
    public TbpopupDto.CreateResDto delete(CommonDto.DeleteServDto param){
        TbpopupDto.UpdateServDto newParam = TbpopupDto.UpdateServDto.builder().id(param.getId()).deleted("Y").isAdmin(param.isAdmin()).build();
        return update(newParam);
    }
    public TbpopupDto.CreateResDto deletes(CommonDto.DeletesServDto param){
        int count = 0;
        for(String each : param.getIds()){
            TbpopupDto.UpdateServDto newParam = TbpopupDto.UpdateServDto.builder().id(each).deleted("Y").isAdmin(param.isAdmin()).build();
            TbpopupDto.CreateResDto result = update(newParam);
            if(!(result.getId()).isEmpty()) {
                count++;
            }
        }
        return TbpopupDto.CreateResDto.builder().id(count + "").build();
    }
    public TbpopupDto.SelectResDto detail(CommonDto.SelectServDto param){
        TbpopupDto.SelectResDto selectDto = tbpopupMapper.detail(param.getId());
        if(selectDto == null){ throw new NoMatchingDataException(""); }
        //날짜 표기 추가
        String createdAt = selectDto.getCreatedAt();
        selectDto.setCreatedAtOnlyDate(createdAt.substring(0, 19));
        return selectDto;
    }

    public List<TbpopupDto.SelectResDto> list(TbpopupDto.ListServDto param){
        List<TbpopupDto.SelectResDto> list = tbpopupMapper.list(param);
        return addListDetails(list, CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build());
    }

    public List<TbpopupDto.SelectResDto> moreList(TbpopupDto.MoreListServDto param){
        param.init();
        return addListDetails(tbpopupMapper.moreList(param), CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build());
    }

    public CommonDto.PagedListResDto<TbpopupDto.SelectResDto> pagedlist(TbpopupDto.PagedListServDto param){
        CommonDto.PagedListResDto<TbpopupDto.SelectResDto> returnDto = new CommonDto.PagedListResDto<>();
        TbpopupDto.PagedListServDto newParam = new TbpopupDto.PagedListServDto();
        newParam.init(tbpopupMapper.pagedListCount(param), param);
        return returnDto.init(addListDetails(tbpopupMapper.pagedList(newParam), CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build()), newParam);
    }

    public List<TbpopupDto.SelectResDto> addListDetails(List<TbpopupDto.SelectResDto> a_list, CommonDto.DetailServDto detailServDto){
        List<TbpopupDto.SelectResDto> result_list = new ArrayList<>();
        for(TbpopupDto.SelectResDto each : a_list){
            result_list.add(detail(CommonDto.SelectServDto.builder().id(each.getId()).reqTbuserId(detailServDto.getReqTbuserId()).isAdmin(detailServDto.isAdmin()).build()));
        }
        return result_list;
    }

}
