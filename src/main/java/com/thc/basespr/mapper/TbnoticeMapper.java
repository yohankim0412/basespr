package com.thc.basespr.mapper;

import com.thc.basespr.dto.TbnoticeDto;

import java.util.List;

//2024-07-08 추가(클래스 처음 추가함)
public interface TbnoticeMapper {
	/**/
	TbnoticeDto.SelectResDto detail(String id);
	List<TbnoticeDto.SelectResDto> list(TbnoticeDto.ListServDto param);

	List<TbnoticeDto.SelectResDto> moreList(TbnoticeDto.MoreListServDto param);
	List<TbnoticeDto.SelectResDto> pagedList(TbnoticeDto.PagedListServDto param);
	int pagedListCount(TbnoticeDto.PagedListServDto param);
}