package com.traffic.controller;

import com.traffic.model.VehicleEvent;
import com.traffic.model.Violation;
import com.traffic.repository.ViolationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/traffic")
public class TrafficController {

    @Autowired
    private ViolationRepository repo;

    // 🔥 POST API - Check violation
    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody VehicleEvent event) {

        try {
            // ✅ Basic validation
            if (event.getVehicleId() == null || event.getVehicleId().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Vehicle ID is required"
                ));
            }

            int fine = 0;

            // 🚑 Emergency vehicle → no fine
            if (!event.isEmergency()) {

                // 🚦 Speed rules
                if (event.getSpeed() > 120) fine = 5000;
                else if (event.getSpeed() > 100) fine = 2000;
                else if (event.getSpeed() > 80) fine = 1000;
            }

            // ✅ Save to DB
            Violation v = new Violation(
                    event.getVehicleId(),
                    event.getSpeed(),
                    event.getZone(),
                    fine
            );

            repo.save(v);

            // ✅ Response
            return ResponseEntity.ok(Map.of(
                    "fine", fine,
                    "message", fine > 0 ? "Violation detected" : "No violation"
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Internal server error"
            ));
        }
    }

    // 📜 GET API - Fetch history (latest first)
    @GetMapping("/history")
    public List<Violation> getHistory() {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
}