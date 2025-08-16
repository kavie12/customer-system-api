package com.kavie12.customer_system_api.controllers;

import com.kavie12.customer_system_api.dto.request.CustomerRequestDto;
import com.kavie12.customer_system_api.dto.response.StandardResponseDto;
import com.kavie12.customer_system_api.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<StandardResponseDto> add(@Valid @RequestBody CustomerRequestDto dto) {
        customerService.add(dto);
        return new ResponseEntity<>(
            new StandardResponseDto(201, "Customer Added.", null),
            HttpStatus.CREATED
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StandardResponseDto> update(@Valid @RequestBody CustomerRequestDto dto, @PathVariable("id") int customerId) {
        customerService.update(dto, customerId);
        return new ResponseEntity<>(
                new StandardResponseDto(200, "Customer Updated.", null),
                HttpStatus.OK
        );
    }

    @PutMapping("/update-authenticated")
    public ResponseEntity<StandardResponseDto> update(@Valid @RequestBody CustomerRequestDto dto) {
        customerService.updateAuthenticated(dto);
        return new ResponseEntity<>(
                new StandardResponseDto(200, "Customer Updated.", null),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponseDto> delete(@PathVariable("id") int customerId) {
        customerService.delete(customerId);
        return new ResponseEntity<>(
                new StandardResponseDto(200, "Customer Deleted.", null),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete-authenticated")
    public ResponseEntity<StandardResponseDto> deleteAuthenticated() {
        customerService.deleteAuthenticated();
        return new ResponseEntity<>(
                new StandardResponseDto(200, "Customer Deleted.", null),
                HttpStatus.OK
        );
    }

    @GetMapping("/find-authenticated")
    public ResponseEntity<StandardResponseDto> findAuthenticated() {
        return new ResponseEntity<>(
                new StandardResponseDto(200, "Customer Found.", customerService.findAuthenticated()),
                HttpStatus.OK
        );
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<StandardResponseDto> find(@PathVariable("id") int customerId) {
        return new ResponseEntity<>(
                new StandardResponseDto(200, "Customer Found.", customerService.findById(customerId)),
                HttpStatus.OK
        );
    }

    @GetMapping("/find-all")
    public ResponseEntity<StandardResponseDto> findAll(@RequestParam int page, @RequestParam int size, @RequestParam String searchKey) {
        return new ResponseEntity<>(
                new StandardResponseDto(200, "Customers Found.", customerService.findAll(page, size, searchKey)),
                HttpStatus.OK
        );
    }
}
