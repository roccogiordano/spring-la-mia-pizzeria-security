package org.lessons.java.spring.spring_la_mia_pizzeria_crud.controller;

import org.lessons.java.spring.spring_la_mia_pizzeria_crud.model.Offer;
import org.lessons.java.spring.spring_la_mia_pizzeria_crud.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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

        redirectAttributes.addFlashAttribute("message", String.format("Offer %s created successfully", offer.getName()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-success");

        return "redirect:/pizzas";
    }

}
