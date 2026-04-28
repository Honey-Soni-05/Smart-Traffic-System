package com.traffic.service;

import com.traffic.model.*;

public class TrafficService {

    public static Violation process(VehicleEvent event) {

        // 🚑 Emergency → no violation
        if (event.isEmergency()) {
            return new Violation(
                    event.getVehicleId(),
                    event.getSpeed(),
                    event.getZone(),
                    0
            );
        }

        // 🚦 Speed check
        if (event.getSpeed() > 80) {

            int fine;

            if (event.getSpeed() > 120) fine = 5000;
            else if (event.getSpeed() > 100) fine = 2000;
            else fine = 1000;

            return new Violation(
                    event.getVehicleId(),
                    event.getSpeed(),
                    event.getZone(),
                    fine
            );
        }

        // ✅ No violation
        return new Violation(
                event.getVehicleId(),
                event.getSpeed(),
                event.getZone(),
                0
        );
    }
}