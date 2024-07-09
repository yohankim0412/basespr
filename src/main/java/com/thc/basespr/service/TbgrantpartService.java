package com.thc.basespr.service;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbgrantpartDto;
import org.springframework.stereotype.Service;

import java.util.List;

//2024-07-09 추가(클래스 처음 추가함)
@Service
public interface TbgrantpartService {
    public TbgrantpartDto.CreateResDto toggle(TbgrantpartDto.ToggleServDto params);
    /**/
    public TbgrantpartDto.CreateResDto create(TbgrantpartDto.CreateServDto param);
    public TbgrantpartDto.CreateResDto update(TbgrantpartDto.UpdateServDto param);
    public TbgrantpartDto.CreateResDto delete(CommonDto.DeleteServDto param);
    public TbgrantpartDto.CreateResDto deletes(CommonDto.DeletesServDto param);
    public TbgrantpartDto.SelectResDto detail(CommonDto.SelectServDto param);
    public List<TbgrantpartDto.SelectResDto> list(TbgrantpartDto.ListServDto param);
    public List<TbgrantpartDto.SelectResDto> moreList(TbgrantpartDto.MoreListServDto param);
    public CommonDto.PagedListResDto<TbgrantpartDto.SelectResDto> pagedlist(TbgrantpartDto.PagedListServDto param);
}
