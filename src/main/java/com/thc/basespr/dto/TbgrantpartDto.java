package com.thc.basespr.dto;

import com.thc.basespr.domain.Tbgrantpart;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

//2024-07-08 추가(클래스 처음 추가함)
public class TbgrantpartDto {

	public static String[][] cates = {
			{"tbgrant","접근권한"}
			,{"tbuser", "사용자"}

			,{"tbnotice", "공지사항"}
			,{"tbfaq", "FAQ"}
			,{"tbqna", "1:1문의"}
			,{"tbbanner", "배너"}
			,{"tbpopup", "팝업"}
			,{"tbpost", "게시글"}
			,{"tbpcate", "게시글 카테고리"}

			,{"tbcrew", "가맹점"}
	};

	@SuperBuilder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ToggleReqDto extends CreateReqDto {
		@Schema(description = "way", example="")
		@NotNull
		@NotEmpty
		private boolean way;
	}
	@SuperBuilder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ToggleServDto extends ToggleReqDto {
		private String reqTbuserId;
		private boolean isAdmin;
	}

	/**/

	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CreateReqDto extends CommonDto.BaseDto {
		@Schema(description = "tbgrantId", example="")
		@NotNull
		@NotEmpty
		private String tbgrantId;

		@Schema(description = "cate", example="구분")
		@NotNull
		@NotEmpty
		@Size(max=50)
		private String cate;
		@Schema(description = "content", example="내용")
		@NotNull
		@NotEmpty
		@Size(max=40000)
		private String content;

		public Tbgrantpart toEntity() {
			return Tbgrantpart.of(tbgrantId, cate, content);
		}
	}
	@SuperBuilder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CreateServDto extends CreateReqDto {
		private String reqTbuserId;
		private boolean isAdmin;
	}

	@Builder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CreateResDto {
		@Schema(description = "id", example="length32textnumber")
		private String id;
	}

	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class UpdateReqDto extends CommonDto.BaseDto {
		@Schema(description = "id", example="")
		@NotNull
		@NotEmpty
		@Size(max=100)
		private String id;

		@Schema(description = "deleted", example="")
		private String deleted;
		@Schema(description = "process", example="")
		private String process;

		@Schema(description = "tbgrantId", example="")
		private String tbgrantId;
		@Schema(description = "cate", example="구분")
		@Size(max=50)
		private String cate;
		@Schema(description = "content", example="내용")
		@Size(max=40000)
		private String content;
	}
	@SuperBuilder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class UpdateServDto extends UpdateReqDto {
		private String reqTbuserId;
		private boolean isAdmin;
	}

	@Schema
	@Getter
	@Setter
	public static class SelectResDto extends CommonDto.SelectResDto {
		@Schema(description = "tbgrantId", example="")
		private String tbgrantId;
		@Schema(description = "cate", example="")
		private String cate;
		@Schema(description = "content", example="")
		private String content;

		private String[][] types = TbgrantpartDto.cates;

		@Schema(description = "tbgrantparts", example="")
		private List<TbgrantpartDto.SelectResDto> tbgrantparts = new ArrayList<>();
	}

	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ListReqDto extends CommonDto.ListReqDto {
		@Schema(description = "tbgrantId", example="")
		private String tbgrantId;
		@Schema(description = "cate", example="")
		private String cate;
		@Schema(description = "content", example="")
		private String content;
	}
	@SuperBuilder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ListServDto extends ListReqDto {
		private String reqTbuserId;
		private boolean isAdmin;
	}
	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class MoreListReqDto extends CommonDto.MoreListReqDto {
		@Schema(description = "tbgrantId", example="")
		private String tbgrantId;
		@Schema(description = "cate", example="")
		private String cate;
		@Schema(description = "content", example="")
		private String content;
	}
	@SuperBuilder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class MoreListServDto extends MoreListReqDto {
		private String reqTbuserId;
		private boolean isAdmin;
	}

	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PagedListReqDto extends CommonDto.PagedListReqDto {
		@Schema(description = "tbgrantId", example="")
		private String tbgrantId;
		@Schema(description = "cate", example="")
		private String cate;
		@Schema(description = "content", example="")
		private String content;
	}
	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PagedListServDto extends CommonDto.PagedListServDto {
		private String reqTbuserId;
		private boolean isAdmin;

		@Schema(description = "title", example="")
		private String tbgrantId;
		@Schema(description = "cate", example="")
		private String cate;
		@Schema(description = "content", example="")
		private String content;
	}
}