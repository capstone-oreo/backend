package com.oreo.backend.common.dto;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageResponse<T> {

    private final boolean first;
    private final boolean last;
    private final List<T> content;
    private final int currentSize; // 현재 content 개수
    private final int totalPages; // 총 page 개수
    private final long totalElements; // 전체 content 개수

    public PageResponse(Page<T> page) {
        first = page.isFirst();
        last = page.isLast();
        content = page.getContent();
        currentSize = page.getNumberOfElements();
        totalPages = page.getTotalPages();
        totalElements = page.getTotalElements();
    }
}
