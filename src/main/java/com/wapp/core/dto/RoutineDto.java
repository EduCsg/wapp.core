package com.wapp.core.dto;

import com.wapp.core.models.ExerciseModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoutineDto {

	private String id;
	private String name;
	private List<ExerciseModel> exercises = new ArrayList<>();

}
