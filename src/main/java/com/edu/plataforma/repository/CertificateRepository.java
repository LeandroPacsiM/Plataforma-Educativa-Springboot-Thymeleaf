package com.edu.plataforma.repository;

import com.edu.plataforma.model.Certificate;
import com.edu.plataforma.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
