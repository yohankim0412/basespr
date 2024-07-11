package com.thc.basespr.service.impl;

import com.thc.basespr.domain.Tbgrantuser;
import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbgrantDto;
import com.thc.basespr.dto.TbgrantuserDto;
import com.thc.basespr.exception.NoAuthorizationException;
import com.thc.basespr.exception.NoMatchingDataException;
import com.thc.basespr.mapper.TbgrantuserMapper;
import com.thc.basespr.repository.TbgrantuserRepository;
import com.thc.basespr.service.TbgrantuserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//2024-07-09 추가(클래스 처음 추가함)
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

    /**/

    public TbgrantuserDto.CreateResDto create(TbgrantuserDto.CreateServDto param){
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }
        return tbgrantuserRepository.save(param.toEntity()).toCreateResDto();
    }
    public TbgrantuserDto.CreateResDto update(TbgrantuserDto.UpdateServDto param){
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }
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
    public TbgrantuserDto.CreateResDto delete(CommonDto.DeleteServDto param){
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }
        TbgrantuserDto.UpdateServDto newParam = TbgrantuserDto.UpdateServDto.builder().id(param.getId()).deleted("Y").isAdmin(param.isAdmin()).reqTbuserId(param.getReqTbuserId()).build();
        return update(newParam);
    }
    public TbgrantuserDto.CreateResDto deletes(CommonDto.DeletesServDto param){
        int count = 0;
        for(String each : param.getIds()){
            TbgrantuserDto.UpdateServDto newParam = TbgrantuserDto.UpdateServDto.builder().id(each).deleted("Y").isAdmin(param.isAdmin()).reqTbuserId(param.getReqTbuserId()).build();
            TbgrantuserDto.CreateResDto result = update(newParam);
            if(!(result.getId()).isEmpty()) {
                count++;
            }
        }
        return TbgrantuserDto.CreateResDto.builder().id(count + "").build();
    }
    public TbgrantuserDto.SelectResDto detail(CommonDto.SelectServDto param){
        TbgrantuserDto.SelectResDto selectDto = tbgrantuserMapper.detail(param.getId());
        if(selectDto == null){ throw new NoMatchingDataException(""); }
        //날짜 표기 추가
        String createdAt = selectDto.getCreatedAt();
        selectDto.setCreatedAtOnlyDate(createdAt.substring(0, 19));
        return selectDto;
    }

    public List<TbgrantuserDto.SelectResDto> list(TbgrantuserDto.ListServDto param){
        List<TbgrantuserDto.SelectResDto> list = tbgrantuserMapper.list(param);
        return addListDetails(list);
    }

    public List<TbgrantuserDto.SelectResDto> moreList(TbgrantuserDto.MoreListServDto param){
        param.init();
        return addListDetails(tbgrantuserMapper.moreList(param));
    }

    public CommonDto.PagedListResDto<TbgrantuserDto.SelectResDto> pagedlist(TbgrantuserDto.PagedListServDto param){
        CommonDto.PagedListResDto<TbgrantuserDto.SelectResDto> returnDto = new CommonDto.PagedListResDto<>();
        TbgrantuserDto.PagedListServDto newParam = new TbgrantuserDto.PagedListServDto();
        newParam.init(tbgrantuserMapper.pagedListCount(param), param);
        return returnDto.init(addListDetails(tbgrantuserMapper.pagedList(newParam)), newParam);
    }

    public List<TbgrantuserDto.SelectResDto> addListDetails(List<TbgrantuserDto.SelectResDto> a_list){
        List<TbgrantuserDto.SelectResDto> result_list = new ArrayList<>();
        for(TbgrantuserDto.SelectResDto each : a_list){
            result_list.add(detail(CommonDto.SelectServDto.builder().id(each.getId()).build()));
        }
        return result_list;
    }

}
