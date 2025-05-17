package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;


@RestController
public class Books {
    HashMap<Integer, User> books = new HashMap<>();
    public Books(){
        books.put(1, new User(2005, "Преступление и наказание", "Ф.М. Достоевский"));
        books.put(2, new User(1993,"Миссия невыполнима", "Том Круз"));
        books.put(3, new User(200, "Анна Каренина", "Л.Н. Толстой"));
    }
    @GetMapping("/{id}")
    public ResponseEntity<? extends Object> getBooks(@PathVariable Integer id){
        if(books.containsKey(id)){
            return ResponseEntity.status(200).body(books.get(id));
        }
        else{
            return ResponseEntity.status(404).body("Книга не найдена");
        }
    }
    @GetMapping
    public HashMap<Integer, User> getAllBooks() {
        return books;
    }
    @PostMapping
    public ResponseEntity<String> AddBooks(@RequestBody User user){
        if(user.getId() == null || user.getTitle() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Не указано название или дата");
    }
        books.put(user.getId(), user);

        return ResponseEntity.status(HttpStatus.CREATED).body("книга добавлена");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteBooks(@PathVariable Integer id){
        books.remove(id);
        return ResponseEntity.status(200).body("Книга удалена");
    }
    @PutMapping
    public ResponseEntity<String> PutBooks(@RequestBody User user){
        books.replace(user.getId(), user);
        return ResponseEntity.status(200).body("Книга обновлена");
    }
}
