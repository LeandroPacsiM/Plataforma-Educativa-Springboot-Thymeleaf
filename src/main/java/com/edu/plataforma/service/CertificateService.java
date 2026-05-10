package com.edu.plataforma.service;

import com.edu.plataforma.model.Certificate;
import com.edu.plataforma.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    public List<Certificate> findAll() {
        return certificateRepository.findAll();
    }

    public Optional<Certificate> findById(Long id) {
        return certificateRepository.findById(id);
    }

    public Certificate save(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    public Certificate update(Long id, Certificate certificateDetails) {
        return certificateRepository.findById(id).map(certificate -> {
            certificate.setUser(certificateDetails.getUser());
            certificate.setCourse(certificateDetails.getCourse());
            certificate.setIssuedAt(certificateDetails.getIssuedAt());
            certificate.setCertificateCode(certificateDetails.getCertificateCode());
            certificate.setFinalGrade(certificateDetails.getFinalGrade());
            return certificateRepository.save(certificate);
        }).orElse(null);
    }

    public boolean delete(Long id) {
        if (certificateRepository.existsById(id)) {
            certificateRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
