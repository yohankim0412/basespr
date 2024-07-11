package com.thc.basespr.service.impl;

import com.thc.basespr.domain.Tbnotice;
import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbnoticeDto;
import com.thc.basespr.exception.NoAuthorizationException;
import com.thc.basespr.exception.NoMatchingDataException;
import com.thc.basespr.mapper.TbnoticeMapper;
import com.thc.basespr.repository.TbnoticeRepository;
import com.thc.basespr.service.TbnoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//2024-07-08 추가(클래스 처음 추가함)
@Service
public class TbnoticeServiceImpl implements TbnoticeService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbnoticeRepository tbnoticeRepository;
    private final TbnoticeMapper tbnoticeMapper;
    public TbnoticeServiceImpl(
            TbnoticeRepository tbnoticeRepository
            , TbnoticeMapper tbnoticeMapper
    ) {
        this.tbnoticeRepository = tbnoticeRepository;
        this.tbnoticeMapper = tbnoticeMapper;
    }

    /**/

    public TbnoticeDto.CreateResDto create(TbnoticeDto.CreateServDto param){
        //권한 확인
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }
        return tbnoticeRepository.save(param.toEntity()).toCreateResDto();
    }
    public TbnoticeDto.CreateResDto update(TbnoticeDto.UpdateServDto param){
        //권한 확인
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }
        Tbnotice tbnotice = tbnoticeRepository.findById(param.getId()).orElseThrow(() -> new RuntimeException(""));
        if(param.getDeleted() != null) {
            tbnotice.setDeleted(param.getDeleted());
        }
        if(param.getTitle() != null) {
            tbnotice.setTitle(param.getTitle());
        }
        if(param.getCate() != null) {
            tbnotice.setCate(param.getCate());
        }
        if(param.getContent() != null) {
            tbnotice.setContent(param.getContent());
        }
        return tbnoticeRepository.save(tbnotice).toCreateResDto();
    }
    public TbnoticeDto.CreateResDto delete(CommonDto.DeleteServDto param){
        TbnoticeDto.UpdateServDto newParam = TbnoticeDto.UpdateServDto.builder().id(param.getId()).deleted("Y").isAdmin(param.isAdmin()).reqTbuserId(param.getReqTbuserId()).build();
        return update(newParam);
    }
    public TbnoticeDto.CreateResDto deletes(CommonDto.DeletesServDto param){
        int count = 0;
        for(String each : param.getIds()){
            TbnoticeDto.UpdateServDto newParam = TbnoticeDto.UpdateServDto.builder().id(each).deleted("Y").isAdmin(param.isAdmin()).reqTbuserId(param.getReqTbuserId()).build();
            TbnoticeDto.CreateResDto result = update(newParam);
            if(!(result.getId()).isEmpty()) {
                count++;
            }
        }
        return TbnoticeDto.CreateResDto.builder().id(count + "").build();
    }
    public TbnoticeDto.SelectResDto detail(CommonDto.SelectServDto param){
        //권한 확인
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }
        TbnoticeDto.SelectResDto selectDto = tbnoticeMapper.detail(param.getId());
        if(selectDto == null){ throw new NoMatchingDataException(""); }
        //날짜 표기 추가
        String createdAt = selectDto.getCreatedAt();
        selectDto.setCreatedAtOnlyDate(createdAt.substring(0, 19));
        return selectDto;
    }

    public List<TbnoticeDto.SelectResDto> list(TbnoticeDto.ListServDto param){
        List<TbnoticeDto.SelectResDto> list = tbnoticeMapper.list(param);
        return addListDetails(list, CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build());
    }

    public List<TbnoticeDto.SelectResDto> moreList(TbnoticeDto.MoreListServDto param){
        param.init();
        return addListDetails(tbnoticeMapper.moreList(param), CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build());
    }

    public CommonDto.PagedListResDto<TbnoticeDto.SelectResDto> pagedlist(TbnoticeDto.PagedListServDto param){
        CommonDto.PagedListResDto<TbnoticeDto.SelectResDto> returnDto = new CommonDto.PagedListResDto<>();
        TbnoticeDto.PagedListServDto newParam = new TbnoticeDto.PagedListServDto();
        newParam.init(tbnoticeMapper.pagedListCount(param), param);
        return returnDto.init(addListDetails(tbnoticeMapper.pagedList(newParam), CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build()), newParam);
    }

    public List<TbnoticeDto.SelectResDto> addListDetails(List<TbnoticeDto.SelectResDto> a_list, CommonDto.DetailServDto detailServDto){
        List<TbnoticeDto.SelectResDto> result_list = new ArrayList<>();
        for(TbnoticeDto.SelectResDto each : a_list){
            result_list.add(detail(CommonDto.SelectServDto.builder().id(each.getId()).reqTbuserId(detailServDto.getReqTbuserId()).isAdmin(detailServDto.isAdmin()).build()));
        }
        return result_list;
    }

}
