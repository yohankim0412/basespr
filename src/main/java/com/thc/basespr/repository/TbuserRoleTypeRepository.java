package com.thc.basespr.repository;

import com.thc.basespr.domain.TbuserRoleType;
import org.springframework.data.jpa.repository.JpaRepository;

//2024-07-03 추가(클래스 처음 추가함)
public interface TbuserRoleTypeRepository extends JpaRepository<TbuserRoleType, String> {
}