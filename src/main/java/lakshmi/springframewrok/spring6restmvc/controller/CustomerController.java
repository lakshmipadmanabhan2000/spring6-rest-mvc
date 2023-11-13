package lakshmi.springframewrok.spring6restmvc.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import lakshmi.springframewrok.spring6restmvc.model.Customer;
import lakshmi.springframewrok.spring6restmvc.services.BeerService;
import lakshmi.springframewrok.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@AllArgsConstructor
@Slf4j
@Controller
@RestController
@RequestMapping("/customer/get")
public class CustomerController {
	private final CustomerService cs;
	@PatchMapping("{cid}")
	public ResponseEntity patchcustomer(@PathVariable("cid") Long id, @RequestBody Customer c) {
		cs.patchCustomer(id,c);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	@DeleteMapping("{customerId}")
	public ResponseEntity deleteCustomer(@PathVariable("customerId")Long id) {
		cs.deleteCustomer(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	@PutMapping("{customerId}")
	public ResponseEntity putCustomer(@RequestBody Customer c, @PathVariable("customerId")Long id) {
		cs.putCustomer(id,c);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	@PostMapping
	public ResponseEntity postCustomer(@RequestBody Customer c) {
		Customer savedCustomer=cs.postCustomer(c);
		HttpHeaders h=new HttpHeaders();
		h.add("LOCATION","/customer/get/"+savedCustomer.getId().toString());
		return new ResponseEntity(h,HttpStatus.CREATED);
	}
	@RequestMapping(method=RequestMethod.GET)
	public List<Customer>getAllCustomers(){
		return cs.getAllCustomers();		
	}
	@RequestMapping(method=RequestMethod.GET, value= "{customerId}")
	public Customer getCustomerById(@PathVariable("customerId") Long id) {
		return cs.getCustomerById(id);
	}
}
