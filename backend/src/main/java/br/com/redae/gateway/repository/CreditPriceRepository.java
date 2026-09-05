package br.com.redae.gateway.repository;

import br.com.redae.gateway.entity.CreditPrice;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CreditPriceRepository extends JpaRepository<CreditPrice, UUID> {
  @Query(
      """
      select price from CreditPrice price
      where price.active = true
        and price.validFrom <= :now
        and (price.validUntil is null or price.validUntil > :now)
      order by price.validFrom desc
      """)
  Optional<CreditPrice> findCurrent(@Param("now") Instant now);
}
