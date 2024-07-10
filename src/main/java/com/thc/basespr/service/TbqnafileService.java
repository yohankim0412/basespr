package com.thc.basespr.service;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbqnafileDto;
import org.springframework.stereotype.Service;

import java.util.List;

//2024-07-08 추가(클래스 처음 추가함)
@Service
public interface TbqnafileService {
    /**/
    public TbqnafileDto.CreateResDto create(TbqnafileDto.CreateServDto param);
    public TbqnafileDto.CreateResDto update(TbqnafileDto.UpdateServDto param);
    public TbqnafileDto.CreateResDto delete(CommonDto.DeleteServDto param);
    public TbqnafileDto.CreateResDto deletes(CommonDto.DeletesServDto param);
    public TbqnafileDto.SelectResDto detail(CommonDto.SelectServDto param);
    public List<TbqnafileDto.SelectResDto> list(TbqnafileDto.ListServDto param);
    public List<TbqnafileDto.SelectResDto> moreList(TbqnafileDto.MoreListServDto param);
    public CommonDto.PagedListResDto<TbqnafileDto.SelectResDto> pagedlist(TbqnafileDto.PagedListServDto param);
}
