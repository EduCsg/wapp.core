package com.wapp.core.models;

import lombok.Data;

@Data
public class ExercisesDoneModel {

	private String id;
	private String workoutId;
	private String userId;
	private String exerciseId;
	private Integer exerciseOrder;

}
