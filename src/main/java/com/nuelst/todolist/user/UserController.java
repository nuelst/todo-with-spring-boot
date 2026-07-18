package com.nuelst.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @PostMapping("")
  public ResponseEntity create(@RequestBody UserModel data) {

    // System.out.println(data.getName());

    var user = this.userRepository.findByUsername(data.getUsername());
    if (user != null) {
      System.out.println("Username exists!");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists!");

    }

    var pwHash = this.passwordEncoder.encode(data.getPassword());
    data.setPassword(pwHash);
    var newUser = this.userRepository.save(data);
    return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
  }

}
