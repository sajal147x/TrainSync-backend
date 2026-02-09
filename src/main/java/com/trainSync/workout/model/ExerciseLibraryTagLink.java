package com.trainSync.workout.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "exercise_library_tag_link")
public class ExerciseLibraryTagLink {

    @EmbeddedId
    private ExerciseLibraryTagKey id;

    @ManyToOne
    @MapsId("exerciseLibraryId")
    @JoinColumn(name = "exercise_library_id")
    @JsonIgnore
    private ExerciseLibrary exerciseLibrary;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    @JsonIgnore
    private MuscleTag muscleTag;

    @Column(name = "level", nullable = false)
    private String level; // PRIMARY or SECONDARY

    public ExerciseLibraryTagLink() {}

    public ExerciseLibraryTagLink(ExerciseLibrary exerciseLibrary, MuscleTag muscleTag, String level) {
        this.exerciseLibrary = exerciseLibrary;
        this.muscleTag = muscleTag;
        this.level = level;
        this.id = new ExerciseLibraryTagKey(exerciseLibrary.getId(), muscleTag.getId());
    }
    
    public boolean isPrimary() {
    	return "PRIMARY".equals(level);
    }


}