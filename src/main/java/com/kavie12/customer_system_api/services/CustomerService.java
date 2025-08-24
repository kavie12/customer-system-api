package com.kavie12.customer_system_api.services;

import com.kavie12.customer_system_api.dto.request.CustomerRequestDto;
import com.kavie12.customer_system_api.dto.response.CustomerResponseDto;
import com.kavie12.customer_system_api.dto.response.paginate.CustomerPaginateResponseDto;
import com.kavie12.customer_system_api.entities.Customer;
import com.kavie12.customer_system_api.entities.User;
import com.kavie12.customer_system_api.exception.UserAlreadyExistsException;
import com.kavie12.customer_system_api.exception.NoSuchUserExistsException;
import com.kavie12.customer_system_api.repositories.CustomerRepository;
import com.kavie12.customer_system_api.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    public void add(CustomerRequestDto dto) {
        Customer customer = toCustomer(dto);

        try {
            customerRepository.save(customer);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException("A customer with the same username is already registered.");
        }
    }

    public void update(CustomerRequestDto dto, int id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NoSuchUserExistsException("Customer not found."));

        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setUpdatedAt(LocalDateTime.now());

        customerRepository.save(customer);
    }

    public void updateAuthenticated(CustomerRequestDto dto) {
        update(dto, getAuthenticatedCustomer().getId());
    }

    public void delete(int id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NoSuchUserExistsException("Customer not found."));
        customerRepository.delete(customer);
    }

    public void deleteAuthenticated() {
        delete(getAuthenticatedCustomer().getId());
    }

    public CustomerResponseDto findAuthenticated() {
        return toCustomerResponseDto(getAuthenticatedCustomer());
    }

    public CustomerResponseDto findById(int id) {
        Customer selectedCustomer = customerRepository.findById(id).orElseThrow(() -> new NoSuchUserExistsException("Customer not found."));
        return toCustomerResponseDto(selectedCustomer);
    }

    public CustomerPaginateResponseDto findAll(int page, int size, String searchKey) {
        return CustomerPaginateResponseDto.builder()
                .dataList(
                        customerRepository.findAllCustomers(searchKey, PageRequest.of(page, size))
                                .stream()
                                .map(this::toCustomerResponseDto)
                                .toList()
                )
                .dataCount(customerRepository.countAllCustomers(searchKey))
                .build();
    }

    private Customer toCustomer(CustomerRequestDto dto) {
        return dto == null ? null :
                Customer.builder()
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .phoneNumber(dto.getPhoneNumber())
                        .user(
                                User.builder()
                                        .username(dto.getUsername())
                                        .password(passwordEncoder.encode(dto.getPassword()))
                                        .role(UserRole.CUSTOMER)
                                        .build()
                        )
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();
    }

    private CustomerResponseDto toCustomerResponseDto(Customer customer) {
        return customer == null ? null :
                CustomerResponseDto.builder()
                        .id(customer.getId())
                        .firstName(customer.getFirstName())
                        .lastName(customer.getLastName())
                        .phoneNumber(customer.getPhoneNumber())
                        .username(customer.getUser().getUsername())
                        .createdAt(customer.getCreatedAt())
                        .updatedAt(customer.getUpdatedAt())
                        .build();
    }

    private Customer getAuthenticatedCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getCustomer();
    }

}
