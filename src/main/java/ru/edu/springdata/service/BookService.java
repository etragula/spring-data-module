package ru.edu.springdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edu.springdata.dao.BookRepository;
import ru.edu.springdata.entity.Book;
import ru.edu.springdata.entity.Category;
import ru.edu.springdata.entity.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.micrometer.common.util.StringUtils.isBlank;
import static io.micrometer.common.util.StringUtils.isNotBlank;
import static java.lang.Boolean.FALSE;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class BookService {

    @Autowired
    public BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        return bookRepository.findById(bookRepository.save(book).getId()).orElseThrow(() -> new RuntimeException("Book wasn't saved."));
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getBooksByFilter(String name, List<String> languages, List<String> categories) {
        if (isBlank(name) && isEmpty(languages) && isEmpty(categories)) return bookRepository.findAll();

        List<Book> result = new ArrayList<>();

        if (FALSE.equals(isEmpty(categories))) {
            result = bookRepository.findAllByCategoryIn(categories.stream().map(Category::valueOf).toList());
            if (isEmpty(result)) return result;
        }

        if (FALSE.equals(isEmpty(languages))) {
            List<Book> booksByLanguage = bookRepository.findAllByLanguageIn(languages.stream().map(Language::valueOf).toList());
            result = result.isEmpty() ? booksByLanguage : result;
            result.retainAll(booksByLanguage);
            if (isEmpty(result)) return result;
        }

        if (isNotBlank(name)) {
            List<Book> booksByName = bookRepository.findAllByNameContainingIgnoreCase(name.toLowerCase());
            result = result.isEmpty() ? booksByName : result;
            result.retainAll(booksByName);
        }
        return result;
    }
}
