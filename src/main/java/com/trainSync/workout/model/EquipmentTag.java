

package com.trainSync.workout.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Date: Nov 14, 2025
 */
@Setter
@Getter
@Entity
@Table(name = "equipment_tag")
public class EquipmentTag {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name; // e.g. "barbell, dumbell"




}