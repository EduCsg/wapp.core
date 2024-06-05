package com.wapp.core.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExerciseModel {

	private String id;
	private String name;
	private String muscleGroup;
	private Integer exerciseOrder;
	private Integer series;

}
