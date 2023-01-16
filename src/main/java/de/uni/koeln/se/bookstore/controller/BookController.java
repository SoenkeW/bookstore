package de.uni.koeln.se.bookstore.controller;

import de.uni.koeln.se.bookstore.datamodel.Book;
import de.uni.koeln.se.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/bookstore")
@RestController
public class BookController {
    @Autowired
    BookService bookSer;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books;
        books = bookSer.findBooks();

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        return new ResponseEntity<>(bookSer.fetchBook(id).get(), HttpStatus.OK);

    }
    @PostMapping public ResponseEntity<Book> addBook(@RequestBody Book book) {

        bookSer.addBook(book);

        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }
    @PostMapping("/addbooks")
        public ResponseEntity<?> addBooks(@RequestBody List<Book> newBooks) {

        for (Book book:newBooks ) {
            bookSer.addBook(book);

        }

        return new ResponseEntity<>(newBooks, HttpStatus.CREATED);}

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> removeBookById(@PathVariable int id) {
        Book book = bookSer.fetchBook(id).get();
        if (bookSer.deleteBook(id)) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(book, HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/id/year")
    public ResponseEntity<List<Book>> getLatestBook(){
        List<Book> books;
        books = bookSer.findBooks();
        Integer latestYear = 0;
        Integer latestId = 0;
        Integer oldestYear = 0;
        Integer oldestId = 0;

        for (Book book:books) {
            if(book.getYear()>latestYear){
                latestYear = book.getYear();
                latestId = book.getId();
            }
            if(book.getYear()<oldestYear||oldestYear==0){
                oldestYear = book.getYear();
                oldestId = book.getId();

            }

        }
        List<Book> edgeYears = new ArrayList<>();
        edgeYears.add(bookSer.fetchBook(latestId).get());
        edgeYears.add(bookSer.fetchBook(oldestId).get());
        return new ResponseEntity<>(edgeYears, HttpStatus.OK);

    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Book> getoldestBook(){
//        List<Book> books;
//        books = bookSer.findBooks();
//        Integer year = 3000;
//        Integer id = 0;
//        for (Book book:books) {
//            if(book.getYear()<year){
//                year = book.getYear();
//                id = book.getId();
//            }
//
//        }
//        return new ResponseEntity<>(bookSer.fetchBook(id).get(), HttpStatus.OK);
//
//    }
}


