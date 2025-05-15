package io.github.tank_mass_control.controllers;

import io.github.tank_mass_control.models.TankHistoryModel;
import io.github.tank_mass_control.models.TankModel;
import io.github.tank_mass_control.repositories.TankHistoryRepository;
import io.github.tank_mass_control.repositories.TankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TankController {
    TankRepository tankRepository;
    TankHistoryRepository tankHistoryRepository;

    @Autowired
    public TankController(TankRepository tankRepository, TankHistoryRepository tankHistoryRepository) {
        this.tankRepository = tankRepository;
        this.tankHistoryRepository = tankHistoryRepository;
    }

    @GetMapping("/current")
    public Iterable<TankModel> getCurrent() {
        return tankRepository.findAll();
    }

    @GetMapping("/history")
    public Iterable<TankHistoryModel> getHistory() {
        return tankHistoryRepository.findAll();
    }

    @GetMapping("/stats")
    public Map<String, BigDecimal> getStats() {
        List<TankHistoryModel> tankHistoryModelList = (List<TankHistoryModel>) tankHistoryRepository.findAll();
        BigDecimal density = BigDecimal.valueOf(tankHistoryModelList.stream()
                .mapToDouble(TankHistoryModel::getDensity)
                .average()
                .orElse(0d))
                .setScale(2, RoundingMode.HALF_DOWN);

        BigDecimal mass = BigDecimal.valueOf(tankHistoryModelList.stream()
                .mapToDouble(TankHistoryModel::getMass)
                .average()
                .orElse(0d))
                .setScale(2, RoundingMode.HALF_DOWN);

        Map<String, BigDecimal> result = new HashMap<>();
        result.put("average density: ", density);
        result.put("average mass: ", mass);
        return result;
    }

    @GetMapping("/history/filter")
    public Iterable<TankHistoryModel> getFilteredHistory(@RequestParam Long tankId, @RequestParam String startDate, @RequestParam String endDate) {
    LocalDateTime start = LocalDateTime.parse(startDate);
    LocalDateTime end = LocalDateTime.parse(endDate);
    return ((Collection<TankHistoryModel>) tankHistoryRepository.findAll()).stream()
            .filter(history -> history.getTank().getId().equals(tankId))
            .filter(history -> !history.getLocalDateTime().isBefore(start) && !history.getLocalDateTime().isAfter(end))
            .toList();
}
}
