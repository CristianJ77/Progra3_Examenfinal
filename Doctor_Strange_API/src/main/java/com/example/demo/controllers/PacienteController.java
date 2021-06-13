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

import com.example.demo.entities.Paciente;
import com.example.demo.repositories.PacienteRepository;

@RestController
@RequestMapping(value = "/pacientes")
public class PacienteController {
	
	@Autowired
	PacienteRepository repository;
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public Collection<Paciente> getListaPacientes(){
		Iterable<Paciente> listaPacientes = repository.findAll();
		
		return (Collection<Paciente>) listaPacientes;
		}
	
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Paciente getPaciente(@PathVariable(name = "id") Long id) {
		
		Optional<Paciente> paciente = repository.findById(id);
		Paciente result = null;
		if(paciente.isPresent()) {
			result = paciente.get();
		}
		return result;	
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Paciente createPaciente(@RequestBody Paciente paciente ) {
		Paciente nuevoPaciente = repository.save(paciente);
		return nuevoPaciente;
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void deletePaciente(@PathVariable(name = "id") Long id) {
		repository.deleteById(id);	
	}
	
	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public Paciente updatePaciente(@PathVariable(name = "id") Long id, 
			@RequestBody Paciente paciente) {
		Optional<Paciente> oPaciente = repository.findById(id);
		if(oPaciente.isPresent()) {
			Paciente actual = oPaciente.get(); 
			actual.setId(id);
			actual.setPrimer_nombre(paciente.getPrimer_nombre());
			actual.setSegundo_nombre(paciente.getSegundo_nombre());
			actual.setApellido(paciente.getApellido());
			actual.setSegundo_apellido(paciente.getSegundo_apellido());
			actual.setEdad(paciente.getEdad());
			Paciente updatedPaciente = repository.save(actual);
			return updatedPaciente;
		}
		return null;
	}
}
