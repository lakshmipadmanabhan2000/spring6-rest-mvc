package lakshmi.springframewrok.spring6restmvc.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import lakshmi.springframewrok.spring6restmvc.model.Customer;
import lakshmi.springframewrok.spring6restmvc.services.CustomerService;
import lakshmi.springframewrok.spring6restmvc.services.CustomerServiceImpl;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
	@Captor
	ArgumentCaptor<Long> ac;
	@Captor
	ArgumentCaptor<Customer> cc;
	@Autowired
	MockMvc mvc;
	@MockBean
	CustomerService cs;
	
	CustomerServiceImpl csImpl;
	@Autowired
	ObjectMapper om;
	@BeforeEach
	void setup() {
		 csImpl=new CustomerServiceImpl(null);
	}
	@Test
	void patchCustomer()throws Exception{
		Customer c=csImpl.getAllCustomers().get(0);
		Map<String,Object> cmap=new HashMap<>();
		cmap.put("version", 110L);
		mvc.perform(patch(CustomerController.CUSTOMER_PATH_ID,c.getId())
				.accept(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(cmap))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
		verify(cs).patchCustomer(ac.capture(), cc.capture());
		assertThat(c.getId()).isEqualTo(ac.getValue());
		assertThat(cmap.get("version")).isEqualTo(cc.getValue().getVersion());
	}
	@Test
	void putCustomer()throws Exception{
		Customer c=csImpl.getAllCustomers().get(0);
		mvc.perform(put(CustomerController.CUSTOMER_PATH_ID,c.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(c)))
		.andExpect(status().isNoContent());
		verify(cs).putCustomer(any(Long.class),any(Customer.class));
	}
	@Test
	void postCustomer() throws Exception{
		Customer c=csImpl.getAllCustomers().get(0);
		c.setId(null);
		c.setVersion(null);
		given(cs.postCustomer(any(Customer.class))).willReturn(csImpl.getAllCustomers().get(1));
		mvc.perform(post(CustomerController.CUSTOMER_PATH)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(c)))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"));
	}
	
	@Test
	void listCustomers() throws Exception {
		given(cs.getAllCustomers()).willReturn(csImpl.getAllCustomers());
		mvc.perform(get(CustomerController.CUSTOMER_PATH).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.length()",is(3)));
	}
	
	@Test
	void getCustomerById() throws Exception{
		Customer c= csImpl.getAllCustomers().get(0);
		given(cs.getCustomerById(c.getId())).willReturn(c);
		mvc.perform(get(CustomerController.CUSTOMER_PATH_ID,c.getId()).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id",is(c.getId()),Long.class))
		.andExpect(jsonPath("$.version",is(c.getVersion()),Long.class));
		//Long.class is used as additional parameter to jsonPath to force jsonPath to return a long value
	}
	@Test
	void deleteCustomer()throws Exception{
		Customer c=csImpl.getAllCustomers().get(0);
		mvc.perform(delete(CustomerController.CUSTOMER_PATH_ID,c.getId()).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
		verify(cs).deleteCustomer(ac.capture());
		assertThat(c.getId()).isEqualTo(ac.getValue());
	}
}
