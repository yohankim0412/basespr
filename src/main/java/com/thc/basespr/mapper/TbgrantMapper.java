package com.thc.basespr.mapper;

import com.thc.basespr.dto.TbgrantDto;

import java.util.List;

//2024-07-08 추가(클래스 처음 추가함)
public interface TbgrantMapper {
	TbgrantDto.SelectResDto detail(String id);
	List<TbgrantDto.SelectResDto> list(TbgrantDto.ListReqDto param);

	List<TbgrantDto.SelectResDto> moreList(TbgrantDto.MoreListReqDto param);
	List<TbgrantDto.SelectResDto> pagedList(TbgrantDto.PagedListServDto param);
	int pagedListCount(TbgrantDto.PagedListReqDto param);
}