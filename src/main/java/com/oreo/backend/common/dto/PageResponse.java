package com.oreo.backend.common.dto;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageResponse<T> {

    boolean first;
    boolean last;
    private List<T> content;
    private int currentSize; // 현재 content 개수
    private int totalPages; // 총 page 개수
    private long totalElements; // 전체 content 개수

    public PageResponse(Page<T> page) {
        first = page.isFirst();
        last = page.isLast();
        content = page.getContent();
        currentSize = page.getNumberOfElements();
        totalPages = page.getTotalPages();
        totalElements = page.getTotalElements();
    }
}
