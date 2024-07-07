package com.thc.basespr.dto;

import com.thc.basespr.domain.Tbuser;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

public class TbuserDto {

	@Schema
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LoginReqDto {
		@Schema(description = "username", example="사용자 아이디")
		@NotNull
		@NotEmpty
		@Size(max=200)
		private String username;
		@Schema(description = "password", example="사용자 비밀번호")
		@NotNull
		@NotEmpty
		@Size(max=200)
		private String password;
	}

	/**/

	@Schema
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CreateReqDto {
		@Schema(description = "username", example="사용자 아이디")
		@NotNull
		@NotEmpty
		@Size(max=200)
		private String username;
		@Schema(description = "password", example="사용자 비밀번호")
		@NotNull
		@NotEmpty
		@Size(max=200)
		private String password;
		@Schema(description = "code", example="code")
		@Size(max=12)
		private String code;
		@Schema(description = "nick", example="nick")
		@Size(max=12)
		private String nick;

		public Tbuser toEntity() {
			return Tbuser.of(username, password, code, nick);
		}
	}

	@Schema
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class CreateResDto {
		@Schema(description = "id", example="length32textnumber")
		private String id;
	}

	@Schema
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UpdateReqDto {
		@Schema(description = "id", example="")
		@NotNull
		@NotEmpty
		@Size(max=100)
		private String id;

		@Schema(description = "deleted", example="")
		private String deleted;

		@Schema(description = "nick", example="nick")
		@Size(max=12)
		private String nick;
		@Schema(description = "phone", example="phone")
		private String phone;
		@Schema(description = "img", example="대표사진")
		@Size(max=400)
		private String img;
		@Schema(description = "content", example="내용")
		@Size(max=40000)
		private String content;
	}

	@Schema
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
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

	@Schema
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ListReqDto {
		@Schema(description = "deleted", example="")
		private String deleted;
		@Schema(description = "nick", example="")
		private String nick;
		@Schema(description = "code", example="")
		private String code;
	}
	@Schema
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MoreListReqDto extends CommonDto.MoreListReqDto {
		@Schema(description = "nick", example="")
		private String nick;
		@Schema(description = "code", example="")
		private String code;
	}
	@Schema
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PagedListReqDto extends CommonDto.PagedListReqDto {
		@Schema(description = "nick", example="")
		private String nick;
		@Schema(description = "code", example="")
		private String code;
	}
	@Schema
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PagedListServDto extends CommonDto.PagedListServDto {
		@Schema(description = "nick", example="")
		private String nick;
		@Schema(description = "code", example="")
		private String code;
	}

}