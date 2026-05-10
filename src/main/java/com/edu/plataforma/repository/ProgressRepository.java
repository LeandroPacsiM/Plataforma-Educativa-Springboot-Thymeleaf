package com.edu.plataforma.repository;

import com.edu.plataforma.model.Certificate;
import com.edu.plataforma.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
}
