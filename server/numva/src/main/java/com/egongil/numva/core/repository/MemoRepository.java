package com.egongil.numva.core.repository;

import com.egongil.numva.core.entity.SafetyInfo.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}
