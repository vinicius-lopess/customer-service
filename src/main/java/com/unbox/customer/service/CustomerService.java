package com.unbox.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unbox.customer.model.Customer;
import com.unbox.customer.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepo;

	public boolean salvar(Customer customer) {
		ExampleMatcher modelMatcher = ExampleMatcher
				.matching()
				.withIgnorePaths("id")
				.withMatcher("numeroCpf", GenericPropertyMatcher.of(StringMatcher.EXACT));
		Example<Customer> example = Example.of(customer, modelMatcher);
		if (customer.getNome() == null || customer.getNumeroCpf() == null 
				|| customerRepo.exists(example)) {
			return false;
		} else {
			customerRepo.saveAndFlush(customer);
			return true;
		}
	}

	public Customer alterar(Customer customer) {
		return customerRepo.saveAndFlush(customer);
	}

	public Customer localizar(final Long id) {
		return customerRepo.findById(id).get();
	}

	public boolean deletar(Customer customer) {
		customerRepo.delete(customer);
		return true;
	}

	public List<Customer> findAll() {
		return customerRepo.findAll();
	}
}
