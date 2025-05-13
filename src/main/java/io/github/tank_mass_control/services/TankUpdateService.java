package io.github.tank_mass_control.services;

import io.github.tank_mass_control.config.TankInitializer;
import io.github.tank_mass_control.models.TankHistoryModel;
import io.github.tank_mass_control.models.TankModel;
import io.github.tank_mass_control.repositories.TankHistoryRepository;
import io.github.tank_mass_control.repositories.TankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TankUpdateService {
    TankRepository tankRepository;
    TankHistoryRepository tankHistoryRepository;

    @Autowired
    public TankUpdateService(TankRepository tankRepository, TankHistoryRepository tankHistoryRepository) {
        this.tankRepository = tankRepository;
        this.tankHistoryRepository = tankHistoryRepository;
    }

    @Scheduled(fixedDelay = 50000)
    public void tankMassUpdate() {
        for (TankModel tankModel : tankRepository.findAll()) {
            TankHistoryModel tankHistoryModel = new TankHistoryModel();
            tankHistoryModel.setTank(tankModel);
            tankHistoryModel.setMass(tankModel.getMass());
            tankHistoryModel.setVolume(tankModel.getVolume());
            tankHistoryModel.setDensity(tankModel.getDensity());
            tankHistoryModel.setLocalDateTime(tankModel.getLocalDateTime());
            tankHistoryRepository.save(tankHistoryModel);

            TankInitializer.updateData(tankModel);
            tankRepository.save(tankModel);
        }
    }

}
