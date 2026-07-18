package com.nuelst.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TaskModel {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(length = 50)
  private String title;
  private String description;

  private LocalDateTime endAt;
  private LocalDateTime startAt;
  private String priority;
  private UUID idUser;

  @CreationTimestamp
  private LocalDateTime createAt;

}
