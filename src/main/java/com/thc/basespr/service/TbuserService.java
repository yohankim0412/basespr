package com.thc.basespr.service;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbuserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TbuserService {
    public TbuserDto.CreateResDto signup(TbuserDto.CreateReqDto param);
    /**/
    public TbuserDto.CreateResDto create(TbuserDto.CreateReqDto param);
    public TbuserDto.CreateResDto update(TbuserDto.UpdateReqDto param);
    public TbuserDto.SelectResDto detail(CommonDto.SelectReqDto param);
    public List<TbuserDto.SelectResDto> list(TbuserDto.ListReqDto param);
    public List<TbuserDto.SelectResDto> moreList(TbuserDto.MoreListReqDto param);
    public CommonDto.PagedListResDto<TbuserDto.SelectResDto> pagedlist(TbuserDto.PagedListReqDto param);
}
