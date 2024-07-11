package com.thc.basespr.dto;

import com.thc.basespr.domain.Tbuser;
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
public class TbuserDto {

	@Schema
	@Getter
	@Setter
	public static class KgcertTokenReqDto {
		private String userName;
		private String userPhone;
		private String userBirth;
	}

	@Builder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class KgcertTokenResDto {
		private String mid;
		private String apiKey;
		private String reqSvcCd;
		private String mTxId;
		private String reservedMsg;
		private String flgFixedUser;
		private String authHash;
		private String userHash;
	}

	@Schema
	@Getter
	@Setter
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

	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CreateReqDto extends CommonDto.BaseDto {
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

		@Schema(description = "name", example="")
		private String name;
		@Schema(description = "phone", example="")
		private String phone;
		@Schema(description = "birth", example="")
		private String birth;
		@Schema(description = "gender", example="")
		private String gender;
		@Schema(description = "route", example="")
		private String route;

		public Tbuser toEntity() {
			return Tbuser.of(username, password, code, nick, name, phone, birth, gender, route);
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

		@Schema(description = "nick", example="nick")
		@Size(max=12)
		private String nick;
		@Schema(description = "phone", example="phone")
		private String phone;
		@Schema(description = "img", example="대표사진")
		@Size(max=400)
		private String img;
		@Schema(description = "brief", example="내용")
		private String brief;
		@Schema(description = "content", example="내용")
		@Size(max=40000)
		private String content;

		@Schema(description = "popup", example="")
		private Boolean popup;
		@Schema(description = "agreethird", example="")
		private Boolean agreethird;
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
		@Schema(description = "code", example="")
		private String code;
		@Schema(description = "nick", example="")
		private String nick;
		@Schema(description = "name", example="name")
		private String name;
		@Schema(description = "phone", example="phone")
		private String phone;
		@Schema(description = "img", example="대표사진")
		private String img;
		@Schema(description = "brief", example="간단소개")
		private String brief;
		@Schema(description = "content", example="내용")
		private String content;

		@Schema(description = "popup", example="")
		private boolean popup;
		@Schema(description = "agreethird", example="")
		private boolean agreethird;
	}

	@SuperBuilder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ListReqDto extends CommonDto.ListReqDto {
		@Schema(description = "code", example="")
		private String code;
		@Schema(description = "nick", example="")
		private String nick;
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
		@Schema(description = "code", example="")
		private String code;
		@Schema(description = "nick", example="")
		private String nick;
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
		@Schema(description = "code", example="")
		private String code;
		@Schema(description = "nick", example="")
		private String nick;
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

		@Schema(description = "code", example="")
		private String code;
		@Schema(description = "nick", example="")
		private String nick;
	}
}