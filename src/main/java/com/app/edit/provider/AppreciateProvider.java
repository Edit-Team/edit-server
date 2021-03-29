package com.app.edit.provider;

import com.app.edit.domain.appreciate.AppreciateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AppreciateProvider {

    private final AppreciateRepository appreciateRepository;

    @Autowired
    public AppreciateProvider(AppreciateRepository appreciateRepository) {
        this.appreciateRepository = appreciateRepository;
    }
}
