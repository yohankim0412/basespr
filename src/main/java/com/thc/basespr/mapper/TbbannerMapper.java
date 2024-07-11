package com.thc.basespr.mapper;

import com.thc.basespr.dto.TbbannerDto;

import java.util.List;

//2024-07-08 추가(클래스 처음 추가함)
public interface TbbannerMapper {
	/**/
	TbbannerDto.SelectResDto detail(String id);
	List<TbbannerDto.SelectResDto> list(TbbannerDto.ListServDto param);

	List<TbbannerDto.SelectResDto> moreList(TbbannerDto.MoreListServDto param);
	List<TbbannerDto.SelectResDto> pagedList(TbbannerDto.PagedListServDto param);
	int pagedListCount(TbbannerDto.PagedListServDto param);
}