package com.nuelst.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {
  @Autowired
  private TaskRepository taskRepository;

  @PostMapping("")
  public ResponseEntity create(@Valid @RequestBody TaskModel data, HttpServletRequest request) {
    try {
      var idUser = request.getAttribute("idUser").toString();
      data.setIdUser(UUID.fromString(idUser));

      var currentDate = LocalDateTime.now();
      if (currentDate.isAfter(data.getStartAt()) || currentDate.isAfter(data.getEndAt())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start or End date can not be at past!");
      }

      if (data.getStartAt().isAfter(data.getEndAt())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("End date can not be before start date!");
      }

      var newTask = this.taskRepository.save(data);
      return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating task: " + e.getMessage());
    }
  }

  @GetMapping
  public List<TaskModel> list(HttpServletRequest request) {
    var idUser = request.getAttribute("idUser");

    var tasks = this.taskRepository.findByIdUser((UUID) idUser);
    return tasks;
  }

  @PutMapping("/{id}")
  public ResponseEntity update(@Valid @RequestBody TaskModel data, HttpServletRequest request, @PathVariable UUID id) {
    try {
      var idUser = UUID.fromString(request.getAttribute("idUser").toString());

      var taskFound = this.taskRepository.findById(id);
      if (taskFound.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found!");
      }

      var task = taskFound.get();
      if (!task.getIdUser().equals(idUser)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to update this task!");
      }

      if (data.getStartAt().isAfter(data.getEndAt())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("End date can not be before start date!");
      }

      task.setTitle(data.getTitle());
      task.setDescription(data.getDescription());
      task.setPriority(data.getPriority());
      task.setStartAt(data.getStartAt());
      task.setEndAt(data.getEndAt());

      var updatedTask = this.taskRepository.save(task);
      return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating task: " + e.getMessage());
    }
  }

  @PatchMapping("/{id}")
  public ResponseEntity partialUpdate(@RequestBody TaskModel data, HttpServletRequest request, @PathVariable UUID id) {
    try {
      var idUser = UUID.fromString(request.getAttribute("idUser").toString());

      var taskFound = this.taskRepository.findById(id);
      if (taskFound.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found!");
      }

      var task = taskFound.get();
      if (!task.getIdUser().equals(idUser)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to update this task!");
      }

      if (data.getTitle() != null) {
        if (data.getTitle().isBlank() || data.getTitle().length() > 50) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title must be between 1 and 50 characters!");
        }
        task.setTitle(data.getTitle());
      }

      if (data.getDescription() != null) {
        if (data.getDescription().length() > 255) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Description must be at most 255 characters!");
        }
        task.setDescription(data.getDescription());
      }

      if (data.getPriority() != null) {
        task.setPriority(data.getPriority());
      }

      if (data.getStartAt() != null) {
        task.setStartAt(data.getStartAt());
      }

      if (data.getEndAt() != null) {
        task.setEndAt(data.getEndAt());
      }

      if (task.getStartAt().isAfter(task.getEndAt())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("End date can not be before start date!");
      }

      var updatedTask = this.taskRepository.save(task);
      return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating task: " + e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(HttpServletRequest request, @PathVariable UUID id) {
    try {
      var idUser = UUID.fromString(request.getAttribute("idUser").toString());

      var taskFound = this.taskRepository.findById(id);
      if (taskFound.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found!");
      }

      var task = taskFound.get();
      if (!task.getIdUser().equals(idUser)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to delete this task!");
      }

      this.taskRepository.delete(task);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting task: " + e.getMessage());
    }
  }

}
