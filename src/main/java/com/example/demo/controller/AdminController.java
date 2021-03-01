package com.example.demo.controller;

import com.example.demo.repository.dto.Book;
import com.example.demo.repository.dto.User;
import com.example.demo.service.BookService;
import com.example.demo.service.BookingService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private BookService bookService;
    private BookingService bookingService;

    @Autowired
    public AdminController(UserService userService, BookService bookService, BookingService bookingService) {
        this.userService = userService;
        this.bookService = bookService;
        this.bookingService = bookingService;
    }


    @GetMapping("/")
    public String adminPage(Model model){
        model.addAttribute("books",bookService.allBooks());
        model.addAttribute("users",userService.allUsers());

        return "admin";
    }

    /*ADD USER METHOD*/
    @PostMapping("/addUser")
    public String addUser(@RequestParam(name = "username")String username,@RequestParam(name = "name")String name,
                          @RequestParam(name = "surname")String surname,@RequestParam(name = "password")String password) {
        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setSurname(surname);
        user.setPassword(password);
        userService.addUser(user);
        return "admin";
    }

    /*GETMAPPING FROM ADMIN.HTML, RETURNS ANOTHER WEB PAGE WITH inputs*/
    @GetMapping("/{id}/edit")
    public String updateUserPage(@PathVariable(value = "id") String id, Model model){
        System.out.println("GET METHOD TEST");
        model.addAttribute("id", id);
        model.addAttribute("user", new User());
        return "updateUser";
    }

    /*POST METHOD FROM updateUSER*/
    @PostMapping("/{id}/edit")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable(value = "id")String id){
        System.out.println("POSTT METHOD TEST");
        userService.updateUser(id, user);
        return "admin";
    }

    /*GETMAPPING FROM ADMIN.HTML*/
    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable(value = "id") String id){
        System.out.println("DELETE METHOD TEST");
        userService.deleteUser(id);
        return "admin";
    }
    /*USER PAGE FOR ADMIN*/
    @GetMapping("/user/{id}")
    public String userPageForAdmin(@PathVariable(value = "id")String id, Model model){
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("books", bookingService.getBorrowedBooksOfUser(id));

        return "userPageForAdmin";
    }

    /*ADD USER METHOD*/
    @PostMapping("/addBook")
    public String addBook(@RequestParam(name = "isbn")String isbn,@RequestParam(name = "name")String name,
                          @RequestParam(name = "author")String author,@RequestParam(name = "counts")Integer counts){
        Book book = new Book();
        book.setIsbn(isbn);
        book.setName(name);
        book.setAuthor(author);
        book.setCount(counts);
        bookService.registerBook(book);
        return "admin";
    }
    /*GETMAPPING FROM ADMIN.HTML, RETURNS ANOTHER WEB PAGE WITH inputs*/
    @GetMapping("/book/{isbn}/edit")
    public String updateBookPage(@PathVariable(value = "isbn") String isbn, Model model){
        model.addAttribute("isbn", isbn);
        model.addAttribute("book", new Book());
        return "updateBook";
    }
    /*POST METHOD FROM updateUSER*/
    @PostMapping("/book/{isbn}/edit")
    public String updateBook(@ModelAttribute("user") Book book, @PathVariable(value = "isbn")String isbn){
        bookService.updateBook(isbn, book);
        return "admin";
    }

    /*GETMAPPING FROM ADMIN.HTML*/
    @GetMapping("book/{isbn}/delete")
    public String deleteBook(@PathVariable(value = "isbn") String isbn){
        bookService.deleteBook(isbn);
        return "admin";
    }




}
