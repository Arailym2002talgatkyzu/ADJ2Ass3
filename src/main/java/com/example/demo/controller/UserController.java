package com.example.demo.controller;

import com.example.demo.repository.dto.Book;
import com.example.demo.repository.dto.User;
import com.example.demo.service.BookService;
import com.example.demo.service.BookingService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping()
public class UserController {
    private UserService userService;
    private BookService bookService;
    private BookingService bookingService;

    @Autowired
    public UserController(UserService userService, BookService bookService, BookingService bookingService) {
        this.userService = userService;
        this.bookService = bookService;
        this.bookingService = bookingService;
    }

    /*RETURN USER PAGE OR ADMIN PAGE AFTER AUTHENTICATION*/
    @GetMapping("/userPage")
    public String successfulLoginPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserById(username);
        user.setPassword(null);

        model.addAttribute("user", user);
        model.addAttribute("users",userService.allUsers());
        model.addAttribute("allbooks",bookService.allBooks());
        model.addAttribute("books",bookingService.getBorrowedBooksOfUser(username));

        /*If user is admin return admin page, else return user page*/
        if (user.getRole().getRolename().equals("ROLE_ADMIN")) return "admin";
        return "userPage";
    }

    /*Return particular book page*/
    @GetMapping("book/{isbn}")
    public String bookPage(@PathVariable(value = "isbn") String isbn, Model model){
        model.addAttribute("book", bookService.getIsbn(isbn));
        return "bookPage";
    }

    /*GETMAPPING FROM userPage.html, RETURNS ANOTHER WEB PAGE WITH inputs*/
    @GetMapping("/borrow/{id}")
    public String borrowBook(@PathVariable(value = "id") String id, Model model) {
        model.addAttribute("books", bookService.allBooks());
        return "borrowBook";
    }

    /*POST METHOD FROM borrowBook*/
    @PostMapping("/borrow/{id}")
    public String borrowBook(@PathVariable(value = "id") String id, @RequestParam("isbn") List<String> books) {
        User user = userService.getUserById(id);
        ArrayList<Book> bookArrayList = bookService.getBooksByIsbn(books);
        bookingService.borrowBook(user, bookArrayList);
        System.out.println("CHECK THE METHOD");
        return "redirect:userPage";
    }

}
