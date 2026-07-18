package com.nuelst.todolist.task;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
  @Autowired
  private TaskRepository taskRepository;

  @PostMapping("")
  public ResponseEntity create(@RequestBody TaskModel data, HttpServletRequest request) {
    System.out.println("Controller Layer" + request.getAttribute("idUser"));
    var idUser = request.getAttribute("idUser").toString();

    data.setIdUser(UUID.fromString(idUser));
    var newTask = this.taskRepository.save(data);

    return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
  }

}
