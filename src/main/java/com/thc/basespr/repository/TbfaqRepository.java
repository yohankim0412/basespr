package com.thc.basespr.repository;

import com.thc.basespr.domain.Tbfaq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//2024-07-09 추가(클래스 처음 추가함)
@Repository
public interface TbfaqRepository extends JpaRepository<Tbfaq, String> {
    Tbfaq findBySequence(Integer sequence);
}
