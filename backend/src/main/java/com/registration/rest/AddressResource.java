package com.registration.rest;

import com.registration.model.Address;
import com.registration.model.Cep;
import com.registration.repository.AddressRepository;
import com.registration.service.AddressService;
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

@RestController("address-resource")
@RequestMapping("/address")
@Api(tags = "address")
public class AddressResource {

    private final AddressRepository addressRepository;
    private final AddressService addressService;

    public AddressResource(
            AddressRepository addressRepository,
            AddressService addressService
    ) {
        this.addressRepository = addressRepository;
        this.addressService = addressService;
    }

    @PostMapping("")
    @CrossOrigin
    @Transactional
    @ApiOperation("Register of an address")
    public Address create(@Valid @RequestBody Address address) {
        return this.addressService.create(address);
    }

    @PutMapping("/{id:[0-9]+}")
    @ApiOperation("Updates address by id")
    @CrossOrigin
    public Address update(
            @ApiParam(name = "id", value = "address id")
            @PathVariable("id") Long id,
            @Valid @RequestBody Address address) {
        return this.addressService.save(address, id);
    }

    @DeleteMapping("/{id:[0-9]+}")
    @ApiOperation("Deletes an address by id")
    @CrossOrigin
    @Transactional
    public ResponseEntity<Address> delete(
            @ApiParam(name = "id", value = "address id")
            @PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.addressRepository.remover(id, "Address not found"));
    }

    @GetMapping("")
    @ApiParam("List of addresses")
    @CrossOrigin
    public Page<Address> list(AddressResource.Filter filter) {
        return this.addressRepository.listar(filter.specification(), filter.pagination());
    }

    @GetMapping("/{id:[0-9]+}")
    @CrossOrigin
    @ApiOperation("Retrieve address by id")
    public Address retrieve(
            @ApiParam(name = "id", value = "address id", required = true)
            @PathVariable("id") Long id
    ) {
        return this.addressRepository.obter(id, true);
    }

    @GetMapping("/cep/{cep:[0-9]+}")
    @CrossOrigin
    @ApiOperation("Retrieve cep")
    public Cep retrieveCep(
            @ApiParam(name = "cep", value = "cep", required = true)
            @PathVariable("cep") String cep
    ) {
        return this.addressService.getCep(cep);
    }

    @ApiModel("Addresses filters")
    @Data
    class Filter {
        @ApiParam(value = "Page", defaultValue = "0")
        Integer page = 0;

        @ApiParam(value = "Items per page (default 10)", defaultValue = "10")
        Integer count = 10;

        public Specification<Address> specification() {
            Specification<Address> spec = Specification.where(null);
            return spec;
        }

        public PageRequest pagination() {
            return PageRequest.of(this.page, this.count);
        }
    }
}
