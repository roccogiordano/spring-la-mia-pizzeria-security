package org.lessons.java.spring.spring_la_mia_pizzeria_crud.controller;

import org.lessons.java.spring.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

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

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "create";
    }
 
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza pizza, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "create";
        }
        
        pizzaRepository.save(pizza);

        redirectAttributes.addFlashAttribute("message", String.format("Pizza %s has been succesfully added to the menu!", pizza.getName()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-success");

        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("pizza", pizzaRepository.findById(id).get());
        return "edit";
    }
 
    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza pizza, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "edit";
        }
        
        pizzaRepository.save(pizza);

        redirectAttributes.addFlashAttribute("message", String.format("Pizza %s has been succesfully edited!", pizza.getName()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-info");

        return "redirect:/pizzas";
    }

}
