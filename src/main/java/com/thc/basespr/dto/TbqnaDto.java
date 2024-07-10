package com.thc.basespr.dto;

import com.thc.basespr.domain.Tbqna;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

//2024-07-10 추가(클래스 처음 추가함)
public class TbqnaDto {

	/**/

	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CreateReqDto extends CommonDto.BaseDto {
		@Schema(description = "title", example="제목")
		@NotNull
		@NotEmpty
		@Size(max=200)
		private String title;
		@Schema(description = "content", example="내용")
		@NotNull
		@NotEmpty
		@Size(max=10000)
		private String content;

		@Schema(description = "cates", example="")
		private String[] cates;
		@Schema(description = "urls", example="")
		private String[] urls;
	}
	@SuperBuilder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CreateServDto extends CreateReqDto {
		private String tbuserId;
		private String reqTbuserId;
		private boolean isAdmin;

		public Tbqna toEntity() {
			return Tbqna.of(tbuserId, super.title, super.content, null);
		}
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

		@Schema(description = "title", example="제목")
		@Size(max=200)
		private String title;
		@Schema(description = "content", example="내용")
		@Size(max=10000)
		private String content;
		@Schema(description = "answer", example="답변")
		@Size(max=10000)
		private String answer;
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
		@Schema(description = "tbuserId", example="")
		private String tbuserId;

		@Schema(description = "tbuserDeleted", example="")
		private String tbuserDeleted;
		@Schema(description = "tbuserCode", example="")
		private String tbuserCode;
		@Schema(description = "tbuserNick", example="")
		private String tbuserNick;
		@Schema(description = "tbuserName", example="")
		private String tbuserName;
		@Schema(description = "tbuserPhone", example="")
		private String tbuserPhone;
		@Schema(description = "tbuserImg", example="")
		private String tbuserImg;

		@Schema(description = "title", example="")
		private String title;
		@Schema(description = "content", example="")
		private String content;
		@Schema(description = "answer", example="")
		private String answer;

		@Schema(description = "files", example="")
		private List<TbqnafileDto.SelectResDto> files;
	}

	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ListReqDto extends CommonDto.ListReqDto {
		@Schema(description = "tbuserId", example="")
		private String tbuserId;
		@Schema(description = "title", example="")
		private String title;
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
		@Schema(description = "tbuserId", example="")
		private String tbuserId;
		@Schema(description = "title", example="")
		private String title;
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
		@Schema(description = "tbuserId", example="")
		private String tbuserId;
		@Schema(description = "title", example="")
		private String title;
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

		@Schema(description = "tbuserId", example="")
		private String tbuserId;
		@Schema(description = "title", example="")
		private String title;
	}
}