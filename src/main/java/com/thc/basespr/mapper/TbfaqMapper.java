package com.thc.basespr.mapper;

import com.thc.basespr.dto.TbfaqDto;

import java.util.List;

//2024-07-08 추가(클래스 처음 추가함)
public interface TbfaqMapper {
	/**/
	TbfaqDto.SelectResDto detail(String id);
	List<TbfaqDto.SelectResDto> list(TbfaqDto.ListServDto param);

	List<TbfaqDto.SelectResDto> moreList(TbfaqDto.MoreListServDto param);
	List<TbfaqDto.SelectResDto> pagedList(TbfaqDto.PagedListServDto param);
	int pagedListCount(TbfaqDto.PagedListServDto param);
}