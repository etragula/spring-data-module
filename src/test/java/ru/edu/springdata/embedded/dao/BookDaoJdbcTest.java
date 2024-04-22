package ru.edu.springdata.embedded.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import ru.edu.springdata.dao.BookDao;
import ru.edu.springdata.dao.BookDaoImpl;
import ru.edu.springdata.embedded.EmbeddedDatabaseAbstractTest;
import ru.edu.springdata.entity.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(classes = BookDaoImpl.class)
class BookDaoJdbcTest extends EmbeddedDatabaseAbstractTest {

    @Autowired
    private BookDao bookDao;

    @Test
    void shouldSaveBook() {
        var expectedBook = new Book(null, "The Terror", "Dan Simmons", "English", "Horror");

        var id = bookDao.save(expectedBook);

        var actualBook = bookDao.findById(id).orElse(null);
        assertThat(actualBook)
                .usingRecursiveComparison().ignoringFields("id")
                .isEqualTo(expectedBook);
    }

    @Test
    void shouldDeleteBook() {
        long id = bookDao.findAll().stream().map(Book::getId).findAny().orElse(-1L);

        bookDao.deleteById(id);

        assertTrue(bookDao.findById(id).isEmpty());
    }

    @Test
    void shouldUpdateBook() {
        var id = bookDao.save(new Book(null, "Old Surehand I", "Karl May", "German", "Adventure"));

        var bookOpt = bookDao.findById(id);
        assertTrue(bookOpt.isPresent());

        var expectedBook = bookOpt.get();
        expectedBook.setName("The Green Mile");
        expectedBook.setAuthor("Stephen King");
        expectedBook.setLanguage("English");
        expectedBook.setCategory("Fantasy");
        bookDao.update(expectedBook);

        bookOpt = bookDao.findById(id);
        assertTrue(bookOpt.isPresent());
        var actualBook = bookOpt.get();
        assertThat(actualBook)
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }
}
