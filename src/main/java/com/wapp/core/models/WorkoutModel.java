package com.wapp.core.models;

import com.wapp.core.dto.ExerciseDto;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class WorkoutModel {

	private String id;
	private String userId;
	private String name;
	private String description;
	private String duration;
	private Timestamp startDate;
	private Timestamp endDate;

	private List<ExerciseDto> exercises = new ArrayList<>();
}
