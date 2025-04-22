package com.dino.backend.shared.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageRes<T> {
    private int totalPages;
    private long totalElements;
    private int page;
    private int size;
    private List<T> content;
    //todo: change meta to pagination
    //todo: change content to items
}
