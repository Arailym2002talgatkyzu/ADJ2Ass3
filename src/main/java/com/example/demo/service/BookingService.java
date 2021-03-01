package com.example.demo.service;

import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.dto.Book;
import com.example.demo.repository.dto.Booking;
import com.example.demo.repository.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class BookingService {
    private BookingRepository bookingRepository;
    private BookRepository bookRepository;
    private UserRepository userRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    /*Returns all booksings*/
    public Iterable<Booking> allBookings (){
        Iterable<Booking> bookings = bookingRepository.findAll();
        return bookings;
    }

    /*Adds a particular book to user*/
    public void borrowBook(User user, ArrayList<Book> books){
        for (Book book: books){
            Booking booking = new Booking();
            booking.setBook(book);
            booking.setUser(user);
            booking.setStatus("Borrowed");
            booking.setDate(new Date());
            book.setCount(book.getCount()-1);
            bookRepository.save(book);
            bookingRepository.save(booking);
        }
    }


    /*Show all books of particular user*/
    public ArrayList<Book> getBorrowedBooksOfUser(String id){
        User user = userRepository.findById(id).get();
        ArrayList<Booking> borrows = (ArrayList<Booking>) bookingRepository.findAllByUser(user);
        ArrayList<Book> books = new ArrayList<>();
        for(Booking booking : borrows){
            if(booking.getStatus().equals("Borrowed")){
                books.add(booking.getBook());
            }
        }
        return books;
    }
}
