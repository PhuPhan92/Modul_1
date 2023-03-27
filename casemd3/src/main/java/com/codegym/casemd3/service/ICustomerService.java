package service;
import model.Customer;
import java.util.List;

public interface ICustomerService {
    List<Customer> getAllCustomer();

    Customer findCustomerById(Long id);
    void editCustomer(Customer customer);
    void deleteCustomerById(Long id);

    void createCustomer(Customer customer);

    boolean checkImageExists(String image);
}
