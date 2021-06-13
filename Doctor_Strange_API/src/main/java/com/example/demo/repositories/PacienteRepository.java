package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;


import com.example.demo.entities.Paciente;

public interface PacienteRepository extends CrudRepository<Paciente, Long> {

}

