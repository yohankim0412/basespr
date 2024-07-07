package com.thc.basespr.repository;

import com.thc.basespr.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//2024-07-03 추가(클래스 처음 추가함)
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByContent(String Content);
    List<RefreshToken> findByTbuserId(String TbuserId);
}
