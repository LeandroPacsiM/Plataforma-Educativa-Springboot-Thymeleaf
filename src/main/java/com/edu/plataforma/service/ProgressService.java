package com.edu.plataforma.service;

import com.edu.plataforma.model.Progress;
import com.edu.plataforma.repository.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    public List<Progress> findAll() {
        return progressRepository.findAll();
    }

    public Optional<Progress> findById(Long id) {
        return progressRepository.findById(id);
    }

    public Progress save(Progress progress) {
        return progressRepository.save(progress);
    }

    public Progress update(Long id, Progress progressDetails) {
        return progressRepository.findById(id).map(progress -> {
            progress.setUser(progressDetails.getUser());
            progress.setLesson(progressDetails.getLesson());
            progress.setCompleted(progressDetails.getCompleted());
            progress.setCompletionDate(progressDetails.getCompletionDate());
            return progressRepository.save(progress);
        }).orElse(null);
    }

    public boolean delete(Long id) {
        if (progressRepository.existsById(id)) {
            progressRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
