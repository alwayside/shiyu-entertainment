package com.shiyu.entity.repository;

public enum SourceType {
    /**
     * article
     */
    ARTICLE("A"),

    /**
     * photo
     */
    PHOTO("P");

    private String value;

    private SourceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
