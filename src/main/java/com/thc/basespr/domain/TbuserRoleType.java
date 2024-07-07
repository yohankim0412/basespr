package com.thc.basespr.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

//2024-07-03 추가(클래스 처음 추가함)
@Getter
@ToString(callSuper = true)
@Table(name = "tbuser_role_type",
		indexes = {
				@Index(name = "IDX_tbuserroletype_createdAt", columnList = "createdAt")
				,@Index(name = "IDX_tbuserroletype_modifiedAt", columnList = "modifiedAt")
		}
)
@Entity
public class TbuserRoleType extends AuditingFields {

	@ManyToOne
	@JoinColumn(name = "tbuser_id")
	private Tbuser tbuser;

	@ManyToOne
	@JoinColumn(name = "role_type_id")
	private RoleType roleType;

	protected TbuserRoleType(){}
	private TbuserRoleType(Tbuser tbuser, RoleType roleType) {
		this.tbuser = tbuser;
		this.roleType = roleType;
	}

	public static TbuserRoleType of(Tbuser tbuser, RoleType roleType) {
		return new TbuserRoleType(tbuser, roleType);
	}
	
}