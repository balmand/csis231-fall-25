package com.csis231.api.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }

    public List<Customer> search(String query) {
        return customerRepository.searchCustomers(query);
    }

    public Customer findById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer with id " + id + " not found"));
        return customer;
    }

    public Customer update(Long id, Customer customer) {
        Customer customerUpdate = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerUpdate.setName(customer.getName());
        customerUpdate.setEmail(customer.getEmail());
        return customerRepository.save(customerUpdate);

    }

    public void delete(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer with id " + id + " not found"));
        customerRepository.delete(customer);
    }

}
