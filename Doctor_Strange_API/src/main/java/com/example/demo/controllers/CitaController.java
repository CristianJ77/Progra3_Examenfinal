package com.example.demo.controllers;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Cita;
import com.example.demo.repositories.CitaRepository;

@RestController
@RequestMapping(value = "/citas")
public class CitaController {
	
	@Autowired
	CitaRepository repository;
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public Collection<Cita> getListaCitas(){
		Iterable<Cita> listaCitas = repository.findAll();
		
		return (Collection<Cita>) listaCitas;
		}
	
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Cita getPaciente(@PathVariable(name = "id") Long id) {
		
		Optional<Cita> cita = repository.findById(id);
		Cita result = null;
		if(cita.isPresent()) {
			result = cita.get();
		}
		return result;	
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cita createCita(@RequestBody Cita cita ) {
		Cita nuevoCita = repository.save(cita);
		return nuevoCita;
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void deleteCita(@PathVariable(name = "id") Long id) {
		repository.deleteById(id);	
	}
	
	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public Cita updateCita(@PathVariable(name = "id") Long id, 
			@RequestBody Cita cita) {
		Optional<Cita> oCita = repository.findById(id);
		if(oCita.isPresent()) {
			Cita actual = oCita.get(); 
			actual.setId(id);
			actual.setFecha(cita.getFecha());
			actual.setHora(cita.getHora());
			actual.setPaciente(cita.getPaciente());
			actual.setEstado(cita.getEstado());
			actual.setObservaciones(cita.getObservaciones());
			Cita updatedCita = repository.save(actual);
			return updatedCita;
		}
		return null;
	}
}
