/**
 * Author: Sajal Gupta
 * Date: Nov 14, 2025
 */

package com.trainSync.workout.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ExerciseLibraryEquipmentKey implements Serializable {

    @Column(name = "exercise_library_id")
    private UUID exerciseLibraryId;

    @Column(name = "tag_id")
    private UUID tagId;

    public ExerciseLibraryEquipmentKey() {}

    public ExerciseLibraryEquipmentKey(UUID exerciseLibraryId, UUID tagId) {
        this.exerciseLibraryId = exerciseLibraryId;
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExerciseLibraryEquipmentKey)) return false;
        ExerciseLibraryEquipmentKey that = (ExerciseLibraryEquipmentKey) o;
        return Objects.equals(exerciseLibraryId, that.exerciseLibraryId) &&
               Objects.equals(tagId, that.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exerciseLibraryId, tagId);
    }

    // getters and setters
}