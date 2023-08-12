package me.marquez.upbit.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderBy {
    /**
     * 오름차순
     */
    ASC("asc"),
    /**
     * 내림차순 (default)
     */
    DESC("desc");
    private final String order_by;
}
