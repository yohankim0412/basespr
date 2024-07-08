package com.thc.basespr.service.impl;

import com.thc.basespr.domain.Tbgrant;
import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbgrantDto;
import com.thc.basespr.mapper.TbgrantMapper;
import com.thc.basespr.repository.TbgrantRepository;
import com.thc.basespr.service.TbgrantService;
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
    public TbgrantServiceImpl(
            TbgrantRepository tbgrantRepository
            ,TbgrantMapper tbgrantMapper
    ) {
        this.tbgrantRepository = tbgrantRepository;
        this.tbgrantMapper = tbgrantMapper;
    }

    public TbgrantDto.CreateResDto create(TbgrantDto.CreateReqDto param){
        return tbgrantRepository.save(param.toEntity()).toCreateResDto();
    }
    public TbgrantDto.CreateResDto update(TbgrantDto.UpdateReqDto param){
        System.out.println(param);
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
    public TbgrantDto.SelectResDto detail(CommonDto.SelectServDto param){
        TbgrantDto.SelectResDto selectDto = tbgrantMapper.detail(param.getId());
        return selectDto;
    }

    public List<TbgrantDto.SelectResDto> list(TbgrantDto.ListReqDto param){
        logger.info("reqTbuserId : " + ((TbgrantDto.ListServDto) param).getReqTbuserId());
        List<TbgrantDto.SelectResDto> list = tbgrantMapper.list(param);
        return addListDetails(list);
    }

    public List<TbgrantDto.SelectResDto> moreList(TbgrantDto.MoreListReqDto param){
        param.afterBuild();
        logger.info(param.getCursor());
        return addListDetails(tbgrantMapper.moreList(param));
    }

    public CommonDto.PagedListResDto<TbgrantDto.SelectResDto> pagedlist(TbgrantDto.PagedListReqDto param){
        CommonDto.PagedListResDto<TbgrantDto.SelectResDto> returnDto = new CommonDto.PagedListResDto<>();
        TbgrantDto.PagedListServDto newParam = new TbgrantDto.PagedListServDto();
        newParam.afterBuild(tbgrantMapper.pagedListCount(param), param);
        return returnDto.afterBuild(addListDetails(tbgrantMapper.pagedList(newParam)), newParam);
    }

    public List<TbgrantDto.SelectResDto> addListDetails(List<TbgrantDto.SelectResDto> a_list){
        List<TbgrantDto.SelectResDto> result_list = new ArrayList<>();
        for(TbgrantDto.SelectResDto each : a_list){
            result_list.add(detail(CommonDto.SelectServDto.builder().id(each.getId()).build()));
        }
        return result_list;
    }

}
