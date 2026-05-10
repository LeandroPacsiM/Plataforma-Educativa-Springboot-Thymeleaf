package com.edu.plataforma.controller;

import com.edu.plataforma.model.Progress;
import com.edu.plataforma.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @GetMapping
    public List<Progress> getAllProgress() {
        return progressService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Progress> getProgressById(@PathVariable Long id) {
        return progressService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Progress createProgress(@RequestBody Progress progress) {
        return progressService.save(progress);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Progress> updateProgress(@PathVariable Long id, @RequestBody Progress progressDetails) {
        Progress updatedProgress = progressService.update(id, progressDetails);
        return updatedProgress != null ? ResponseEntity.ok(updatedProgress) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgress(@PathVariable Long id) {
        return progressService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}