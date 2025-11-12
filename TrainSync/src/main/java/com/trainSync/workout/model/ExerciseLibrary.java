
package com.trainSync.workout.model;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @Column(nullable = false, unique = true)
    private String name;


    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private boolean isCustom = false;

    @Column(name = "created_by")
    private UUID createdBy; // null if global exercise

    // ✅ Many-to-Many with tags
    @ManyToMany
    @JoinTable(
        name = "exercise_library_tag_link",
        joinColumns = @JoinColumn(name = "exercise_library_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<MuscleTag> muscleTags;

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

    public Set<MuscleTag> getMuscleTags() {
        return muscleTags;
    }

    public void setMuscleTags(Set<MuscleTag> muscleTags) {
        this.muscleTags = muscleTags;
    }
}