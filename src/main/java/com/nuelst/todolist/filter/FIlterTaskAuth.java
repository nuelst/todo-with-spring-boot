package com.nuelst.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nuelst.todolist.user.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // must be a componet for spring manage it
public class FIlterTaskAuth extends OncePerRequestFilter {
  @Autowired
  UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    var servletPath = request.getServletPath();
    if (!servletPath.equals("/tasks")) {
      filterChain.doFilter(request, response);
      return;
    }

    var auth = request.getHeader("Authorization");
    if (auth == null || !auth.startsWith("Basic")) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing credentials");
      return;
    }

    var authEncoded = auth.substring("Basic".length()).trim();
    byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
    var authString = new String(authDecoded);

    String[] credentials = authString.split(":");
    String username = credentials[0];
    String password = credentials[1];
    var user = this.userRepository.findByUsername(username);
    if (user == null) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials");
      return;
    }

    var validPassword = BCrypt.checkpw(password, user.getPassword());
    if (!validPassword) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials");
      return;
    }
    request.setAttribute("idUser", user.getId());
    filterChain.doFilter(request, response);
  }

}
