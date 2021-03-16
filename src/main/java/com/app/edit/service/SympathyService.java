package com.app.edit.service;

import com.app.edit.domain.sympathy.SympathyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SympathyService {

    private final SympathyRepository sympathyRepository;

    @Autowired
    public SympathyService(SympathyRepository sympathyRepository) {
        this.sympathyRepository = sympathyRepository;
    }
}
