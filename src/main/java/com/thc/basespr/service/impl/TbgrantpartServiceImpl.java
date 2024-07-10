package com.thc.basespr.service.impl;

import com.thc.basespr.domain.Tbgrantpart;
import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbgrantDto;
import com.thc.basespr.dto.TbgrantpartDto;
import com.thc.basespr.mapper.TbgrantpartMapper;
import com.thc.basespr.repository.TbgrantpartRepository;
import com.thc.basespr.service.TbgrantpartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//2024-07-09 추가(클래스 처음 추가함)
@Service
public class TbgrantpartServiceImpl implements TbgrantpartService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbgrantpartRepository tbgrantpartRepository;
    private final TbgrantpartMapper tbgrantpartMapper;
    public TbgrantpartServiceImpl(
            TbgrantpartRepository tbgrantpartRepository
            ,TbgrantpartMapper tbgrantpartMapper
    ) {
        this.tbgrantpartRepository = tbgrantpartRepository;
        this.tbgrantpartMapper = tbgrantpartMapper;
    }

    public TbgrantpartDto.CreateResDto toggle(TbgrantpartDto.ToggleServDto params){
        TbgrantpartDto.CreateResDto returnVal = null;
        Tbgrantpart tbgrantpart = tbgrantpartRepository.findByTbgrantIdAndCateAndContent(params.getTbgrantId(), params.getCate(), params.getContent());
        if(params.isWay()){
            if(tbgrantpart == null){
                returnVal = tbgrantpartRepository.save(params.toEntity()).toCreateResDto();
            } else {
                returnVal = tbgrantpart.toCreateResDto();
            }
        } else {
            if(tbgrantpart == null){
            } else {
                CommonDto.DeleteServDto deleteParam = CommonDto.DeleteServDto.builder().id(tbgrantpart.getId()).reqTbuserId(params.getReqTbuserId()).build();
                delete(deleteParam);
                returnVal = tbgrantpart.toCreateResDto();
            }

        }
        return returnVal;
    }

    /**/

    public TbgrantpartDto.CreateResDto create(TbgrantpartDto.CreateServDto param){
        return tbgrantpartRepository.save(param.toEntity()).toCreateResDto();
    }
    public TbgrantpartDto.CreateResDto update(TbgrantpartDto.UpdateServDto param){
        System.out.println(param);
        Tbgrantpart tbgrantpart = tbgrantpartRepository.findById(param.getId()).orElseThrow(() -> new RuntimeException(""));
        if(param.getDeleted() != null) {
            tbgrantpart.setDeleted(param.getDeleted());
        }
        if(param.getTbgrantId() != null) {
            tbgrantpart.setTbgrantId(param.getTbgrantId());
        }
        if(param.getCate() != null) {
            tbgrantpart.setCate(param.getCate());
        }
        if(param.getContent() != null) {
            tbgrantpart.setContent(param.getContent());
        }
        return tbgrantpartRepository.save(tbgrantpart).toCreateResDto();
    }
    public TbgrantpartDto.CreateResDto delete(CommonDto.DeleteServDto param){
        TbgrantpartDto.UpdateServDto newParam = TbgrantpartDto.UpdateServDto.builder().id(param.getId()).deleted("Y").isAdmin(param.isAdmin()).reqTbuserId(param.getReqTbuserId()).build();
        return update(newParam);
    }
    public TbgrantpartDto.CreateResDto deletes(CommonDto.DeletesServDto param){
        int count = 0;
        for(String each : param.getIds()){
            TbgrantpartDto.UpdateServDto newParam = TbgrantpartDto.UpdateServDto.builder().id(each).deleted("Y").isAdmin(param.isAdmin()).reqTbuserId(param.getReqTbuserId()).build();
            TbgrantpartDto.CreateResDto result = update(newParam);
            if(!(result.getId()).isEmpty()) {
                count++;
            }
        }
        return TbgrantpartDto.CreateResDto.builder().id(count + "").build();
    }

    public TbgrantpartDto.SelectResDto detail(CommonDto.SelectServDto param){
        TbgrantpartDto.SelectResDto selectDto = tbgrantpartMapper.detail(param.getId());
        return selectDto;
    }

    public List<TbgrantpartDto.SelectResDto> list(TbgrantpartDto.ListServDto param){
        List<TbgrantpartDto.SelectResDto> list = tbgrantpartMapper.list(param);
        return addListDetails(list);
    }

    public List<TbgrantpartDto.SelectResDto> moreList(TbgrantpartDto.MoreListServDto param){
        param.init();
        return addListDetails(tbgrantpartMapper.moreList(param));
    }

    public CommonDto.PagedListResDto<TbgrantpartDto.SelectResDto> pagedlist(TbgrantpartDto.PagedListServDto param){
        CommonDto.PagedListResDto<TbgrantpartDto.SelectResDto> returnDto = new CommonDto.PagedListResDto<>();
        TbgrantpartDto.PagedListServDto newParam = new TbgrantpartDto.PagedListServDto();
        newParam.init(tbgrantpartMapper.pagedListCount(param), param);
        return returnDto.init(addListDetails(tbgrantpartMapper.pagedList(newParam)), newParam);
    }

    public List<TbgrantpartDto.SelectResDto> addListDetails(List<TbgrantpartDto.SelectResDto> a_list){
        List<TbgrantpartDto.SelectResDto> result_list = new ArrayList<>();
        for(TbgrantpartDto.SelectResDto each : a_list){
            result_list.add(detail(CommonDto.SelectServDto.builder().id(each.getId()).build()));
        }
        return result_list;
    }

}
