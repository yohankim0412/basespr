package com.thc.basespr.mapper;

import com.thc.basespr.dto.TbgrantpartDto;

import java.util.List;

//2024-07-09 추가(클래스 처음 추가함)
public interface TbgrantpartMapper {
	TbgrantpartDto.SelectResDto detail(String id);
	List<TbgrantpartDto.SelectResDto> list(TbgrantpartDto.ListReqDto param);

	List<TbgrantpartDto.SelectResDto> moreList(TbgrantpartDto.MoreListReqDto param);
	List<TbgrantpartDto.SelectResDto> pagedList(TbgrantpartDto.PagedListServDto param);
	int pagedListCount(TbgrantpartDto.PagedListServDto param);
}