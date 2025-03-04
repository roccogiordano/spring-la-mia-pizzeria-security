package org.lessons.java.spring.spring_la_mia_pizzeria_crud.controller;

import java.util.List;

import org.lessons.java.spring.spring_la_mia_pizzeria_crud.model.Ingredient;
import org.lessons.java.spring.spring_la_mia_pizzeria_crud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepository;
    
    @GetMapping
    public String view(Model model) {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        model.addAttribute("ingredients", ingredients);
        return "/ingredients/index";
    }
    
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "/ingredients/create";
    }
 
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ingredient") Ingredient ingredient, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "/ingredients/create";
        }
        
        ingredientRepository.save(ingredient);

        redirectAttributes.addFlashAttribute("message", String.format("Ingredient %s has been succesfully added to the menu!", ingredient.getName()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-success");

        return "redirect:/ingredients";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ingredient", ingredientRepository.findById(id).get());
        return "/ingredients/edit";
    }
 
    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("ingredient") Ingredient ingredient, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "/ingredients/edit";
        }
        
        ingredientRepository.save(ingredient);

        redirectAttributes.addFlashAttribute("message", String.format("Ingredient %s has been succesfully edited!", ingredient.getName()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-info");

        return "redirect:/ingredients";
    }

        @PostMapping("/delete/{id}")
        public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        Ingredient ingredient = ingredientRepository.findById(id).get();
        ingredientRepository.delete(ingredient);

        redirectAttributes.addFlashAttribute("message", String.format("Ingredient %s has been succesfully deleted!", ingredient.getName()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-danger");

        return "redirect:/ingredients";
    }

}
