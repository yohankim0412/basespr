package com.thc.basespr.dto;

import com.thc.basespr.domain.Tbpost;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class TbpostDto {

	/**/

	@Schema
	@Getter
	@Setter
	public static class CreateReqDto {
		@Schema(description = "title", example="제목")
		@NotNull
		@NotEmpty
		@Size(max=200)
		private String title;

		@Schema(description = "cate", example="구분")
		@NotNull
		@NotEmpty
		@Size(max=50)
		private String cate;
		@Schema(description = "img", example="대표사진")
		@Size(max=400)
		private String img;
		@Schema(description = "content", example="내용")
		@NotNull
		@NotEmpty
		@Size(max=40000)
		private String content;

		public CreateReqDto afterBuild(CreateReqDto param) {
			BeanUtils.copyProperties(param, this);
			return this;
		}
		public Tbpost toEntity() {
			return Tbpost.of(title, cate, img, content, "");
		}
	}
	@Builder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CreateServDto extends CreateReqDto {
		private String tbuserId;
		private String reqTbuserId;

		@Override
		public Tbpost toEntity() {
			return Tbpost.of(getTitle(), getCate(), getImg(), getContent(), tbuserId);
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

		@Schema(description = "title", example="제목")
		@Size(max=200)
		private String title;
		@Schema(description = "cate", example="구분")
		@Size(max=50)
		private String cate;
		@Schema(description = "img", example="대표사진")
		@Size(max=400)
		private String img;
		@Schema(description = "content", example="내용")
		@Size(max=40000)
		private String content;

		public UpdateReqDto afterBuild(UpdateReqDto param) {
			BeanUtils.copyProperties(param, this);
			return this;
		}
	}
	@Builder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
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
		@Schema(description = "img", example="")
		private String img;
		@Schema(description = "content", example="")
		private String content;
	}

	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ListReqDto extends CommonDto.ListReqDto{
		@Schema(description = "title", example="")
		private String title;
	}
	@SuperBuilder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
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
	@AllArgsConstructor
	@NoArgsConstructor
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