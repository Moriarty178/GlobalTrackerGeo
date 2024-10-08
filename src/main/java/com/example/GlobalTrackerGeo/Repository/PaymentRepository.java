package com.example.GlobalTrackerGeo.Repository;

import com.example.GlobalTrackerGeo.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    Optional<Payment> findByTripId(String tripId);

    // Total revenue
    @Query("SELECT SUM(p.total) FROM Payment p WHERE p.paymentStatus = 'Paid'")
    Double calculateTotalRevenue();
}
