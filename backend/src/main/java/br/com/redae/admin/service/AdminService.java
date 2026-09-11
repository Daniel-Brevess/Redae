package br.com.redae.admin.service;

import br.com.redae.admin.dto.AdminUserResponse;
import br.com.redae.user.repository.UserRepository;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
  private static final int DEFAULT_PAGE_SIZE = 20;
  private static final int MAX_PAGE_SIZE = 100;
  private final UserRepository userRepository;

  public AdminService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional(readOnly = true)
  public long countUsers() {
    return userRepository.count();
  }

  @Transactional(readOnly = true)
  public Page<AdminUserResponse> listUsers(Pageable pageable) {
    return userRepository.findAll(normalizePageable(pageable)).map(AdminUserResponse::from);
  }

  private Pageable normalizePageable(Pageable pageable) {
    int size = Math.min(pageable.getPageSize(), MAX_PAGE_SIZE);
    if (size < 1) size = DEFAULT_PAGE_SIZE;
    Sort.Order requestedOrder = pageable.getSort().stream().findFirst().orElse(null);
    String property = requestedOrder == null ? "createdAt" : requestedOrder.getProperty();
    if (!Set.of("createdAt", "name", "email", "role").contains(property)) {
      property = "createdAt";
    }
    Sort.Direction direction =
        requestedOrder == null ? Sort.Direction.DESC : requestedOrder.getDirection();
    return PageRequest.of(
        Math.max(pageable.getPageNumber(), 0), size, Sort.by(direction, property));
  }
}
