package com.registration.rest;

import com.registration.model.Email;
import com.registration.repository.EmailRepository;
import com.registration.service.EmailService;
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

@RestController("email-resource")
@RequestMapping("/email")
@Api(tags = "email")
public class EmailResource {

    private final EmailRepository emailRepository;
    private final EmailService emailService;

    public EmailResource(
            EmailRepository emailRepository,
            EmailService emailService
    ) {
        this.emailRepository = emailRepository;
        this.emailService = emailService;
    }

    @PostMapping("")
    @CrossOrigin
    @Transactional
    @ApiOperation("Register of an email")
    public Email create(@Valid @RequestBody Email email) {
        return this.emailService.criar(email);
    }

    @PutMapping("/{id:[0-9]+}")
    @ApiOperation("Updates email by id")
    @CrossOrigin
    public Email update(
            @ApiParam(name = "id", value = "email id")
            @PathVariable("id") Long id,
            @Valid @RequestBody Email email) {
        return this.emailService.save(email, id);
    }

    @DeleteMapping("/{id:[0-9]+}")
    @ApiOperation("Deletes an email by id")
    @CrossOrigin
    @Transactional
    public ResponseEntity<Email> delete(
            @ApiParam(name = "id", value = "email id")
            @PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.emailService.delete(id));
    }

    @GetMapping("")
    @ApiParam("List of emails")
    @CrossOrigin
    public Page<Email> list(EmailResource.Filter filter) {
        return this.emailRepository.listar(filter.specification(), filter.pagination());
    }

    @GetMapping("/{id:[0-9]+}")
    @CrossOrigin
    @ApiOperation("Retrieve email by id")
    public Email retrieve(
            @ApiParam(name = "id", value = "email id", required = true)
            @PathVariable("id") Long id
    ) {
        return this.emailRepository.obter(id, true);
    }

    @ApiModel("Email filters")
    @Data
    class Filter {
        @ApiParam(value = "Page", defaultValue = "0")
        Integer page = 0;

        @ApiParam(value = "Items per page (default 10)", defaultValue = "10")
        Integer count = 10;

        public Specification<Email> specification() {
            Specification<Email> spec = Specification.where(null);
            return spec;
        }

        public PageRequest pagination() {
            return PageRequest.of(this.page, this.count);
        }
    }
}
