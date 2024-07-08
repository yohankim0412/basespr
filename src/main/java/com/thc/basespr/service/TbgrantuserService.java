package com.thc.basespr.service;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbgrantuserDto;
import org.springframework.stereotype.Service;

import java.util.List;

//2024-07-08 추가(클래스 처음 추가함)
@Service
public interface TbgrantuserService {
    public TbgrantuserDto.CreateResDto create(TbgrantuserDto.CreateReqDto param);
    public TbgrantuserDto.CreateResDto update(TbgrantuserDto.UpdateReqDto param);
    public TbgrantuserDto.SelectResDto detail(CommonDto.SelectServDto param);
    public List<TbgrantuserDto.SelectResDto> list(TbgrantuserDto.ListReqDto param);
    public List<TbgrantuserDto.SelectResDto> moreList(TbgrantuserDto.MoreListReqDto param);
    public CommonDto.PagedListResDto<TbgrantuserDto.SelectResDto> pagedlist(TbgrantuserDto.PagedListReqDto param);
}
