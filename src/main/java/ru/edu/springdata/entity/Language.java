package ru.edu.springdata.entity;

public enum Language {
    RUSSIAN("Russian"),
    ENGLISH("English"),
    GERMAN("German"),
    SPANISH("Spanish"),
    POLISH("Polish"),
    FRENCH("French");

    private final String value;

    Language(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
