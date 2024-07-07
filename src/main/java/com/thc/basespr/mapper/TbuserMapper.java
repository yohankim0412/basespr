package com.thc.basespr.mapper;

import com.thc.basespr.dto.TbuserDto;

import java.util.List;

public interface TbuserMapper {
	TbuserDto.SelectResDto detail(String id);
	List<TbuserDto.SelectResDto> list(TbuserDto.ListReqDto param);

	List<TbuserDto.SelectResDto> moreList(TbuserDto.MoreListReqDto param);
	List<TbuserDto.SelectResDto> pagedList(TbuserDto.PagedListServDto param);
	int pagedListCount(TbuserDto.PagedListReqDto param);
}