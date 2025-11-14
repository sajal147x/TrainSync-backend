
package com.trainSync.workout.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Author: Sajal Gupta Date: Nov 12, 2025
 */
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

    @OneToMany(mappedBy = "exerciseLibrary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseLibraryTagLink> tagLinks = new ArrayList<>();
    
    @ManyToMany @JoinTable( name = "exercise_library_tag_link", joinColumns = @JoinColumn(name = "exercise_library_id"), inverseJoinColumns = @JoinColumn(name = "tag_id") )
    private Set<MuscleTag> muscleTags;
    
    @ManyToMany @JoinTable( name = "exercise_library_equipment_tag_link", joinColumns = @JoinColumn(name = "exercise_library_id"), inverseJoinColumns = @JoinColumn(name = "tag_id") )
    private Set<EquipmentTag> equipmentTags;
  

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }



	/**
	 * @return the tagLinks
	 */
	public List<ExerciseLibraryTagLink> getTagLinks() {
		return tagLinks;
	}

	/**
	 * @param tagLinks the tagLinks to set
	 */
	public void setTagLinks(List<ExerciseLibraryTagLink> tagLinks) {
		this.tagLinks = tagLinks;
	}

	/**
	 * @return the muscleTags
	 */
	public Set<MuscleTag> getMuscleTags() {
		return muscleTags;
	}

	/**
	 * @param muscleTags the muscleTags to set
	 */
	public void setMuscleTags(Set<MuscleTag> muscleTags) {
		this.muscleTags = muscleTags;
	}
	
	/**
	 * @return the equipmentTags
	 */
	public Set<EquipmentTag> getEquipmentTags() {
		return equipmentTags;
	}

	/**
	 * @param equipmentTags the equipmentTags to set
	 */
	public void setEquipmentTags(Set<EquipmentTag> equipmentTags) {
		this.equipmentTags = equipmentTags;
	}


	
}