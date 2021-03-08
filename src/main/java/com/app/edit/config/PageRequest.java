package com.app.edit.config;

import org.springframework.data.domain.Sort;

import static com.app.edit.config.Constant.ONE;

public class PageRequest {

    public static org.springframework.data.domain.PageRequest of(int page, int size, Sort sort) {
        page = setPage(page);
        return org.springframework.data.domain.PageRequest.of(page, size, sort);
    }

    public static org.springframework.data.domain.PageRequest of(int page, int size) {
        return org.springframework.data.domain.PageRequest.of(page, size);
    }

    private static int setPage(int page) {
        if (page - ONE <= 0) {
            page = 0;
        }
        return page;
    }
}
