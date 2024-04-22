package ru.edu.springdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edu.springdata.dao.BookDao;
import ru.edu.springdata.entity.Book;

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
    public BookDao bookDao;

    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookDao.findById(id);
    }

    public Book saveBook(Book book) {
        return bookDao.findById(bookDao.save(book)).orElseThrow(() -> new RuntimeException("Book wasn't saved."));
    }

    public void updateBook(Book book) {
        if (bookDao.update(book) == 0) {
            throw new RuntimeException("Book wasn't updated. Maybe it doesn't exist");
        }
    }

    public void deleteBookById(Long id) {
        if (bookDao.deleteById(id) == 0) {
            throw new RuntimeException("Book wasn't deleted. Maybe it doesn't exist");
        }
    }

    public List<Book> getBooksByFilter(String name, List<String> languages, List<String> categories) {
        if (isBlank(name) && isEmpty(languages) && isEmpty(categories)) return bookDao.findAll();

        List<Book> result = new ArrayList<>();

        if (FALSE.equals(isEmpty(categories))) {
            result = bookDao.findByCategories(categories.stream().map(String::toLowerCase).toList());
            if (isEmpty(result)) return result;
        }

        if (FALSE.equals(isEmpty(languages))) {
            List<Book> booksByLanguage = bookDao.findByLanguages(languages.stream().map(String::toLowerCase).toList());
            result = result.isEmpty() ? booksByLanguage : result;
            result.retainAll(booksByLanguage);
            if (isEmpty(result)) return result;
        }

        if (isNotBlank(name)) {
            List<Book> booksByName = bookDao.findByName(name.toLowerCase());
            result = result.isEmpty() ? booksByName : result;
            result.retainAll(booksByName);
        }
        return result;
    }
}
