package br.com.redae.gateway.entity;

import br.com.redae.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "preco_credito")
public class CreditPrice {
  @Id private UUID id;

  @Column(name = "valor_por_credito", nullable = false, precision = 12, scale = 2)
  private BigDecimal amountPerCredit;

  @Column(name = "moeda", nullable = false, length = 3)
  private String currency;

  @Column(name = "ativo", nullable = false)
  private boolean active;

  @Column(name = "vigente_desde", nullable = false)
  private Instant validFrom;

  @Column(name = "vigente_ate")
  private Instant validUntil;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "administrador_id", nullable = false)
  private User administrator;

  protected CreditPrice() {}

  public BigDecimal getAmountPerCredit() {
    return amountPerCredit;
  }
}
