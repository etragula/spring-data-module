package ru.edu.springdata.entity;

public enum Category {
    IT("IT"),
    HORROR("Horror"),
    FANTASY("Fantasy"),
    CLASSIC("Classic"),
    DETECTIVE("Detective"),
    BIOGRAPHY("Biography"),
    SCIENCE_FICTION("Science fiction");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
