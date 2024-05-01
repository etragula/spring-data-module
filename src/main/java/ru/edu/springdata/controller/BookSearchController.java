package ru.edu.springdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edu.springdata.entity.Book;
import ru.edu.springdata.service.BookService;

import java.util.List;

@Controller
public class BookSearchController {

    private final BookService bookService;

    public BookSearchController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping({"/", "/search"})
    public String getBooksByFilters(
            Model model,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<String> languages,
            @RequestParam(required = false) List<String> categories
    ) {
        List<Book> books = bookService.getBooksByFilter(
                name,
                languages,
                categories
        );
        model.addAttribute("books", books);
        return "index";
    }
}
