package com.thc.basespr.mapper;

import com.thc.basespr.dto.TbqnafileDto;

import java.util.List;

//2024-07-08 추가(클래스 처음 추가함)
public interface TbqnafileMapper {
	/**/
	TbqnafileDto.SelectResDto detail(String id);
	List<TbqnafileDto.SelectResDto> list(TbqnafileDto.ListServDto param);

	List<TbqnafileDto.SelectResDto> moreList(TbqnafileDto.MoreListServDto param);
	List<TbqnafileDto.SelectResDto> pagedList(TbqnafileDto.PagedListServDto param);
	int pagedListCount(TbqnafileDto.PagedListServDto param);
}