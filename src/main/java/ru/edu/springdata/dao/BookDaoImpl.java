package ru.edu.springdata.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.edu.springdata.entity.Book;

import java.util.List;
import java.util.Optional;

@Component
public class BookDaoImpl implements BookDao {

    private final SimpleJdbcInsert jdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BookDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("BOOKS")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Book> findAll() {
        return namedParameterJdbcTemplate.query(
                "SELECT * FROM BOOKS ORDER BY NAME",
                new BeanPropertyRowMapper<>(Book.class)
        );
    }

    @Override
    public Optional<Book> findById(long id) {
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject("SELECT * FROM BOOKS WHERE ID = :id",
                            new MapSqlParameterSource("id", id),
                            new BeanPropertyRowMapper<>(Book.class)
                    ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findByName(String name) {
        return namedParameterJdbcTemplate.query(
                "SELECT * FROM BOOKS WHERE LOWER(NAME) LIKE :name",
                new MapSqlParameterSource("name", "%" + name + "%"),
                new BeanPropertyRowMapper<>(Book.class)
        );
    }

    @Override
    public List<Book> findByCategories(List<String> categories) {
        return namedParameterJdbcTemplate.query(
                "SELECT * FROM BOOKS WHERE LOWER(CATEGORY) in (:categories)",
                new MapSqlParameterSource("categories", categories),
                new BeanPropertyRowMapper<>(Book.class)
        );
    }

    @Override
    public List<Book> findByLanguages(List<String> languages) {
        return namedParameterJdbcTemplate.query(
                "SELECT * FROM BOOKS WHERE LOWER(LANGUAGE) in (:languages)",
                new MapSqlParameterSource("languages", languages),
                new BeanPropertyRowMapper<>(Book.class)
        );
    }

    @Override
    public Long save(Book book) {
        return jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(book)).longValue();
    }

    @Override
    public int update(Book book) {
        return namedParameterJdbcTemplate.update(
                "UPDATE BOOKS SET NAME = :name, AUTHOR = :author, LANGUAGE = :language, CATEGORY = :category WHERE ID = :id",
                new BeanPropertySqlParameterSource(book)
        );
    }

    @Override
    public int deleteById(long id) {
        return namedParameterJdbcTemplate.update(
                "DELETE FROM BOOKS WHERE ID = :id",
                new MapSqlParameterSource("id", id)
        );
    }
}
