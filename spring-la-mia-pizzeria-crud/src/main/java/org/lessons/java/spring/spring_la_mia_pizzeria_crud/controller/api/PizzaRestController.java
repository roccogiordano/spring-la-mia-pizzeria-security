package org.lessons.java.spring.spring_la_mia_pizzeria_crud.controller.api;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/pizzas")
public class PizzaRestController {
    
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public List<Pizza> index() {
        return pizzaRepository.findAll();
    }

    @GetMapping("/search")
    public List<Pizza> search(@RequestParam(name = "name") String name) {
        return pizzaRepository.findByNameContaining(name);
    }

    @GetMapping("{id}")
    public ResponseEntity<Pizza> show(@PathVariable Integer id) {
        Optional<Pizza> pizzaAttempt = pizzaRepository.findById(id);

        if (pizzaAttempt.isPresent()) {
            return new ResponseEntity<Pizza>(pizzaAttempt.get(), HttpStatus.OK);
        }

        return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Pizza> store(@Valid @RequestBody Pizza pizza) {
        return new ResponseEntity<Pizza>(pizzaRepository.save(pizza), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Pizza> update(@PathVariable Integer id, @Valid @RequestBody Pizza pizza) {
        Optional<Pizza> pizzaAttempt = pizzaRepository.findById(id);

        if (pizzaAttempt.isPresent()) {
            return new ResponseEntity<Pizza>(pizzaRepository.save(pizza), HttpStatus.OK);
        }

        return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Pizza> delete(@PathVariable Integer id) {
        Optional<Pizza> pizzaAttempt = pizzaRepository.findById(id);

        if (pizzaAttempt.isPresent()) {
            pizzaRepository.delete(pizzaAttempt.get());
            return new ResponseEntity<Pizza>(HttpStatus.OK);
        }

        return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
    }

}
