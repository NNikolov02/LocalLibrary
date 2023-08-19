package com.example.locallibrary1.dto.borrowinghistory;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@EqualsAndHashCode
public class BorrowingHistoryApiPage<T> {

    private List<T> content;
    private PaginationMetadata pagination;


    public  BorrowingHistoryApiPage(Page<T> springPage) {
        this.content = springPage.getContent();
        this.pagination = PaginationMetadata.builder()
                .currentPage(springPage.getNumber())
                .pageSize(springPage.getSize())
                .totalElement(springPage.getTotalElements())
                .totalPages(springPage.getTotalPages())
                .build();
    }

    @Data
    @Builder
    private static class PaginationMetadata {
        private Integer currentPage;
        private Integer pageSize;
        private Integer totalPages;
        private Long totalElement;
    }
}