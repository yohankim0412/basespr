package com.thc.basespr.repository;

import com.thc.basespr.domain.Tbpost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TbpostRepository extends JpaRepository<Tbpost, String> {
}
