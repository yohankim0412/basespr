package com.thc.basespr.mapper;

import com.thc.basespr.dto.TbqnaDto;

import java.util.List;

//2024-07-08 추가(클래스 처음 추가함)
public interface TbqnaMapper {
	/**/
	TbqnaDto.SelectResDto detail(String id);
	List<TbqnaDto.SelectResDto> list(TbqnaDto.ListServDto param);

	List<TbqnaDto.SelectResDto> moreList(TbqnaDto.MoreListServDto param);
	List<TbqnaDto.SelectResDto> pagedList(TbqnaDto.PagedListServDto param);
	int pagedListCount(TbqnaDto.PagedListServDto param);
}