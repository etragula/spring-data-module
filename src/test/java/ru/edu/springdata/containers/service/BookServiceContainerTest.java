package ru.edu.springdata.containers.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import ru.edu.springdata.containers.ContainerDatabaseAbstractTest;
import ru.edu.springdata.dao.BookDaoImpl;
import ru.edu.springdata.entity.Book;
import ru.edu.springdata.entity.Category;
import ru.edu.springdata.entity.Language;
import ru.edu.springdata.service.BookService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static ru.edu.springdata.entity.Category.*;
import static ru.edu.springdata.entity.Language.*;

@Disabled
@ContextConfiguration(classes = {
        BookDaoImpl.class,
        BookService.class
})
public class BookServiceContainerTest extends ContainerDatabaseAbstractTest {

    @Autowired
    private BookService bookService;

    public static Stream<Arguments> languagesSourceMethod() {
        return Stream.of(
                arguments(List.of(ENGLISH.name())),
                arguments(List.of(RUSSIAN.name(), SPANISH.name())),
                arguments(List.of(GERMAN.name(), POLISH.name(), FRENCH.name())),
                arguments(List.of(GERMAN.name(), FRENCH.name(), ENGLISH.name(),
                        POLISH.name(), RUSSIAN.name(), SPANISH.name()))
        );
    }

    @ParameterizedTest
    @MethodSource("languagesSourceMethod")
    @DisplayName("Должны быть получены только книги с выбранным языком.")
    void shouldReturnBooksByLanguage(List<String> languages) {
        Set<Book> expectedBooks = bookService.getAllBooks().stream()
                .filter(b -> languages.contains(b.getLanguage().toUpperCase()))
                .collect(Collectors.toSet());
        Set<Book> actualBooks = new HashSet<>(bookService.getBooksByFilter(null, languages, null));

        assertEquals(expectedBooks, actualBooks);
        assertFalse(expectedBooks.isEmpty());
    }

    public static Stream<Arguments> categoriesSourceMethod() {
        return Stream.of(
                arguments(List.of(IT.name())),
                arguments(List.of(HORROR.name(), FANTASY.name())),
                arguments(List.of(DETECTIVE.name(), BIOGRAPHY.name(), SCIENCE_FICTION.getValue().toUpperCase())),
                arguments(List.of(CLASSIC.name(), DETECTIVE.name(), BIOGRAPHY.name(), SCIENCE_FICTION.getValue().toUpperCase(),
                        HORROR.name(), IT.name(), FANTASY.name()))
        );
    }

    @ParameterizedTest
    @MethodSource("categoriesSourceMethod")
    @DisplayName("Должны быть получены только книги с выбранным жанром.")
    void shouldReturnBooksByCategory(List<String> categories) {
        Set<Book> expectedBooks = bookService.getAllBooks().stream()
                .filter(b -> categories.contains(b.getCategory().toUpperCase()))
                .collect(Collectors.toSet());
        Set<Book> actualBooks = new HashSet<>(bookService.getBooksByFilter(null, null, categories));

        assertEquals(expectedBooks, actualBooks);
        assertFalse(expectedBooks.isEmpty());
    }

    public static Stream<Arguments> categoriesAndLanguagesSourceMethod() {
        return Stream.of(
                arguments(List.of(IT.name()),
                        List.of(ENGLISH.name())),
                arguments(List.of(IT.name()),
                        List.of(ENGLISH.name(), RUSSIAN.name())),
                arguments(List.of(CLASSIC.name(), HORROR.name()),
                        List.of(ENGLISH.name(), RUSSIAN.name()))
        );
    }

    @ParameterizedTest
    @MethodSource("categoriesAndLanguagesSourceMethod")
    @DisplayName("Должны быть получены только книги, у которых совпал жанр и язык.")
    void shouldReturnBooksByCategoryAndLanguage(List<String> categories, List<String> languages) {
        Set<Book> expectedBooks = bookService.getAllBooks().stream()
                .filter(b -> categories.contains(b.getCategory().toUpperCase()))
                .filter(b -> languages.contains(b.getLanguage().toUpperCase()))
                .collect(Collectors.toSet());
        Set<Book> actualBooks = new HashSet<>(bookService.getBooksByFilter(null, languages, categories));

        assertEquals(expectedBooks, actualBooks);
        assertFalse(expectedBooks.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Spring", "and", "of", "the", "Data", "Crime", "One"})
    @DisplayName("Должны быть получены только книги, в названии которых есть подстрока.")
    void shouldReturnBooksByName(String bookName) {
        Set<Book> expectedBooks = bookService.getAllBooks().stream()
                .filter(b -> b.getName().toLowerCase().contains(bookName.toLowerCase()))
                .collect(Collectors.toSet());
        Set<Book> actualBooks = new HashSet<>(bookService.getBooksByFilter(bookName, null, null));

        assertEquals(expectedBooks, actualBooks);
        assertFalse(expectedBooks.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Papillon", "Solaris", "Eugene", "Witcher", "Microservices", "Stand", "Spring"})
    @DisplayName("Должны быть получены только книги по названию.")
    void shouldReturnBooksByNameOnly(String bookName) {
        Set<Book> expectedBooks = bookService.getAllBooks().stream()
                .filter(b -> b.getName().toLowerCase().contains(bookName.toLowerCase()))
                .collect(Collectors.toSet());
        Set<Book> actualBooks = new HashSet<>(bookService.getBooksByFilter(bookName,
                Arrays.stream(Language.values()).map(Language::name).toList(),
                Arrays.stream(Category.values()).map(Category::getValue).toList()
        ));

        assertEquals(expectedBooks, actualBooks);
        assertEquals(1, expectedBooks.size());
    }

    @Test
    @DisplayName("При отстутствии фильтров должны вернутся все книги.")
    void shouldReturnAllWhenAllFiltersAreEmpty() {
        int expectedSize = bookService.getAllBooks().size();
        assertEquals(expectedSize, bookService.getBooksByFilter(null, null, null).size());
        assertEquals(expectedSize, bookService.getBooksByFilter("", Collections.emptyList(), Collections.emptyList()).size());
        assertTrue(expectedSize > 1);
    }
}

