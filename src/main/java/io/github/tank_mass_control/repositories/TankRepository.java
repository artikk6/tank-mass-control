package io.github.tank_mass_control.repositories;

import io.github.tank_mass_control.models.TankModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TankRepository extends CrudRepository<TankModel, Long> {
}
