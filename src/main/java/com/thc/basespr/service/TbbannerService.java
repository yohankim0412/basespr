package com.thc.basespr.service;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbbannerDto;
import org.springframework.stereotype.Service;

import java.util.List;

//2024-07-09 추가(클래스 처음 추가함)
@Service
public interface TbbannerService {
    public TbbannerDto.CreateResDto sequence(TbbannerDto.SequenceServDto param);
    /**/
    public TbbannerDto.CreateResDto create(TbbannerDto.CreateServDto param);
    public TbbannerDto.CreateResDto update(TbbannerDto.UpdateServDto param);
    public TbbannerDto.CreateResDto delete(CommonDto.DeleteServDto param);
    public TbbannerDto.CreateResDto deletes(CommonDto.DeletesServDto param);
    public TbbannerDto.SelectResDto detail(CommonDto.SelectServDto param);
    public List<TbbannerDto.SelectResDto> list(TbbannerDto.ListServDto param);
    public List<TbbannerDto.SelectResDto> moreList(TbbannerDto.MoreListServDto param);
    public CommonDto.PagedListResDto<TbbannerDto.SelectResDto> pagedlist(TbbannerDto.PagedListServDto param);
}
