package lakshmi.springframewrok.spring6restmvc.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@Controller
@RestController
//@RequestMapping("/customer/get")
public class CustomerController {
	private final CustomerService cs;
	public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

	@PatchMapping(CUSTOMER_PATH_ID)
	public ResponseEntity patchcustomer(@PathVariable("customerId") Long id, @RequestBody Customer c) {
		cs.patchCustomer(id,c);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	@DeleteMapping(CUSTOMER_PATH_ID)
	public ResponseEntity deleteCustomer(@PathVariable("customerId")Long id) {
		cs.deleteCustomer(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	@PutMapping(CUSTOMER_PATH_ID)
	public ResponseEntity putCustomer(@RequestBody Customer c, @PathVariable("customerId")Long id) {
		cs.putCustomer(id,c);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	@PostMapping(CUSTOMER_PATH)
	public ResponseEntity postCustomer(@RequestBody Customer c) {
		Customer savedCustomer=cs.postCustomer(c);
		HttpHeaders h=new HttpHeaders();
		h.add("LOCATION",CUSTOMER_PATH+savedCustomer.getId().toString());
		return new ResponseEntity(h,HttpStatus.CREATED);
	}
	@GetMapping(CUSTOMER_PATH)
	public List<Customer>getAllCustomers(){
		return cs.getAllCustomers();		
	}
	@GetMapping(CUSTOMER_PATH_ID)
	public Customer getCustomerById(@PathVariable("customerId") Long id) {
		return cs.getCustomerById(id);
	}
}
