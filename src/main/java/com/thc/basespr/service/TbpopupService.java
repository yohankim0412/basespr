package com.thc.basespr.service;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbpopupDto;
import org.springframework.stereotype.Service;

import java.util.List;

//2024-07-09 추가(클래스 처음 추가함)
@Service
public interface TbpopupService {
    public TbpopupDto.CreateResDto sequence(TbpopupDto.SequenceServDto param);
    /**/
    public TbpopupDto.CreateResDto create(TbpopupDto.CreateServDto param);
    public TbpopupDto.CreateResDto update(TbpopupDto.UpdateServDto param);
    public TbpopupDto.CreateResDto delete(CommonDto.DeleteServDto param);
    public TbpopupDto.CreateResDto deletes(CommonDto.DeletesServDto param);
    public TbpopupDto.SelectResDto detail(CommonDto.SelectServDto param);
    public List<TbpopupDto.SelectResDto> list(TbpopupDto.ListServDto param);
    public List<TbpopupDto.SelectResDto> moreList(TbpopupDto.MoreListServDto param);
    public CommonDto.PagedListResDto<TbpopupDto.SelectResDto> pagedlist(TbpopupDto.PagedListServDto param);
}
