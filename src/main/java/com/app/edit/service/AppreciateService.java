package com.app.edit.service;

import com.app.edit.domain.appreciate.AppreciateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AppreciateService {

    private final AppreciateRepository appreciateRepository;

    @Autowired
    public AppreciateService(AppreciateRepository appreciateRepository) {
        this.appreciateRepository = appreciateRepository;
    }
}
