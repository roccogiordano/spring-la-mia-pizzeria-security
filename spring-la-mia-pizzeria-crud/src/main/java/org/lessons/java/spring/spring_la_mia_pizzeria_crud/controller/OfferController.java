package org.lessons.java.spring.spring_la_mia_pizzeria_crud.controller;

import org.lessons.java.spring.spring_la_mia_pizzeria_crud.model.Offer;
import org.lessons.java.spring.spring_la_mia_pizzeria_crud.repository.OfferRepository;
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
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    private OfferRepository offerRepository;
    
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("offer") Offer offer, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "/offers/create";
        }

        offerRepository.save(offer);

        redirectAttributes.addFlashAttribute("message", String.format("Offer %s has been successfully added to Pizza %s!", offer.getName(), offer.getPizza().getName()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-success");

        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("offer", offerRepository.findById(id).get());
        return "/offers/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("offer") Offer offer, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "/offers/edit";
        }

        offerRepository.save(offer);

        redirectAttributes.addFlashAttribute("message", String.format("Offer %s, for Pizza %s, has been successfully edited!", offer.getName(), offer.getPizza().getName()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-info");

        return "redirect:/pizzas";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        Offer offer = offerRepository.findById(id).get();
        offerRepository.delete(offer);

        redirectAttributes.addFlashAttribute("message", String.format("Offer %s has been succesfully deleted!", offer.getName()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-danger");

        return "redirect:/pizzas";
    }

}
