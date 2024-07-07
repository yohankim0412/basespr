package com.thc.basespr.dto;

import com.thc.basespr.domain.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

//2024-07-03 추가(클래스 처음 추가함)
public class RoletypeDto {
	@Schema
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class SelectResDto{
		@Schema(description = "id", example="id")
		private String id;
		@Schema(description = "typeName", example="title")
		private String typeName;
	}
}