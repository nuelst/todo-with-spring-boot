package com.nuelst.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class TaskModel {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank
  @Size(max = 50)
  @Column(length = 50)
  private String title;

  @Size(max = 255)
  private String description;

  @NotNull
  private LocalDateTime endAt;

  @NotNull
  private LocalDateTime startAt;

  @NotBlank
  private String priority;

  private UUID idUser;

  @CreationTimestamp
  private LocalDateTime createAt;

}
