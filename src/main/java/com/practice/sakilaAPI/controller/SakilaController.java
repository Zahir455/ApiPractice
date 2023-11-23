package com.practice.sakilaAPI.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.sakilaAPI.DAO.ActorDAO;
import com.practice.sakilaAPI.DTO.ActorDTO;
import com.practice.sakilaAPI.entity.Actor;
import com.practice.sakilaAPI.entity.Customer;
import com.practice.sakilaAPI.repo.ActorRepository;
import com.practice.sakilaAPI.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SakilaController {

    @Autowired
    private ActorRepository actorRepo;

    @Autowired
    private ActorDAO actorDAO;

    @GetMapping("/actor/{id}")
    public Actor getActorById(@PathVariable int id){
        Actor result = actorRepo.findById(id).get();
        return result;
    }

    @GetMapping("/actor/all")
    public List<Actor> getAllActors(){
        List<Actor> list = actorRepo.findAll();
        return list;
    }

    @DeleteMapping("/actor/{id}")
    public int deleteById(@PathVariable int id){
        Actor actor = actorRepo.findById(id).get();
        actorRepo.delete(actor);
        return actor.getId();
    }

    @PatchMapping("/actor/{id}/firstName/{newFirstname}")
    public ActorDTO updateFirstName(@PathVariable int id, @PathVariable String newFirstname){
 //       ActorDAO actorDAO = new ActorDAO(actorRepo);
        ActorDTO actorDTO = new ActorDTO(id, newFirstname, null);
        actorDTO = actorDAO.update(actorDTO);
        return actorDTO;
    }

    @GetMapping("/")
    public String basic(){
        return"<h1>Hello there</h1>";
    }

//    @GetMapping({"/customer", "/something"})
//    public Customer getCustomer(){
//        return new Customer("Houssam Eddine", "Bououdina", 5);
//    }

    @GetMapping("customer/{id}") //doesn't work
    public ResponseEntity<String> getCustomerByid(@PathVariable int id){
        CustomerRepo repo = CustomerRepo.getInstance();
        Customer cust = repo.get(id-1);
        ObjectMapper mapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> result = null;
        headers.add("content-type", "application/json");
        if(cust != null){
            try {
                result = new ResponseEntity<>(mapper.writeValueAsString(cust), headers, HttpStatus.OK);
            }catch (JsonProcessingException e){
                throw new RuntimeException();
            }
        } else{
            result=new ResponseEntity<>("{\"message\":\"Customer not found\"}",headers, HttpStatus.OK);
        }
        return result;
    }

    @GetMapping("/customer/all")
    public List<Customer> getAllCustomers(){
        CustomerRepo repo = CustomerRepo.getInstance();
        return repo.getList();
    }

    @PostMapping("/customer")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void newCustomer(@RequestBody Customer newCustomer){
        CustomerRepo repo = CustomerRepo.getInstance();
        repo.add(newCustomer);
        repo.toString();
    }

    @DeleteMapping("/customer/remove/{id}")
    public void removeCustomer(@PathVariable Integer id){
        CustomerRepo repo = CustomerRepo.getInstance();
        repo.remove(id);
    }

    @PatchMapping("/customer")
    public void editCustomer(String firstName,String lastName, int id){
        CustomerRepo repo = CustomerRepo.getInstance();
        repo.set(firstName, lastName, id);
        repo.toString();
    }

    @PutMapping("/customer/put")
    public void editOrAddcustomer(String firstName,String lastName, int id){
        CustomerRepo repo = CustomerRepo.getInstance();
        if(repo.check(id)){
            repo.set(firstName, lastName, id);
        } else{
            Customer customer = new Customer(firstName, lastName, id);
            repo.add(customer);
        }
    }

    @GetMapping("/actor/lastname/{lastName}")
    public List<Actor> getActorByLastName(@PathVariable String lastName){
        return actorRepo.findByLastName(lastName);
    }
}
