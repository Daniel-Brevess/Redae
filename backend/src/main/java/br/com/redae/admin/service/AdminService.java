package br.com.redae.admin.service;

import br.com.redae.admin.dto.AdminCreditAdjustmentResponse;
import br.com.redae.admin.dto.AdminUserResponse;
import br.com.redae.gateway.service.CreditService;
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
  private final CreditService creditService;

  public AdminService(UserRepository userRepository, CreditService creditService) {
    this.userRepository = userRepository;
    this.creditService = creditService;
  }

  @Transactional(readOnly = true)
  public long countUsers() {
    return userRepository.count();
  }

  @Transactional(readOnly = true)
  public Page<AdminUserResponse> listUsers(Pageable pageable) {
    return listUsers(null, pageable);
  }

  @Transactional(readOnly = true)
  public Page<AdminUserResponse> listUsers(String search, Pageable pageable) {
    var normalizedPageable = normalizePageable(pageable);
    var normalizedSearch = normalizeSearch(search);
    var users =
        normalizedSearch.isEmpty()
            ? userRepository.findAll(normalizedPageable)
            : userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                normalizedSearch, normalizedSearch, normalizedPageable);
    var userIds = users.getContent().stream().map(user -> user.getId()).toList();
    var balances = creditService.getBalances(userIds);
    return users.map(user -> AdminUserResponse.from(user, balances.getOrDefault(user.getId(), 0L)));
  }

  @Transactional
  public AdminCreditAdjustmentResponse grantCredits(
      java.util.UUID userId, br.com.redae.user.entity.User administrator, int credits) {
    long balance = creditService.grantCredits(userId, administrator, credits);
    return new AdminCreditAdjustmentResponse(userId, balance);
  }

  private String normalizeSearch(String search) {
    return search == null ? "" : search.trim();
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
