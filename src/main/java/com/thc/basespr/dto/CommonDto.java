package com.thc.basespr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class CommonDto {

	//2024-07-09 추가 클래스
	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class BaseDto {
		String empty;
		public BaseDto afterBuild(BaseDto param) {
			BeanUtils.copyProperties(param, this);
			return this;
		}
	}
	//2024-07-09 추가 클래스
	@Builder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class DetailServDto {
		private String reqTbuserId;
		private boolean isAdmin;
	}

	//2024-07-04 추가 클래스
	@Builder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class UrlResDto {
		@Schema(description = "url", example="")
		private String url;
	}

	//2024-07-09 추가 클래스
	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class DeleteReqDto extends BaseDto {
		@Schema(description = "id", example="")
		private String id;
	}
	//2024-07-09 추가 클래스
	@SuperBuilder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class DeleteServDto extends CommonDto.DeleteReqDto {
		private String reqTbuserId;
		private boolean isAdmin;
	}
	//2024-07-09 추가 클래스
	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class DeletesReqDto extends BaseDto {
		@Schema(description = "ids", example="")
		private List<String> ids;
	}
	//2024-07-09 추가 클래스
	@SuperBuilder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class DeletesServDto extends CommonDto.DeletesReqDto {
		private String reqTbuserId;
		private boolean isAdmin;
	}

	//2024-07-04 추가 클래스
	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SelectReqDto extends BaseDto {
		@Schema(description = "id", example="")
		private String id;
	}
	//2024-07-04 추가 클래스
	@SuperBuilder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SelectServDto extends SelectReqDto {
		@Schema(description = "reqTbuserId", example="")
		private String reqTbuserId;
		private boolean isAdmin;
	}
	
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SelectResDto {
		@Schema(description = "id", example="")
		private String id;
		@Schema(description = "deleted", example="")
		private String deleted;
		@Schema(description = "process", example="")
		private String process;
		@Schema(description = "createdAt", example="")
		private String createdAt;
		@Schema(description = "modifiedAt", example="")
		private String modifiedAt;

		@Schema(description = "createdAtOnlyDate", example="")
		private String createdAtOnlyDate;
	}

	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ListReqDto extends BaseDto {
		@Schema(description = "deleted", example="")
		private String deleted;
		@Schema(description = "process", example="")
		private String process;
		@Schema(description = "sdate", example="")
		private String sdate;
		@Schema(description = "fdate", example="")
		private String fdate;
	}
	@SuperBuilder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ListServDto extends ListReqDto {
		@Schema(description = "reqTbuserId", example="")
		private String reqTbuserId;
		private boolean isAdmin;
	}

	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class MoreListReqDto extends BaseDto {
		@Schema(description = "deleted", example="")
		private String deleted;
		@Schema(description = "process", example="")
		private String process;
		@Schema(description = "sdate", example="")
		private String sdate;
		@Schema(description = "fdate", example="")
		private String fdate;

		@Schema(description = "cursor", example="")
		private String cursor;
		@Schema(description = "perpage", example="")
		private Integer perpage;
		@Schema(description = "orderby", example="")
		private String orderby;
		@Schema(description = "orderway", example="")
		private String orderway;

		public MoreListReqDto init(){
			if(perpage == null || perpage <= 0){
				setPerpage(10);
			}
			if(getOrderby() == null || getOrderby().isEmpty()){
				setOrderby("created_at");
			}
			if(getOrderway() == null || getOrderway().isEmpty()){
				setOrderway("desc");
			}
			if(cursor == null || cursor.isEmpty()){
				setOrderby("created_at");
				setOrderway("desc");
				setCursor("9999-12-31 23:59:59.999999");
			}
			return this;
		}
	}
	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PagedListReqDto extends BaseDto {
		@Schema(description = "deleted", example="")
		private String deleted;
		@Schema(description = "process", example="")
		private String process;
		@Schema(description = "sdate", example="")
		private String sdate;
		@Schema(description = "fdate", example="")
		private String fdate;

		@Schema(description = "callpage", example="")
		private Integer callpage;
		@Schema(description = "perpage", example="")
		private Integer perpage;
		@Schema(description = "orderby", example="")
		private String orderby;
		@Schema(description = "orderway", example="")
		private String orderway;
	}

	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PagedListServDto extends PagedListReqDto {
		@Schema(description = "offset", example="")
		private int offset;
		@Schema(description = "lastpage", example="")
		private int lastpage;
		@Schema(description = "listsize", example="")
		private int listsize;

		public PagedListServDto init(int listsize, PagedListReqDto param){
			BeanUtils.copyProperties(param, this);

			if(getPerpage() == null || getPerpage() <= 0){
				setPerpage(10);
			}
			int perpage = getPerpage();

			int lastpage = (int) listsize / perpage;
			if(listsize % perpage != 0){
				lastpage++;
			}
			if(lastpage == 0){
				lastpage++;
			}

			if(getCallpage() == null || getCallpage() < 1){
				setCallpage(1);
			} else if(getCallpage() > lastpage){
				setCallpage(lastpage);
			}
			int offset = (getCallpage() - 1) * perpage;

			if(getOrderby() == null || getOrderby().isEmpty()){
				setOrderby("created_at");
			}
			if(getOrderway() == null || getOrderway().isEmpty()){
				setOrderway("desc");
			}

			setOffset(offset);
			setLastpage(lastpage);
			setListsize(listsize);
			return this;
		}
	}

	@Schema
	@Getter
	@Setter
	public static class PagedListResDto<T> {
		@Schema(description = "요청 페이지", example="1")
		private int callpage;
		@Schema(description = "마지막 페이지", example="100")
		private int lastpage;
		@Schema(description = "한번에 조회할 갯수", example="100")
		private int perpage;
		@Schema(description = "전체 갯수", example="1")
		private int listsize;

		@Schema(description = "리스트", example="상세정보가 담긴 리스트")
		private List<T> list;

		public PagedListResDto<T> init(List<T> list, PagedListServDto param){
			setList(list);
			setCallpage(param.getCallpage());
			setLastpage(param.getLastpage());
			setListsize(param.getListsize());
			setPerpage(param.getPerpage());
			return this;
		}

	}

}