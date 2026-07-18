package com.nuelst.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class UserModel {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank
  @Column(unique = true)
  private String username;

  @NotBlank
  private String name;

  @NotBlank
  private String password;

  @CreationTimestamp
  private LocalDateTime createdAt;

  // Can use @Getter or Setter for a field or just to make for class

  // public void setName(String name) {
  // this.name = name;
  // }

  // public String getName() {
  // return name;
  // }

  // public void setPassword(String password) {
  // this.password = password;
  // }

  // public String getPassword() {
  // return password;
  // }

  // public void setUsername(String username) {
  // this.username = username;
  // }

  // public String getUsername() {
  // return username;
  // }

  // @Override
  // public String toString() {
  // // TODO Auto-generated method stub
  // return super.toString();
  // }
}
