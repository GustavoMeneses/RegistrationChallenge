package com.registration.rest;

import com.registration.model.Phone;
import com.registration.repository.PhoneRepository;
import com.registration.service.PhoneService;
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

@RestController("phone-resource")
@RequestMapping("/phone")
@Api(tags = "phone")
public class PhoneResource {

    private final PhoneRepository phoneRepository;
    private final PhoneService phoneService;

    public PhoneResource(
            PhoneRepository phoneRepository,
            PhoneService phoneService
    ) {
        this.phoneRepository = phoneRepository;
        this.phoneService = phoneService;
    }

    @PostMapping("")
    @CrossOrigin
    @Transactional
    @ApiOperation("Register of a phone")
    public Phone create(@Valid @RequestBody Phone phone) {
        return this.phoneService.criar(phone);
    }

    @PutMapping("/{id:[0-9]+}")
    @ApiOperation("Updates phone by id")
    @CrossOrigin
    public Phone update(
            @ApiParam(name = "id", value = "phone id")
            @PathVariable("id") Long id,
            @Valid @RequestBody Phone phone) {
        return this.phoneService.save(phone, id);
    }

    @DeleteMapping("/{id:[0-9]+}")
    @ApiOperation("Deletes a phone by id")
    @CrossOrigin
    @Transactional
    public ResponseEntity<Phone> delete(
            @ApiParam(name = "id", value = "phone id")
            @PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.phoneService.delete(id));
    }

    @GetMapping("")
    @ApiParam("List of phones")
    @CrossOrigin
    public Page<Phone> list(PhoneResource.Filter filter) {
        return this.phoneRepository.listar(filter.specification(), filter.pagination());
    }

    @GetMapping("/{id:[0-9]+}")
    @CrossOrigin
    @ApiOperation("Retrieve phone by id")
    public Phone retrieve(
            @ApiParam(name = "id", value = "phone id", required = true)
            @PathVariable("id") Long id
    ) {
        return this.phoneRepository.obter(id, true);
    }

    @ApiModel("Phone filters")
    @Data
    class Filter {
        @ApiParam(value = "Page", defaultValue = "0")
        Integer page = 0;

        @ApiParam(value = "Items per page (default 10)", defaultValue = "10")
        Integer count = 10;

        public Specification<Phone> specification() {
            Specification<Phone> spec = Specification.where(null);
            return spec;
        }

        public PageRequest pagination() {
            return PageRequest.of(this.page, this.count);
        }
    }
}
