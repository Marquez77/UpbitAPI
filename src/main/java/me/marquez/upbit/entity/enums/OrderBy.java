package me.marquez.upbit.entity.enums;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderBy {
    /**
     * 오름차순
     */
    @SerializedName("asc")
    ASC,

    /**
     * 내림차순 (default)
     */
    @SerializedName("desc")
    DESC;
}
