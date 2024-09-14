package com.airfranceklm.fasttrack.assignment.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusEnum {
    DRAFT("DRAFT"), REQUESTED("REQUESTED"), SCHEDULED("SCHEDULED"), ARCHIVED("ARCHIVED");

    private final String value;

    StatusEnum(String status) {
        this.value = status;
    }


    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String text) {
        for (StatusEnum s : StatusEnum.values()) {
            if (String.valueOf(s.value).equals(text)) {
                return s;
            }
        }
        return null;
    }

}
