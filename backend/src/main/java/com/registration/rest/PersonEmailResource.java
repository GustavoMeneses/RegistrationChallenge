package com.registration.rest;

import com.registration.model.PersonEmail;
import com.registration.repository.PersonEmailRepository;
import com.registration.service.PersonEmailService;
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

@RestController("person-email-resource")
@RequestMapping("/person-email")
@Api(tags = "person-email")
public class PersonEmailResource {

    private final PersonEmailRepository personEmailRepository;
    private final PersonEmailService personEmailService;

    public PersonEmailResource(
            PersonEmailRepository personEmailRepository,
            PersonEmailService personEmailService
    ) {
        this.personEmailRepository = personEmailRepository;
        this.personEmailService = personEmailService;
    }

    @PostMapping("")
    @CrossOrigin
    @Transactional
    @ApiOperation("Register of a person-email")
    public PersonEmail create(@Valid @RequestBody PersonEmail personEmail) {
        return this.personEmailService.criar(personEmail);
    }

    @DeleteMapping("/{id:[0-9]+}")
    @ApiOperation("Deletes an person-email relation by id")
    @CrossOrigin
    @Transactional
    public ResponseEntity<PersonEmail> delete(
            @ApiParam(name = "id", value = "person-email id")
            @PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.personEmailRepository.remover(id, "Person-email not found"));
    }


    @GetMapping("")
    @ApiParam("List of person-email")
    @CrossOrigin
    public Page<PersonEmail> list(PersonEmailResource.Filter filter) {
        return this.personEmailRepository.listar(filter.specification(), filter.pagination());
    }

    @GetMapping("/{id:[0-9]+}")
    @CrossOrigin
    @ApiOperation("Retrieve person-email by id")
    public PersonEmail retrieve(
            @ApiParam(name = "id", value = "person-email id", required = true)
            @PathVariable("id") Long id
    ) {
        return this.personEmailRepository.obter(id, true);
    }

    @ApiModel("Person-email filters")
    @Data
    class Filter {
        @ApiParam(value = "Page", defaultValue = "0")
        Integer page = 0;

        @ApiParam(value = "Items per page (default 10)", defaultValue = "10")
        Integer count = 10;

        public Specification<PersonEmail> specification() {
            Specification<PersonEmail> spec = Specification.where(null);
            return spec;
        }

        public PageRequest pagination() {
            return PageRequest.of(this.page, this.count);
        }
    }
}
