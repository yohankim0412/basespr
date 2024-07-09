package com.thc.basespr.service;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbnoticeDto;
import org.springframework.stereotype.Service;

import java.util.List;

//2024-07-08 추가(클래스 처음 추가함)
@Service
public interface TbnoticeService {
    /**/
    public TbnoticeDto.CreateResDto create(TbnoticeDto.CreateServDto param);
    public TbnoticeDto.CreateResDto update(TbnoticeDto.UpdateServDto param);
    public TbnoticeDto.CreateResDto delete(CommonDto.DeleteServDto param);
    public TbnoticeDto.CreateResDto deletes(CommonDto.DeletesServDto param);
    public TbnoticeDto.SelectResDto detail(CommonDto.SelectServDto param);
    public List<TbnoticeDto.SelectResDto> list(TbnoticeDto.ListServDto param);
    public List<TbnoticeDto.SelectResDto> moreList(TbnoticeDto.MoreListServDto param);
    public CommonDto.PagedListResDto<TbnoticeDto.SelectResDto> pagedlist(TbnoticeDto.PagedListServDto param);
}
