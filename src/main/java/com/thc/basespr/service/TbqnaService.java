package com.thc.basespr.service;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbqnaDto;
import org.springframework.stereotype.Service;

import java.util.List;

//2024-07-08 추가(클래스 처음 추가함)
@Service
public interface TbqnaService {
    /**/
    public TbqnaDto.CreateResDto create(TbqnaDto.CreateServDto param);
    public TbqnaDto.CreateResDto update(TbqnaDto.UpdateServDto param);
    public TbqnaDto.CreateResDto delete(CommonDto.DeleteServDto param);
    public TbqnaDto.CreateResDto deletes(CommonDto.DeletesServDto param);
    public TbqnaDto.SelectResDto detail(CommonDto.SelectServDto param);
    public List<TbqnaDto.SelectResDto> list(TbqnaDto.ListServDto param);
    public List<TbqnaDto.SelectResDto> moreList(TbqnaDto.MoreListServDto param);
    public CommonDto.PagedListResDto<TbqnaDto.SelectResDto> pagedlist(TbqnaDto.PagedListServDto param);
}
