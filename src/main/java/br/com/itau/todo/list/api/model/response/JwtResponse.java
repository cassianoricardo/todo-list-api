package br.com.itau.todo.list.api.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class JwtResponse {
  private final String token;
  private String type = "Bearer";
  private final Long id;
  private final String username;
  private final String email;
  private final List<String> roles;
}
