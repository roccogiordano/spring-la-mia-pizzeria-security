package org.lessons.java.spring.spring_la_mia_pizzeria_crud.repository;

import java.util.List;

import org.lessons.java.spring.spring_la_mia_pizzeria_crud.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Integer> {

    public List<Offer> findByNameContaining(String name);

}