package com.thc.basespr.mapper;

import com.thc.basespr.dto.TbgrantuserDto;

import java.util.List;

//2024-07-08 추가(클래스 처음 추가함)
public interface TbgrantuserMapper {
	TbgrantuserDto.SelectResDto detail(String id);
	List<TbgrantuserDto.SelectResDto> list(TbgrantuserDto.ListReqDto param);

	List<TbgrantuserDto.SelectResDto> moreList(TbgrantuserDto.MoreListReqDto param);
	List<TbgrantuserDto.SelectResDto> pagedList(TbgrantuserDto.PagedListServDto param);
	int pagedListCount(TbgrantuserDto.PagedListReqDto param);
}