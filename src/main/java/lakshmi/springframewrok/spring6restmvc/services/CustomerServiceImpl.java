package lakshmi.springframewrok.spring6restmvc.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lakshmi.springframewrok.spring6restmvc.model.Customer;
@Service
public class CustomerServiceImpl implements CustomerService {
	private Map<Long,Customer> CustomerMap;
	
	public CustomerServiceImpl(Map<Long, Customer> customerMap) {
		super();
		this.CustomerMap = new HashMap();
		Customer c1=Customer.builder().
				id((long) 1).
				version((long) 2).
				createdDate(LocalDateTime.now()).
				lastModifiedDate(LocalDateTime.now()).build();
		Customer c2=Customer.builder().
				id((long) 2).
				version((long) 5).
				createdDate(LocalDateTime.now()).
				lastModifiedDate(LocalDateTime.now()).build();
		Customer c3=Customer.builder().
				id((long) 3).
				version((long) 4).
				createdDate(LocalDateTime.now()).
				lastModifiedDate(LocalDateTime.now()).build();
		CustomerMap.put(c1.getId(), c1);
		CustomerMap.put(c2.getId(), c2);
		CustomerMap.put(c3.getId(), c3);
	}

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return new ArrayList(CustomerMap.values());
	}

	@Override
	public Customer getCustomerById(Long id) {
		// TODO Auto-generated method stub
		return CustomerMap.get(id);
	}
	public Customer postCustomer(Customer c) {
		Customer save=Customer.builder()
				.id(c.getId())
				.version(c.getVersion())
				.createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now())
				.build();
		CustomerMap.put(save.getId(), save);
		return c;
	}
	public void putCustomer(Long id,Customer c) {
		Customer c1= CustomerMap.get(id);
		c1.setVersion(c.getVersion());
		CustomerMap.put(c.getId(), c1);
	}
	public void deleteCustomer(Long id) {
		CustomerMap.remove(id);
	}
	public void patchCustomer(Long id, Customer c) {
		Customer c1=CustomerMap.get(id);
		if(c.getVersion()!=null) 
			c1.setVersion(c.getVersion());
		
	}
}
