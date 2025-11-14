
package com.trainSync.workout.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

/**
 * Author: Sajal Gupta
 * Date: Nov 14, 2025
 */
@Entity
@Table(name = "exercise_library_equipment_tag_link")
public class ExerciseLibraryEquipmentLink {

    @EmbeddedId
    private ExerciseLibraryEquipmentKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("exerciseLibraryId")
    @JoinColumn(name = "exercise_library_id")
    @JsonIgnore
    private ExerciseLibrary exerciseLibrary;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    @JsonIgnore
    private EquipmentTag equipmentTag;


    public ExerciseLibraryEquipmentLink() {}

    public ExerciseLibraryEquipmentLink(ExerciseLibrary exerciseLibrary, EquipmentTag equipmentTag) {
        this.exerciseLibrary = exerciseLibrary;
        this.equipmentTag = equipmentTag;
        this.id = new ExerciseLibraryEquipmentKey(exerciseLibrary.getId(), equipmentTag.getId());
    }

    public ExerciseLibraryEquipmentKey getId() {
        return id;
    }

    public void setId(ExerciseLibraryEquipmentKey id) {
        this.id = id;
    }

    public ExerciseLibrary getExerciseLibrary() {
        return exerciseLibrary;
    }

    public void setExerciseLibrary(ExerciseLibrary exerciseLibrary) {
        this.exerciseLibrary = exerciseLibrary;
    }

	/**
	 * @return the equipmentTag
	 */
	public EquipmentTag getEquipmentTag() {
		return equipmentTag;
	}

	/**
	 * @param equipmentTag the equipmentTag to set
	 */
	public void setEquipmentTag(EquipmentTag equipmentTag) {
		this.equipmentTag = equipmentTag;
	}

}