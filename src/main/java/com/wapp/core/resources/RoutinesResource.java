package com.wapp.core.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wapp.core.dto.RoutineDto;
import com.wapp.core.services.RoutinesService;

@RestController
@RequestMapping("/v1/core/routines")
public class RoutinesResource {

	@Autowired
	RoutinesService routinesService;

	@GetMapping("/list/{userId}")
	public ResponseEntity<?> getRoutineByUserId(@PathVariable String userId) {
		return routinesService.getRoutineByUserId(userId);
	}

	@GetMapping("/{routineId}")
	public ResponseEntity<?> getRoutineById(@PathVariable String routineId) {
		return routinesService.getRoutineById(routineId);
	}

	@PostMapping("/create/{userId}")
	public ResponseEntity<?> createRoutine(@PathVariable String userId, @RequestBody RoutineDto routineDto) {
		return routinesService.postRoutine(userId, routineDto);
	}

	@PutMapping("/{routineId}")
	public ResponseEntity<?> updateRoutine(@PathVariable String routineId, @RequestBody RoutineDto routineDto) {
		return routinesService.updateRoutine(routineId, routineDto);
	}

	@DeleteMapping("/{routineId}")
	public ResponseEntity<?> deleteRoutine(@PathVariable String routineId) {
		return routinesService.deleteRoutineById(routineId);
	}
}
