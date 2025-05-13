package io.github.tank_mass_control.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="tank")
public class TankModel {
    @Id
    private Long id;

    @Column(nullable = false)
    private Integer volume;

    private Integer density;
    private Long mass;
    private LocalDateTime localDateTime;
}
