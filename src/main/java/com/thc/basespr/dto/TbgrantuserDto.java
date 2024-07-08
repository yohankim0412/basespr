package com.thc.basespr.dto;

import com.thc.basespr.domain.Tbgrantuser;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;

//2024-07-08 추가(클래스 처음 추가함)
public class TbgrantuserDto {

	/**/

	@Schema
	@Getter
	@Setter
	public static class CreateReqDto {
		@Schema(description = "tbgrantId", example="tbgrantId")
		@NotNull
		@NotEmpty
		private String tbgrantId;
		@Schema(description = "tbuserId", example="tbuserId")
		@NotNull
		@NotEmpty
		private String tbuserId;

		public CreateReqDto afterBuild(CreateReqDto param) {
			BeanUtils.copyProperties(param, this);
			return this;
		}
		public Tbgrantuser toEntity() {
			return Tbgrantuser.of(tbgrantId, tbuserId);
		}
	}
	@Builder
	@Getter
	@Setter
	public static class CreateServDto extends CreateReqDto {
		private String reqTbuserId;
	}

	@Builder
	@Schema
	@Getter
	@Setter
	public static class CreateResDto {
		@Schema(description = "id", example="length32textnumber")
		private String id;
	}

	@Schema
	@Getter
	@Setter
	public static class UpdateReqDto {
		@Schema(description = "id", example="")
		@NotNull
		@NotEmpty
		@Size(max=100)
		private String id;

		@Schema(description = "deleted", example="")
		private String deleted;
		@Schema(description = "process", example="")
		private String process;

		@Schema(description = "tbgrantId", example="tbgrantId")
		@NotNull
		@NotEmpty
		private String tbgrantId;
		@Schema(description = "tbuserId", example="tbuserId")
		@NotNull
		@NotEmpty
		private String tbuserId;

		public UpdateReqDto afterBuild(UpdateReqDto param) {
			BeanUtils.copyProperties(param, this);
			return this;
		}
	}
	@Builder
	@Getter
	@Setter
	public static class UpdateServDto extends UpdateReqDto {
		private String reqTbuserId;
	}

	@Schema
	@Getter
	@Setter
	public static class SelectResDto extends CommonDto.SelectResDto {
		@Schema(description = "title", example="")
		private String title;
		@Schema(description = "cate", example="")
		private String cate;
		@Schema(description = "content", example="")
		private String content;
	}

	@SuperBuilder
	@Schema
	@Getter
	@Setter
	public static class ListReqDto {
		@Schema(description = "deleted", example="")
		private String deleted;
		@Schema(description = "title", example="")
		private String title;
	}
	@SuperBuilder
	@Getter
	@Setter
	public static class ListServDto extends ListReqDto{
		private String reqTbuserId;

		public ListReqDto afterBuild(ListReqDto param) {
			BeanUtils.copyProperties(param, this);
			return this;
		}
	}
	@SuperBuilder
	@Schema
	@Getter
	@Setter
	public static class MoreListReqDto extends CommonDto.MoreListReqDto {
		@Schema(description = "title", example="")
		private String title;
		@Schema(description = "cate", example="")
		private String cate;
	}
	@Schema
	@Getter
	@Setter
	public static class PagedListReqDto extends CommonDto.PagedListReqDto {
		@Schema(description = "title", example="")
		private String title;
		@Schema(description = "cate", example="")
		private String cate;
	}
	@Schema
	@Getter
	@Setter
	public static class PagedListServDto extends CommonDto.PagedListServDto {
		@Schema(description = "title", example="")
		private String title;
		@Schema(description = "cate", example="")
		private String cate;
	}
}