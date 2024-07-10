package com.thc.basespr.service;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbuserDto;
import com.thc.basespr.security.JwtTokenDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//2024-07-09 전체 수정
@Service
public interface TbuserService {
    public JwtTokenDto tempKgcert(TbuserDto.CreateServDto param);
    public TbuserDto.CreateResDto signup(TbuserDto.CreateServDto param);
    /**/
    public TbuserDto.CreateResDto create(TbuserDto.CreateServDto param);
    public TbuserDto.CreateResDto update(TbuserDto.UpdateServDto param);
    public TbuserDto.CreateResDto delete(CommonDto.DeleteServDto param);
    public TbuserDto.CreateResDto deletes(CommonDto.DeletesServDto param);
    public TbuserDto.SelectResDto detail(CommonDto.SelectServDto param);
    public List<TbuserDto.SelectResDto> list(TbuserDto.ListServDto param);
    public List<TbuserDto.SelectResDto> moreList(TbuserDto.MoreListServDto param);
    public CommonDto.PagedListResDto<TbuserDto.SelectResDto> pagedlist(TbuserDto.PagedListServDto param);
}
