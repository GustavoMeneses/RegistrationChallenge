package com.registration.rest;

import com.registration.model.PersonPhone;
import com.registration.repository.PersonPhoneRepository;
import com.registration.service.PersonPhoneService;
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

@RestController("person-phone-resource")
@RequestMapping("/person-phone")
@Api(tags = "person-phone")
public class PersonPhoneResource {

    private final PersonPhoneRepository personPhoneRepository;
    private final PersonPhoneService personPhoneService;

    public PersonPhoneResource(
            PersonPhoneRepository personPhoneRepository,
            PersonPhoneService personPhoneService
    ) {
        this.personPhoneRepository = personPhoneRepository;
        this.personPhoneService = personPhoneService;
    }

    @PostMapping("")
    @CrossOrigin
    @Transactional
    @ApiOperation("Register of a person-phone")
    public PersonPhone create(@Valid @RequestBody PersonPhone personPhone) {
        return this.personPhoneService.criar(personPhone);
    }

    @DeleteMapping("/{id:[0-9]+}")
    @ApiOperation("Deletes an person-phone relation by id")
    @CrossOrigin
    @Transactional
    public ResponseEntity<PersonPhone> delete(
            @ApiParam(name = "id", value = "person-phone id")
            @PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.personPhoneRepository.remover(id, "Person-phone not found"));
    }


    @GetMapping("")
    @ApiParam("List of person-phone")
    @CrossOrigin
    public Page<PersonPhone> list(PersonPhoneResource.Filter filter) {
        return this.personPhoneRepository.listar(filter.specification(), filter.pagination());
    }

    @GetMapping("/{id:[0-9]+}")
    @CrossOrigin
    @ApiOperation("Retrieve person-phone by id")
    public PersonPhone retrieve(
            @ApiParam(name = "id", value = "person-phone id", required = true)
            @PathVariable("id") Long id
    ) {
        return this.personPhoneRepository.obter(id, true);
    }

    @ApiModel("Person-phone filters")
    @Data
    class Filter {
        @ApiParam(value = "Page", defaultValue = "0")
        Integer page = 0;

        @ApiParam(value = "Items per page (default 10)", defaultValue = "10")
        Integer count = 10;

        public Specification<PersonPhone> specification() {
            Specification<PersonPhone> spec = Specification.where(null);
            return spec;
        }

        public PageRequest pagination() {
            return PageRequest.of(this.page, this.count);
        }
    }
}
