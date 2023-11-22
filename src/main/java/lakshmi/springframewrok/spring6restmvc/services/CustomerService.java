package lakshmi.springframewrok.spring6restmvc.services;
import java.util.List;
import lakshmi.springframewrok.spring6restmvc.model.Customer;
public interface CustomerService {
	public List<Customer> getAllCustomers();
	public Customer getCustomerById(Long id);
	public Customer postCustomer(Customer c);
	public void putCustomer(Long id, Customer c);
	public void deleteCustomer(Long id);
	public void patchCustomer(Long id, Customer c);

}
