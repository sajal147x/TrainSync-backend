package com.trainSync.workout.model;

import java.io.Serializable;
import java.util.UUID;
import jakarta.persistence.*;

@Embeddable
public class ExerciseLibraryTagKey implements Serializable {

    @Column(name = "exercise_library_id")
    private UUID exerciseLibraryId;

    @Column(name = "tag_id")
    private UUID tagId;

    public ExerciseLibraryTagKey() {}

    public ExerciseLibraryTagKey(UUID exerciseLibraryId, UUID tagId) {
        this.exerciseLibraryId = exerciseLibraryId;
        this.tagId = tagId;
    }

    public UUID getExerciseLibraryId() {
        return exerciseLibraryId;
    }

    public void setExerciseLibraryId(UUID exerciseLibraryId) {
        this.exerciseLibraryId = exerciseLibraryId;
    }

    public UUID getTagId() {
        return tagId;
    }

    public void setTagId(UUID tagId) {
        this.tagId = tagId;
    }

    // equals & hashCode required for @Embeddable key

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExerciseLibraryTagKey)) return false;
        ExerciseLibraryTagKey that = (ExerciseLibraryTagKey) o;
        return exerciseLibraryId.equals(that.exerciseLibraryId) &&
                tagId.equals(that.tagId);
    }

    @Override
    public int hashCode() {
        return exerciseLibraryId.hashCode() + tagId.hashCode();
    }
}