package com.traffic.service;

import com.traffic.model.VehicleEvent;
import com.traffic.model.Violation;
import com.traffic.repository.ViolationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrafficService {

    @Autowired
    private ViolationRepository repo;

    public Violation process(VehicleEvent event) {

        int fine = 0;

        // 🚑 Emergency → no fine
        if (!event.isEmergency() && event.getSpeed() > 80) {
            fine = 1000;
        }

        Violation violation = new Violation(
                event.getVehicleId(),
                event.getSpeed(),
                event.getZone(),
                fine
        );

        return repo.save(violation); // 🔥 IMPORTANT
    }
}