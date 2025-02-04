package com.thc.basespr.dto;

import com.thc.basespr.domain.Tbqnafile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

//2024-07-10 추가(클래스 처음 추가함)
public class TbqnafileDto {

	/**/

	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CreateReqDto extends CommonDto.BaseDto {
		@Schema(description = "tbqnaId", example="")
		@NotNull
		@NotEmpty
		private String tbqnaId;
		@Schema(description = "cate", example="")
		@NotNull
		@NotEmpty
		private String cate;
		@Schema(description = "url", example="")
		@NotNull
		@NotEmpty
		@Size(max=400)
		private String url;
	}
	@SuperBuilder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CreateServDto extends CreateReqDto {
		private String reqTbuserId;
		private boolean isAdmin;

		public Tbqnafile toEntity() {
			return Tbqnafile.of(super.tbqnaId, super.cate, super.url);
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

		@Schema(description = "cate", example="")
		private String cate;
		@Schema(description = "url", example="")
		@Size(max=400)
		private String url;
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
		@Schema(description = "tbqnaId", example="")
		private String tbqnaId;

		@Schema(description = "cate", example="")
		private String cate;
		@Schema(description = "url", example="")
		private String url;
	}

	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ListReqDto extends CommonDto.ListReqDto {
		@Schema(description = "tbqnaId", example="")
		private String tbqnaId;
		@Schema(description = "cate", example="")
		private String cate;
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
		@Schema(description = "tbqnaId", example="")
		private String tbqnaId;
		@Schema(description = "cate", example="")
		private String cate;
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
		@Schema(description = "tbqnaId", example="")
		private String tbqnaId;
		@Schema(description = "cate", example="")
		private String cate;
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

		@Schema(description = "tbqnaId", example="")
		private String tbqnaId;
		@Schema(description = "cate", example="")
		private String cate;
	}
}