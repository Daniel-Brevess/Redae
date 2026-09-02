package br.com.redae.evaluation.repository;

import br.com.redae.evaluation.entity.Evaluation;
import br.com.redae.evaluation.entity.EvaluationType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, UUID> {
  boolean existsByUserIdAndType(UUID userId, EvaluationType type);

  @EntityGraph(attributePaths = {"competencyScores", "competencyScores.feedbackItems"})
  Optional<Evaluation> findByIdAndUserId(UUID id, UUID userId);

  @EntityGraph(attributePaths = {"competencyScores", "competencyScores.feedbackItems"})
  List<Evaluation> findAllByUserIdOrderByCreatedAtDesc(UUID userId);
}
