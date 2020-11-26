package com.registration.rest;

import com.registration.model.Person;
import com.registration.repository.PersonRepository;
import com.registration.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController("person-resource")
@RequestMapping("/person")
@Api(tags = "person")
public class PersonResource {

    private final PersonRepository personRepository;
    private final PersonService personService;

    public PersonResource(
            PersonRepository personRepository,
            PersonService personService
    ) {
        this.personRepository = personRepository;
        this.personService = personService;
    }

    @PostMapping("")
    @CrossOrigin
    @Transactional
    @ApiOperation("Register of a person")
    public Person create(@Valid @RequestBody Person person) {
        return this.personService.criar(person);
    }

    @PutMapping("/{id:[0-9]+}")
    @ApiOperation("Updates person by id")
    @CrossOrigin
    @Transactional
    public Person update(
            @ApiParam(name = "id", value = "person id")
            @PathVariable("id") Long id,
            @Valid @RequestBody Person person) {
        return this.personService.save(person, id);
    }

    @DeleteMapping("/{id:[0-9]+}")
    @ApiOperation("Deletes a person by id")
    @CrossOrigin
    @Transactional
    public ResponseEntity<Person> delete(
            @ApiParam(name = "id", value = "person id")
            @PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.personService.delete(id));
    }

    @GetMapping("")
    @ApiParam("List of people")
    @CrossOrigin
    public Page<Person> list(PersonResource.Filter filter) {
        return this.personRepository.listar(filter.specification(), filter.pagination());
    }

    @GetMapping("/{id:[0-9]+}")
    @CrossOrigin
    @ApiOperation("Retrieve person by id")
    public Person retrieve(
            @ApiParam(name = "id", value = "person id", required = true)
            @PathVariable("id") Long id
    ) {
        return this.personRepository.obter(id, true);
    }

    @ApiModel("Person filters")
    @Data
    class Filter {
        @ApiParam(value = "Page", defaultValue = "0")
        Integer page = 0;

        @ApiParam(value = "Items per page (default 10)", defaultValue = "10")
        Integer count = 10;

        public Specification<Person> specification() {
            Specification<Person> spec = Specification.where(null);
            return spec;
        }

        public PageRequest pagination() {
            return PageRequest.of(this.page, this.count);
        }
    }
}
