package com.thc.basespr.mapper;

import com.thc.basespr.dto.TbpopupDto;

import java.util.List;

//2024-07-08 추가(클래스 처음 추가함)
public interface TbpopupMapper {
	/**/
	TbpopupDto.SelectResDto detail(String id);
	List<TbpopupDto.SelectResDto> list(TbpopupDto.ListServDto param);

	List<TbpopupDto.SelectResDto> moreList(TbpopupDto.MoreListServDto param);
	List<TbpopupDto.SelectResDto> pagedList(TbpopupDto.PagedListServDto param);
	int pagedListCount(TbpopupDto.PagedListServDto param);
}