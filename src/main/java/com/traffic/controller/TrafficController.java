package com.traffic.controller;

import com.traffic.model.*;
import com.traffic.repository.ViolationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.Map;

@RestController
@RequestMapping("/traffic")
public class TrafficController {

    @Autowired
    private ViolationRepository repo;

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody VehicleEvent event) {

        int fine = 0;

        if (!event.isEmergency() && event.getSpeed() > 80) {
            if (event.getSpeed() > 120) fine = 5000;
            else if (event.getSpeed() > 100) fine = 2000;
            else fine = 1000;
        }

        // ✅ Save to DB
        Violation v = new Violation(
                event.getVehicleId(),
                event.getSpeed(),
                event.getZone(),
                fine
        );
        repo.save(v);

        return ResponseEntity.ok(Map.of(
                "fine", fine,
                "message", fine > 0 ? "Violation detected" : "No violation"
        ));
    }

    @GetMapping("/history")
    public List<Violation> getHistory() {
        return repo.findAll();
    }
}