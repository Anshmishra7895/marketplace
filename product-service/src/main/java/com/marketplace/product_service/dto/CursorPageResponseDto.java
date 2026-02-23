package com.marketplace.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
/* Page Response return by Keyset pagination won't return extra information like isLast, no. of pages, etc
* So to return all those information in our API we are creating our own DTO class with this information */
public class CursorPageResponseDto <T> {

    List<T> data;
    int pageSize;
    Long nextCursor;
    Boolean hasNext;

}
