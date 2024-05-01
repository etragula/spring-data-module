package ru.edu.springdata.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.springdata.entity.Book;
import ru.edu.springdata.entity.Category;
import ru.edu.springdata.entity.Language;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByCategoryIn(List<Category> categories);

    List<Book> findAllByLanguageIn(List<Language> languages);

    List<Book> findAllByNameContainingIgnoreCase(String name);
}
