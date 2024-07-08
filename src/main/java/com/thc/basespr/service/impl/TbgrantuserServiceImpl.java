package com.thc.basespr.service.impl;

import com.thc.basespr.domain.Tbgrantuser;
import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbgrantuserDto;
import com.thc.basespr.mapper.TbgrantuserMapper;
import com.thc.basespr.repository.TbgrantuserRepository;
import com.thc.basespr.service.TbgrantuserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//2024-07-08 추가(클래스 처음 추가함)
@Service
public class TbgrantuserServiceImpl implements TbgrantuserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbgrantuserRepository tbgrantuserRepository;
    private final TbgrantuserMapper tbgrantuserMapper;
    public TbgrantuserServiceImpl(
            TbgrantuserRepository tbgrantuserRepository
            ,TbgrantuserMapper tbgrantuserMapper
    ) {
        this.tbgrantuserRepository = tbgrantuserRepository;
        this.tbgrantuserMapper = tbgrantuserMapper;
    }

    public TbgrantuserDto.CreateResDto create(TbgrantuserDto.CreateReqDto param){
        return tbgrantuserRepository.save(param.toEntity()).toCreateResDto();
    }
    public TbgrantuserDto.CreateResDto update(TbgrantuserDto.UpdateReqDto param){
        System.out.println(param);
        Tbgrantuser tbgrantuser = tbgrantuserRepository.findById(param.getId()).orElseThrow(() -> new RuntimeException(""));
        if(param.getDeleted() != null) {
            tbgrantuser.setDeleted(param.getDeleted());
        }
        if(param.getTbgrantId() != null) {
            tbgrantuser.setTbgrantId(param.getTbgrantId());
        }
        if(param.getTbuserId() != null) {
            tbgrantuser.setTbuserId(param.getTbuserId());
        }
        return tbgrantuserRepository.save(tbgrantuser).toCreateResDto();
    }
    public TbgrantuserDto.SelectResDto detail(CommonDto.SelectServDto param){
        TbgrantuserDto.SelectResDto selectDto = tbgrantuserMapper.detail(param.getId());
        return selectDto;
    }

    public List<TbgrantuserDto.SelectResDto> list(TbgrantuserDto.ListReqDto param){
        logger.info("reqTbuserId : " + ((TbgrantuserDto.ListServDto) param).getReqTbuserId());
        List<TbgrantuserDto.SelectResDto> list = tbgrantuserMapper.list(param);
        return addListDetails(list);
    }

    public List<TbgrantuserDto.SelectResDto> moreList(TbgrantuserDto.MoreListReqDto param){
        param.afterBuild();
        logger.info(param.getCursor());
        return addListDetails(tbgrantuserMapper.moreList(param));
    }

    public CommonDto.PagedListResDto<TbgrantuserDto.SelectResDto> pagedlist(TbgrantuserDto.PagedListReqDto param){
        CommonDto.PagedListResDto<TbgrantuserDto.SelectResDto> returnDto = new CommonDto.PagedListResDto<>();
        TbgrantuserDto.PagedListServDto newParam = new TbgrantuserDto.PagedListServDto();
        newParam.afterBuild(tbgrantuserMapper.pagedListCount(param), param);
        return returnDto.afterBuild(addListDetails(tbgrantuserMapper.pagedList(newParam)), newParam);
    }

    public List<TbgrantuserDto.SelectResDto> addListDetails(List<TbgrantuserDto.SelectResDto> a_list){
        List<TbgrantuserDto.SelectResDto> result_list = new ArrayList<>();
        for(TbgrantuserDto.SelectResDto each : a_list){
            result_list.add(detail(CommonDto.SelectServDto.builder().id(each.getId()).build()));
        }
        return result_list;
    }

}
