package br.com.redae.admin.dto;

import br.com.redae.user.entity.User;
import java.util.UUID;

public record AdminUserResponse(
    UUID id, String name, String email, String role, boolean emailVerified, long credits) {
  public static AdminUserResponse from(User user, long credits) {
    return new AdminUserResponse(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getRole().name(),
        user.isEmailVerified(),
        credits);
  }
}
