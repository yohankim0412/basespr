package com.thc.basespr.service;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbfaqDto;
import org.springframework.stereotype.Service;

import java.util.List;

//2024-07-09 추가(클래스 처음 추가함)
@Service
public interface TbfaqService {
    public TbfaqDto.CreateResDto sequence(TbfaqDto.SequenceServDto param);
    /**/
    public TbfaqDto.CreateResDto create(TbfaqDto.CreateServDto param);
    public TbfaqDto.CreateResDto update(TbfaqDto.UpdateServDto param);
    public TbfaqDto.CreateResDto delete(CommonDto.DeleteServDto param);
    public TbfaqDto.CreateResDto deletes(CommonDto.DeletesServDto param);
    public TbfaqDto.SelectResDto detail(CommonDto.SelectServDto param);
    public List<TbfaqDto.SelectResDto> list(TbfaqDto.ListServDto param);
    public List<TbfaqDto.SelectResDto> moreList(TbfaqDto.MoreListServDto param);
    public CommonDto.PagedListResDto<TbfaqDto.SelectResDto> pagedlist(TbfaqDto.PagedListServDto param);
}
