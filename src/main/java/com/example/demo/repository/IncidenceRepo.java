package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Incidence;

public interface IncidenceRepo extends JpaRepository<Incidence, Long>{

}
