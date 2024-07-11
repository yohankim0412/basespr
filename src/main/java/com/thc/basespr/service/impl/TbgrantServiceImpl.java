package com.thc.basespr.service.impl;

import com.thc.basespr.domain.Tbgrant;
import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbgrantDto;
import com.thc.basespr.dto.TbgrantpartDto;
import com.thc.basespr.exception.NoAuthorizationException;
import com.thc.basespr.exception.NoMatchingDataException;
import com.thc.basespr.mapper.TbgrantMapper;
import com.thc.basespr.repository.TbgrantRepository;
import com.thc.basespr.service.TbgrantService;
import com.thc.basespr.service.TbgrantpartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//2024-07-08 추가(클래스 처음 추가함)
@Service
public class TbgrantServiceImpl implements TbgrantService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbgrantRepository tbgrantRepository;
    private final TbgrantMapper tbgrantMapper;
    private final TbgrantpartService tbgrantpartService;
    public TbgrantServiceImpl(
            TbgrantRepository tbgrantRepository
            , TbgrantMapper tbgrantMapper
            , TbgrantpartService tbgrantpartService
    ) {
        this.tbgrantRepository = tbgrantRepository;
        this.tbgrantMapper = tbgrantMapper;
        this.tbgrantpartService = tbgrantpartService;
    }

    public boolean access(TbgrantDto.AccessReqDto param){
        TbgrantDto.SelectResDto selectDto = tbgrantMapper.access(param);
        if(selectDto == null || selectDto.getId() == null){
            return false;
        }
        return true;
    }

    /**/

    public TbgrantDto.CreateResDto create(TbgrantDto.CreateServDto param){
        //권한 확인
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }
        return tbgrantRepository.save(param.toEntity()).toCreateResDto();
    }
    public TbgrantDto.CreateResDto update(TbgrantDto.UpdateServDto param){
        //권한 확인
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }
        Tbgrant tbgrant = tbgrantRepository.findById(param.getId()).orElseThrow(() -> new RuntimeException(""));
        if(param.getDeleted() != null) {
            tbgrant.setDeleted(param.getDeleted());
        }
        if(param.getTitle() != null) {
            tbgrant.setTitle(param.getTitle());
        }
        if(param.getCate() != null) {
            tbgrant.setCate(param.getCate());
        }
        if(param.getContent() != null) {
            tbgrant.setContent(param.getContent());
        }
        return tbgrantRepository.save(tbgrant).toCreateResDto();
    }
    public TbgrantDto.CreateResDto delete(CommonDto.DeleteServDto param){
        TbgrantDto.UpdateServDto newParam = TbgrantDto.UpdateServDto.builder().id(param.getId()).deleted("Y").reqTbuserId(param.getReqTbuserId()).build();
        return update(newParam);
    }
    public TbgrantDto.CreateResDto deletes(CommonDto.DeletesServDto param){
        int count = 0;
        for(String each : param.getIds()){
            TbgrantDto.UpdateServDto newParam = TbgrantDto.UpdateServDto.builder().id(each).deleted("Y").reqTbuserId(param.getReqTbuserId()).build();
            TbgrantDto.CreateResDto result = update(newParam);
            if(!(result.getId()).isEmpty()) {
                count++;
            }
        }
        return TbgrantDto.CreateResDto.builder().id(count + "").build();
    }
    public TbgrantDto.SelectResDto detail(CommonDto.SelectServDto param){
        //권한 확인
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }
        TbgrantDto.SelectResDto selectDto = tbgrantMapper.detail(param.getId());
        if(selectDto == null){ throw new NoMatchingDataException(""); }
        selectDto.setTbgrantparts(tbgrantpartService.list(TbgrantpartDto.ListServDto.builder().deleted("N").tbgrantId(selectDto.getId()).reqTbuserId(param.getReqTbuserId()).build()));
        return selectDto;
    }

    public List<TbgrantDto.SelectResDto> list(TbgrantDto.ListServDto param){
        List<TbgrantDto.SelectResDto> list = tbgrantMapper.list(param);
        return addListDetails(list, CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build());
    }

    public List<TbgrantDto.SelectResDto> moreList(TbgrantDto.MoreListServDto param){
        param.init();
        return addListDetails(tbgrantMapper.moreList(param), CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build());
    }

    public CommonDto.PagedListResDto<TbgrantDto.SelectResDto> pagedlist(TbgrantDto.PagedListServDto param){
        CommonDto.PagedListResDto<TbgrantDto.SelectResDto> returnDto = new CommonDto.PagedListResDto<>();
        TbgrantDto.PagedListServDto newParam = new TbgrantDto.PagedListServDto();
        newParam.init(tbgrantMapper.pagedListCount(param), param);
        return returnDto.init(addListDetails(tbgrantMapper.pagedList(newParam), CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build()), newParam);
    }

    public List<TbgrantDto.SelectResDto> addListDetails(List<TbgrantDto.SelectResDto> a_list, CommonDto.DetailServDto detailServDto){
        List<TbgrantDto.SelectResDto> result_list = new ArrayList<>();
        for(TbgrantDto.SelectResDto each : a_list){
            result_list.add(detail(CommonDto.SelectServDto.builder().id(each.getId()).reqTbuserId(detailServDto.getReqTbuserId()).isAdmin(detailServDto.isAdmin()).build()));
        }
        return result_list;
    }

}
