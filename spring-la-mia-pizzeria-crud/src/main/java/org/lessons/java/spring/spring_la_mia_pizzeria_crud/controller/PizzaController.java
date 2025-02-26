package org.lessons.java.spring.spring_la_mia_pizzeria_crud.controller;

import org.lessons.java.spring.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String getPizzas(Model model) {
        List<Pizza> pizzas = pizzaRepository.findAll();
        model.addAttribute("pizzas", pizzas);
        return "index";
    }

    @GetMapping("/{id}")
    public String getPizzaById(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("pizza", pizzaRepository.findById(id).get());
        return "/show";
        
    }   

    @GetMapping("/search")
    public String findPizzaByName(@RequestParam(name = "name") String name, Model model) {
        List<Pizza> pizzas = pizzaRepository.findByNameContaining(name);
        model.addAttribute("pizzas", pizzas);
        return "index";
    }
}
