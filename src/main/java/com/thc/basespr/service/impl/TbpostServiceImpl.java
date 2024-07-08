package com.thc.basespr.service.impl;

import com.thc.basespr.domain.Tbpost;
import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbpostDto;
import com.thc.basespr.mapper.TbpostMapper;
import com.thc.basespr.repository.TbpostRepository;
import com.thc.basespr.service.TbpostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TbpostServiceImpl implements TbpostService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbpostRepository tbpostRepository;
    private final TbpostMapper tbpostMapper;
    public TbpostServiceImpl(
            TbpostRepository tbpostRepository
            ,TbpostMapper tbpostMapper
    ) {
        this.tbpostRepository = tbpostRepository;
        this.tbpostMapper = tbpostMapper;
    }

    public TbpostDto.CreateResDto create(TbpostDto.CreateReqDto param){
        return tbpostRepository.save(param.toEntity()).toCreateResDto();
    }
    public TbpostDto.CreateResDto update(TbpostDto.UpdateReqDto param){
        System.out.println(param);
        Tbpost tbpost = tbpostRepository.findById(param.getId()).orElseThrow(() -> new RuntimeException(""));
        if(param.getDeleted() != null) {
            tbpost.setDeleted(param.getDeleted());
        }
        if(param.getTitle() != null) {
            tbpost.setTitle(param.getTitle());
        }
        if(param.getCate() != null) {
            tbpost.setCate(param.getCate());
        }
        if(param.getImg() != null) {
            tbpost.setImg(param.getImg());
        }
        if(param.getContent() != null) {
            tbpost.setContent(param.getContent());
        }
        return tbpostRepository.save(tbpost).toCreateResDto();
    }
    public TbpostDto.SelectResDto detail(CommonDto.SelectServDto param){
        TbpostDto.SelectResDto selectDto = tbpostMapper.detail(param.getId());
        return selectDto;
    }

    public List<TbpostDto.SelectResDto> list(TbpostDto.ListReqDto param){
        logger.info("reqTbuserId : " + ((TbpostDto.ListServDto) param).getReqTbuserId());
        List<TbpostDto.SelectResDto> list = tbpostMapper.list(param);
        return addListDetails(list);
    }

    public List<TbpostDto.SelectResDto> moreList(TbpostDto.MoreListReqDto param){
        param.afterBuild();
        logger.info(param.getCursor());
        return addListDetails(tbpostMapper.moreList(param));
    }

    public CommonDto.PagedListResDto<TbpostDto.SelectResDto> pagedlist(TbpostDto.PagedListReqDto param){
        CommonDto.PagedListResDto<TbpostDto.SelectResDto> returnDto = new CommonDto.PagedListResDto<>();
        TbpostDto.PagedListServDto newParam = new TbpostDto.PagedListServDto();
        newParam.afterBuild(tbpostMapper.pagedListCount(param), param);
        return returnDto.afterBuild(addListDetails(tbpostMapper.pagedList(newParam)), newParam);
    }

    public List<TbpostDto.SelectResDto> addListDetails(List<TbpostDto.SelectResDto> a_list){
        List<TbpostDto.SelectResDto> result_list = new ArrayList<>();
        for(TbpostDto.SelectResDto each : a_list){
            result_list.add(detail(CommonDto.SelectServDto.builder().id(each.getId()).build()));
        }
        return result_list;
    }

}
