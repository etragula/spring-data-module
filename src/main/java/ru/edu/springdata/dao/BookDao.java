package ru.edu.springdata.dao;

import ru.edu.springdata.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    List<Book> findAll();

    Optional<Book> findById(long id);

    List<Book> findByName(String name);

    List<Book> findByCategories(List<String> categories);

    List<Book> findByLanguages(List<String> languages);

    Long save(Book book);

    int update(Book book);

    int deleteById(long id);
}
