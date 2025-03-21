package org.lessons.java.spring.spring_la_mia_pizzeria_crud.controller;

import org.lessons.java.spring.spring_la_mia_pizzeria_crud.model.Offer;
import org.lessons.java.spring.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring.spring_la_mia_pizzeria_crud.repository.IngredientRepository;
import org.lessons.java.spring.spring_la_mia_pizzeria_crud.repository.OfferRepository;
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

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String view(Model model) {
        List<Pizza> pizzas = pizzaRepository.findAll();
        model.addAttribute("pizzas", pizzas);
        return "/pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("pizza", pizzaRepository.findById(id).get());
        return "/pizzas/show";
        
    }   

    @GetMapping("/search")
    public String search(@RequestParam(name = "name") String name, Model model) {
        List<Pizza> pizzas = pizzaRepository.findByNameContaining(name);
        model.addAttribute("pizzas", pizzas);
        return "/pizzas/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredients", ingredientRepository.findAll());
        return "/pizzas/create";
    }
 
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza pizza, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientRepository.findAll());
            return "/pizzas/create";
        }
        
        pizzaRepository.save(pizza);

        redirectAttributes.addFlashAttribute("message", String.format("Pizza %s has been succesfully added to the menu!", pizza.getName()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-success");

        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("pizza", pizzaRepository.findById(id).get());
        model.addAttribute("ingredients", ingredientRepository.findAll());
        return "/pizzas/edit";
    }
 
    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza pizza, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientRepository.findAll());
            return "/pizzas/edit";
        }
        
        pizzaRepository.save(pizza);

        redirectAttributes.addFlashAttribute("message", String.format("Pizza %s has been succesfully edited!", pizza.getName()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-info");

        return "redirect:/pizzas";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        Pizza pizza = pizzaRepository.findById(id).get();

        for (Offer offer : pizza.getOffers()) {
            offerRepository.delete(offer);
        }

        pizzaRepository.delete(pizza);

        redirectAttributes.addFlashAttribute("message", String.format("Pizza %s has been succesfully deleted!", pizza.getName()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-danger");

        return "redirect:/pizzas";
    }

    @GetMapping("/{id}/offer")
    public String offer(@PathVariable("id") Integer id, Model model) {

        Offer offer = new Offer();
        offer.setPizza(pizzaRepository.findById(id).get());
    
        model.addAttribute("offer", offer);
        return "/offers/create";
    }

}
