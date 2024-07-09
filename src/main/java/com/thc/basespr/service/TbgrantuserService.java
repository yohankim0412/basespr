package com.thc.basespr.service;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbgrantuserDto;
import org.springframework.stereotype.Service;

import java.util.List;

//2024-07-09 추가(클래스 처음 추가함)
@Service
public interface TbgrantuserService {
    /**/
    public TbgrantuserDto.CreateResDto create(TbgrantuserDto.CreateServDto param);
    public TbgrantuserDto.CreateResDto update(TbgrantuserDto.UpdateServDto param);
    public TbgrantuserDto.CreateResDto delete(CommonDto.DeleteServDto param);
    public TbgrantuserDto.CreateResDto deletes(CommonDto.DeletesServDto param);
    public TbgrantuserDto.SelectResDto detail(CommonDto.SelectServDto param);
    public List<TbgrantuserDto.SelectResDto> list(TbgrantuserDto.ListServDto param);
    public List<TbgrantuserDto.SelectResDto> moreList(TbgrantuserDto.MoreListServDto param);
    public CommonDto.PagedListResDto<TbgrantuserDto.SelectResDto> pagedlist(TbgrantuserDto.PagedListServDto param);
}
