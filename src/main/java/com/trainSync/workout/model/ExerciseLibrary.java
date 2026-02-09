
package com.trainSync.workout.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Author: Sajal Gupta Date: Nov 12, 2025
 */
@Setter
@Getter
@Entity
@Table(name = "exercise_library")
public class ExerciseLibrary {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;


    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private boolean isCustom = false;

    @Column(name = "created_by")
    private UUID createdBy; // null if global exercise

    @OneToMany(mappedBy = "exerciseLibrary", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @BatchSize(size = 10)
    private List<ExerciseLibraryTagLink> tagLinks = new ArrayList<>();
    
    @Column
    private String exercisePictureUrl;
    

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "equipment_id")
    private EquipmentTag equipment;


	
	public String getDisplayName() {
		return this.getName() + " (" + this.getEquipment().getName() + ")";
	}
	
	


	
}