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
import ru.edu.springdata.dao.AuthorRepository;
import ru.edu.springdata.entity.Author;
import ru.edu.springdata.entity.Book;
import ru.edu.springdata.entity.Category;
import ru.edu.springdata.entity.Language;
import ru.edu.springdata.service.BookService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static ru.edu.springdata.entity.Category.*;
import static ru.edu.springdata.entity.Language.*;

@Disabled
@ContextConfiguration(classes = BookService.class)
public class BookServiceContainerTest extends ContainerDatabaseAbstractTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void shouldSaveBook() {
        Author author = new Author();
        author.setName("Dan Simmons");
        author = authorRepository.save(author);
        var expectedBook = new Book("The Terror", ENGLISH, HORROR, author);
        var id = bookService.saveBook(expectedBook).getId();

        var actualBook = bookService.getBookById(id).orElse(null);
        assertThat(actualBook)
                .usingRecursiveComparison().ignoringFields("id")
                .isEqualTo(expectedBook);
    }

    @Test
    void shouldDeleteBook() {
        long id = bookService.getAllBooks().stream().map(Book::getId).findAny().orElse(-1L);

        bookService.deleteBookById(id);

        assertTrue(bookService.getBookById(id).isEmpty());
    }

    @Test
    void shouldUpdateBook() {
        Author author = new Author();
        author.setName("Karl Mays");
        author = authorRepository.save(author);
        var id = bookService.saveBook(new Book("Old Surehand I", GERMAN, SCIENCE_FICTION, author)).getId();

        var bookOpt = bookService.getBookById(id);
        assertTrue(bookOpt.isPresent());

        var expectedBook = bookOpt.get();
        Author newAuthor = authorRepository.save(new Author("Stephen King"));
        expectedBook.setAuthor(newAuthor);
        expectedBook.setName("The Green Mile");
        expectedBook.setLanguage(ENGLISH);
        expectedBook.setCategory(FANTASY);
        bookService.saveBook(expectedBook);

        bookOpt = bookService.getBookById(id);
        assertTrue(bookOpt.isPresent());
        var actualBook = bookOpt.get();
        assertThat(actualBook)
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    public static Stream<Arguments> languagesSourceMethod() {
        return Stream.of(
                arguments(List.of(ENGLISH)),
                arguments(List.of(RUSSIAN, SPANISH)),
                arguments(List.of(GERMAN, POLISH, FRENCH)),
                arguments(List.of(GERMAN, FRENCH, ENGLISH, POLISH, RUSSIAN, SPANISH))
        );
    }

    @ParameterizedTest
    @MethodSource("languagesSourceMethod")
    @DisplayName("Должны быть получены только книги с выбранным языком.")
    void shouldReturnBooksByLanguage(List<Language> languages) {
        Set<Book> expectedBooks = bookService.getAllBooks().stream()
                .filter(b -> languages.contains(b.getLanguage()))
                .collect(Collectors.toSet());
        Set<Book> actualBooks = new HashSet<>(
                bookService.getBooksByFilter(null, languages.stream().map(Enum::name).toList(), null));

        assertEquals(expectedBooks, actualBooks);
        assertFalse(expectedBooks.isEmpty());
    }

    public static Stream<Arguments> categoriesSourceMethod() {
        return Stream.of(
                arguments(List.of(IT)),
                arguments(List.of(HORROR, FANTASY)),
                arguments(List.of(DETECTIVE, BIOGRAPHY, SCIENCE_FICTION)),
                arguments(List.of(CLASSIC, DETECTIVE, BIOGRAPHY, SCIENCE_FICTION, HORROR, IT, FANTASY))
        );
    }

    @ParameterizedTest
    @MethodSource("categoriesSourceMethod")
    @DisplayName("Должны быть получены только книги с выбранным жанром.")
    void shouldReturnBooksByCategory(List<Category> categories) {
        Set<Book> expectedBooks = bookService.getAllBooks().stream()
                .filter(b -> categories.contains(b.getCategory()))
                .collect(Collectors.toSet());
        Set<Book> actualBooks = new HashSet<>(
                bookService.getBooksByFilter(null, null, categories.stream().map(Enum::name).toList())
        );

        assertEquals(expectedBooks, actualBooks);
        assertFalse(expectedBooks.isEmpty());
    }

    public static Stream<Arguments> categoriesAndLanguagesSourceMethod() {
        return Stream.of(
                arguments(List.of(IT), List.of(ENGLISH)),
                arguments(List.of(IT), List.of(ENGLISH, RUSSIAN)),
                arguments(List.of(CLASSIC, HORROR), List.of(ENGLISH, RUSSIAN))
        );
    }

    @ParameterizedTest
    @MethodSource("categoriesAndLanguagesSourceMethod")
    @DisplayName("Должны быть получены только книги, у которых совпал жанр и язык.")
    void shouldReturnBooksByCategoryAndLanguage(List<Category> categories, List<Language> languages) {
        Set<Book> expectedBooks = bookService.getAllBooks().stream()
                .filter(b -> categories.contains(b.getCategory()))
                .filter(b -> languages.contains(b.getLanguage()))
                .collect(Collectors.toSet());
        Set<Book> actualBooks = new HashSet<>(bookService.getBooksByFilter(
                null,
                languages.stream().map(Enum::name).toList(),
                categories.stream().map(Enum::name).toList()
        ));

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
        Set<Book> actualBooks = new HashSet<>(
                bookService.getBooksByFilter(
                        bookName,
                        null,
                        null
                ));

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
                Arrays.stream(Language.values()).map(Enum::name).toList(),
                Arrays.stream(Category.values()).map(Enum::name).toList()
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
    }
}

