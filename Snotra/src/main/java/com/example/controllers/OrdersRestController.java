package com.example.controllers;

import com.example.models.BookModel;
import com.example.services.BooksDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersRestController {

    private final BooksDataService booksDataService;

    @Autowired
    public OrdersRestController(BooksDataService booksDataService) {
        this.booksDataService = booksDataService;
    }

    @GetMapping("/{id}")
    public BookModel getById(@PathVariable long id) {
        return booksDataService.getById(id);
    }

    @GetMapping
    public List<BookModel> getAllOrders() {
        return booksDataService.getOrders();
    }

    @GetMapping("/search")
    public List<BookModel> searchOrders(@RequestParam String term) {
        return booksDataService.searchOrders(term);
    }

    @PostMapping
    public long addOrder(@RequestBody BookModel newOrder) {
        return booksDataService.addOne(newOrder);
    }

    @GetMapping("/delete/{id}")
    public boolean deleteOrder(@PathVariable long id) {
        return booksDataService.deleteOne(id);
    }

    @PutMapping("/update/{id}")
    public BookModel updateOrder(@PathVariable long id, @RequestBody BookModel updateOrder) {
        return booksDataService.updateOne(id, updateOrder);
    }
}