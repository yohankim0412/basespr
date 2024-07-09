package com.thc.basespr.mapper;

import com.thc.basespr.dto.TbuserDto;

import java.util.List;

//2024-07-09 전체 수정
public interface TbuserMapper {
	TbuserDto.SelectResDto detail(String id);
	List<TbuserDto.SelectResDto> list(TbuserDto.ListServDto param);

	List<TbuserDto.SelectResDto> moreList(TbuserDto.MoreListServDto param);
	List<TbuserDto.SelectResDto> pagedList(TbuserDto.PagedListServDto param);
	int pagedListCount(TbuserDto.PagedListServDto param);
}