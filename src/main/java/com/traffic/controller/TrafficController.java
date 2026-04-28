package com.traffic.controller;

import com.traffic.model.VehicleEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/traffic")
public class TrafficController {

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody VehicleEvent event) {

        try {
            int fine = 0;

            // 🚑 Emergency vehicle
            if (event.isEmergency()) {
                return ResponseEntity.ok(Map.of(
                        "fine", 0,
                        "message", "Emergency vehicle - no violation"
                ));
            }

            // 🚦 Speed check
            if (event.getSpeed() > 80) {
                fine = 1000;
                return ResponseEntity.ok(Map.of(
                        "fine", fine,
                        "message", "Violation detected"
                ));
            }

            // ✅ No violation
            return ResponseEntity.ok(Map.of(
                    "fine", 0,
                    "message", "No violation"
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Internal server error"
            ));
        }
    }
}