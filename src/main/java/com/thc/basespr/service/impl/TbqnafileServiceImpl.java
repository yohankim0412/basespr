package com.thc.basespr.service.impl;

import com.thc.basespr.domain.Tbqnafile;
import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbqnafileDto;
import com.thc.basespr.exception.NoAuthorizationException;
import com.thc.basespr.exception.NoMatchingDataException;
import com.thc.basespr.mapper.TbqnafileMapper;
import com.thc.basespr.repository.TbqnafileRepository;
import com.thc.basespr.service.TbqnafileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//2024-07-08 추가(클래스 처음 추가함)
@Service
public class TbqnafileServiceImpl implements TbqnafileService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbqnafileRepository tbqnafileRepository;
    private final TbqnafileMapper tbqnafileMapper;
    public TbqnafileServiceImpl(
            TbqnafileRepository tbqnafileRepository
            , TbqnafileMapper tbqnafileMapper
    ) {
        this.tbqnafileRepository = tbqnafileRepository;
        this.tbqnafileMapper = tbqnafileMapper;
    }

    /**/

    public TbqnafileDto.CreateResDto create(TbqnafileDto.CreateServDto param){
        return tbqnafileRepository.save(param.toEntity()).toCreateResDto();
    }
    public TbqnafileDto.CreateResDto update(TbqnafileDto.UpdateServDto param){
        Tbqnafile tbqnafile = tbqnafileRepository.findById(param.getId()).orElseThrow(() -> new RuntimeException(""));
        if(param.getDeleted() != null) {
            tbqnafile.setDeleted(param.getDeleted());
        }
        if(param.getProcess() != null) {
            tbqnafile.setProcess(param.getProcess());
        }
        if(param.getCate() != null) {
            tbqnafile.setCate(param.getCate());
        }
        if(param.getUrl() != null) {
            tbqnafile.setUrl(param.getUrl());
        }
        return tbqnafileRepository.save(tbqnafile).toCreateResDto();
    }
    public TbqnafileDto.CreateResDto delete(CommonDto.DeleteServDto param){
        TbqnafileDto.UpdateServDto newParam = TbqnafileDto.UpdateServDto.builder().id(param.getId()).deleted("Y").isAdmin(param.isAdmin()).reqTbuserId(param.getReqTbuserId()).build();
        return update(newParam);
    }
    public TbqnafileDto.CreateResDto deletes(CommonDto.DeletesServDto param){
        int count = 0;
        for(String each : param.getIds()){
            TbqnafileDto.UpdateServDto newParam = TbqnafileDto.UpdateServDto.builder().id(each).deleted("Y").isAdmin(param.isAdmin()).reqTbuserId(param.getReqTbuserId()).build();
            TbqnafileDto.CreateResDto result = update(newParam);
            if(!(result.getId()).isEmpty()) {
                count++;
            }
        }
        return TbqnafileDto.CreateResDto.builder().id(count + "").build();
    }
    public TbqnafileDto.SelectResDto detail(CommonDto.SelectServDto param){
        TbqnafileDto.SelectResDto selectDto = tbqnafileMapper.detail(param.getId());
        if(selectDto == null){ throw new NoMatchingDataException(""); }
        return selectDto;
    }

    public List<TbqnafileDto.SelectResDto> list(TbqnafileDto.ListServDto param){
        List<TbqnafileDto.SelectResDto> list = tbqnafileMapper.list(param);
        return addListDetails(list, CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build());
    }

    public List<TbqnafileDto.SelectResDto> moreList(TbqnafileDto.MoreListServDto param){
        param.init();
        return addListDetails(tbqnafileMapper.moreList(param), CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build());
    }

    public CommonDto.PagedListResDto<TbqnafileDto.SelectResDto> pagedlist(TbqnafileDto.PagedListServDto param){
        CommonDto.PagedListResDto<TbqnafileDto.SelectResDto> returnDto = new CommonDto.PagedListResDto<>();
        TbqnafileDto.PagedListServDto newParam = new TbqnafileDto.PagedListServDto();
        newParam.init(tbqnafileMapper.pagedListCount(param), param);
        return returnDto.init(addListDetails(tbqnafileMapper.pagedList(newParam), CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build()), newParam);
    }

    public List<TbqnafileDto.SelectResDto> addListDetails(List<TbqnafileDto.SelectResDto> a_list, CommonDto.DetailServDto detailServDto){
        List<TbqnafileDto.SelectResDto> result_list = new ArrayList<>();
        for(TbqnafileDto.SelectResDto each : a_list){
            result_list.add(detail(CommonDto.SelectServDto.builder().id(each.getId()).reqTbuserId(detailServDto.getReqTbuserId()).isAdmin(detailServDto.isAdmin()).build()));
        }
        return result_list;
    }

}
