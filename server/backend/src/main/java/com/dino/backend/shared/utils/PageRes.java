package com.dino.backend.shared.utils;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageRes<T> {
    Pagination pagination;
    List<T> items;

    // NOTE: Builder
    // @Builder need to Getter, Setter, AllArgsConstructor, NoArgsConstructor
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Pagination {
        private int totalPages;
        private long totalElements;
        private int page;
        private int size;
    }

    /**
     * Map PageRes from PageJpa
     * @param pageJpa Page
     * @return PageRes
     */
    public static <T> PageRes<T> from(Page<T> pageJpa) {
        Pagination pagination = Pagination.builder()
                .totalPages(pageJpa.getTotalPages())
                .totalElements(pageJpa.getTotalElements())
                .page(pageJpa.getNumber() + 1) // Page of client starts 1. But PageNumber of Jpa starts from 0
                .size(pageJpa.getSize())
                .build();

        return PageRes.<T>builder()
                .pagination(pagination)
                .items(pageJpa.getContent())
                .build();
    }
}
