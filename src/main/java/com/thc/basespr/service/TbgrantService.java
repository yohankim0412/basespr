package com.thc.basespr.service;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbgrantDto;
import org.springframework.stereotype.Service;

import java.util.List;

//2024-07-08 추가(클래스 처음 추가함)
@Service
public interface TbgrantService {
    public TbgrantDto.CreateResDto create(TbgrantDto.CreateServDto param);
    public TbgrantDto.CreateResDto update(TbgrantDto.UpdateServDto param);
    public TbgrantDto.CreateResDto delete(CommonDto.DeleteServDto param);
    public TbgrantDto.SelectResDto detail(CommonDto.SelectServDto param);
    public List<TbgrantDto.SelectResDto> list(TbgrantDto.ListServDto param);
    public List<TbgrantDto.SelectResDto> moreList(TbgrantDto.MoreListServDto param);
    public CommonDto.PagedListResDto<TbgrantDto.SelectResDto> pagedlist(TbgrantDto.PagedListServDto param);
}
