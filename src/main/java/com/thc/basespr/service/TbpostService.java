package com.thc.basespr.service;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbpostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TbpostService {
    public TbpostDto.CreateResDto create(TbpostDto.CreateReqDto param);
    public TbpostDto.CreateResDto update(TbpostDto.UpdateReqDto param);
    public TbpostDto.SelectResDto detail(CommonDto.SelectServDto param);
    public List<TbpostDto.SelectResDto> list(TbpostDto.ListReqDto param);
    public List<TbpostDto.SelectResDto> moreList(TbpostDto.MoreListReqDto param);
    public CommonDto.PagedListResDto<TbpostDto.SelectResDto> pagedlist(TbpostDto.PagedListReqDto param);
}
