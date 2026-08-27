package br.com.redae.evaluation.repository;

import br.com.redae.evaluation.entity.Evaluation;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, UUID> {}
