package com.example.GlobalTrackerGeo.Service;

import com.example.GlobalTrackerGeo.Dto.SignupRequest;
import com.example.GlobalTrackerGeo.Entity.Customer;
import com.example.GlobalTrackerGeo.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Map<String, Object> getRidersForAdmin(int offset, int limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(Sort.Order.desc("status"), Sort.Order.desc("createdAt")));

        // Láy danh sách riders với điều kiện phân trang
        Page<Customer> riders = customerRepository.findRidersWithPagination(pageRequest);
        long total = customerRepository.count();

        Map<String, Object> response = new HashMap<>();
        response.put("riders", riders.getContent());
        response.put("total", total);

        return response;
    }

    public void updateRiderStatus(long riderId, String newStatus) {
        Customer rider = customerRepository.findById(riderId).orElseThrow(() -> new RuntimeException("Rider not found"));
        rider.setStatus(newStatus);
        customerRepository.save(rider);
    }

    public void addRider(SignupRequest addRequest) {
        Customer newRider = new Customer();
        newRider.setEmail(addRequest.getEmail());
        newRider.setPhone(addRequest.getPhone());
        newRider.setFirstName(addRequest.getFirstName());
        newRider.setLastName(addRequest.getLastName());
        newRider.setPassword(addRequest.getPassword());
        newRider.setStatus("Active");

        customerRepository.save(newRider);
    }

    public void editRider(SignupRequest editRequest) {
        Customer customer = customerRepository.findByEmail(editRequest.getEmail());

        customer.setFirstName(editRequest.getFirstName());
        customer.setLastName(editRequest.getLastName());
        customer.setPhone(editRequest.getPhone());
        customer.setPassword(editRequest.getPassword());

        customerRepository.save(customer);
    }
}