package io.github.tank_mass_control.config;

import io.github.tank_mass_control.models.TankModel;
import io.github.tank_mass_control.repositories.TankRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class TankInitializer {
    TankRepository tankRepository;

    public TankInitializer(TankRepository tankRepository) {
        this.tankRepository = tankRepository;
    }

    @PostConstruct
    public void init() {
        if (tankRepository.count() == 0) {
            tankRepository.save(new TankModel(1L, 5000, null, null, null));
            tankRepository.save(new TankModel(2L, 10000, null, null, null));
            tankRepository.save(new TankModel(3L, 15000, null, null, null));
        }
        for (TankModel tankModel : tankRepository.findAll()) {
            if (tankModel.getDensity() == null && tankModel.getMass() == null && tankModel.getLocalDateTime() == null) {
                updateData(tankModel);
                tankRepository.save(tankModel);
            }
        }
    }
    public static void updateData(TankModel tankModel) {
        int density = new Random().nextInt(1200 - 780 + 1) + 780;
        long mass = (long) density * tankModel.getVolume();
        LocalDateTime localDateTime = LocalDateTime.now();

        tankModel.setDensity(density);
        tankModel.setMass(mass);
        tankModel.setLocalDateTime(localDateTime);

    }
}
