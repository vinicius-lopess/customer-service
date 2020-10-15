package com.unbox.customer.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.unbox.customer.model.Customer;
import com.unbox.customer.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/customer")
@Api(value="customer")
public class CustomerController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CustomerService customerService;
	
	@ApiOperation(value = "Buscar Customer pelo Id", response = Customer.class)
	@ApiImplicitParams({
    	@ApiImplicitParam(name = "id", value = "Customer id", required = true, dataType = "long", paramType = "query")
      }) 
	@ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Customer.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Failure")})
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) {
		Customer customer = customerService.localizar(id);
		if (customer != null) { 
			logger.info("Customer com Id "+id+" localizado.");
			return new ResponseEntity<Customer>(customer, HttpStatus.OK);
		}
		logger.info("Customer com Id "+id+" não localizado.");
		return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
	}
	
	@ApiOperation(value = "Localizar todos os customers", response = Customer[].class)    
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Customer.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Failure")})
	@GetMapping("/listar/todos")
	@ResponseBody
	public ResponseEntity<List<Customer>> getAll(){
		logger.info("Obtendo todos os customers registrados.");
		List<Customer> customers = customerService.findAll(); 
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Insere um novo customer.")
	@ApiResponses(value = { 
            @ApiResponse(code = 201, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Failure")})
	@PostMapping("/salvar")
	public ResponseEntity<Void> addCustomer(@RequestBody Customer customer, UriComponentsBuilder builder) {
		boolean flag = customerService.salvar(customer);
        if (flag == false) {
        	logger.error("Customer não inserido.");
        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        logger.info("Customer inserido com sucesso.");
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/new/{id}").buildAndExpand(customer.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Update customer existente pelo Id.")
	@ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Customer.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Failure")})
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
		customer.setId(id);
		customer = customerService.alterar(customer);
		if ( customer != null ) {
			logger.info("Customer alterado com sucesso: " + customer);
			return new ResponseEntity<Customer>(customer, HttpStatus.OK);
		}
		logger.warn("Customer com o Id indicado não localizado.");
		return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
	}
	
	@ApiOperation(value = "Deletar customer existente pelo Id")
	@ApiResponses(value = { 
            @ApiResponse(code = 204, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Failure")})
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id) {
		logger.info("Deletando customer com o ID: '" + id + "'");
		Customer customer = customerService.localizar(id);
		boolean userDeleted = customerService.deletar(customer);
		if ( userDeleted ) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
}
