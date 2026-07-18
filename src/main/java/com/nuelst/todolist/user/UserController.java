package com.nuelst.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  UserRepository userRepository;

  @PostMapping("")
  public UserModel create(@RequestBody UserModel data) {

    // System.out.println(data.getName());

    var newUser = this.userRepository.save(data);
    return newUser;

  }

}
