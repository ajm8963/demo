package com.example.demo;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;


@RestController
@Slf4j
public class Books {
    HashMap<Integer, User> books = new HashMap<>();
    public Books(){
        books.put(1, new User(2005, "Преступление и наказание", "Ф.М. Достоевский","0486404512"));
        books.put(2, new User(1993,"Миссия невыполнима", "Том Круз","0486404522"));
        books.put(3, new User(200, "Анна Каренина", "Л.Н. Толстой", "0486404513"));
    }
    @GetMapping("/{id}")
    public ResponseEntity<? extends Object> getBooks(@PathVariable Integer id){
        log.info("Вызвал книгу под номером " + id);
        if(books.containsKey(id)){
            return ResponseEntity.status(200).body(books.get(id));

        }
        else{
            return ResponseEntity.status(404).body("Книга не найдена");
        }
    }
    @GetMapping
    public HashMap<Integer, User> getAllBooks() {
        log.info("Вызвал все книги");
        return books;
    }
    @PostMapping
    public ResponseEntity<String> AddBooks(@RequestBody User user){
        log.info("Добавил книгу");
        if (user.getId() == null || user.getTitle() == null || !ISNBValidator.isValidISBN(user.getISBN())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неверный формат ISBN или данные");
        }
        books.put(user.getId(), user);
        return ResponseEntity.status(HttpStatus.CREATED).body("книга добавлена");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteBooks(@PathVariable Integer id){
        log.info("Удалил книгу под номером " + id);
        books.remove(id);
        return ResponseEntity.status(200).body("Книга удалена");
    }
    @PutMapping
    public ResponseEntity<String> PutBooks(@RequestBody User user){
        log.info("Обновление книги");
        if (user.getId() == null || user.getTitle() == null || !ISNBValidator.isValidISBN(user.getISBN())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неверный формат ISBN или данные");
        }
        books.replace(user.getId(), user);
        return ResponseEntity.status(200).body("Книга обновлена");
    }
    @GetMapping("/author")
    public ResponseEntity<List<User>> getBooksByAuthor(@RequestParam String name) {
        log.info("Искал книгу под названием " + name);
        List<User> result = books.values().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(name))
                .toList();

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(result);
    }
}
