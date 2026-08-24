# ADR 0050 — AbacatePay e pacotes de créditos

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

O MVP usará a AbacatePay como provedor inicial de pagamentos. A integração ficará atrás de uma interface de pagamento para permitir substituição futura sem acoplar o domínio ao SDK ou ao formato do provedor.

O usuário poderá:

- escolher livremente a quantidade de créditos;
- selecionar pacotes promocionais predefinidos, quando disponíveis.

Não haverá uma entidade `PacoteCredito` no banco. Ofertas e alterações promocionais serão configuradas na AbacatePay. `CompraCredito` armazenará a quantidade, valor, moeda, referência externa, identificação da oferta quando disponível e um snapshot da condição aplicada. Alterações futuras na AbacatePay não modificarão compras já realizadas.

A representação monetária usará `BigDecimal` no Java e `NUMERIC(12,2)` no PostgreSQL, evitando `double` para preços, descontos e estornos.

Pacotes promocionais poderão possuir `vigencia_inicio`, `vigencia_fim` e `limite_de_uso` opcional. A compra armazenará um snapshot da configuração aplicada.

## Consequências

- permite flexibilidade para o usuário e campanhas promocionais;
- mantém histórico financeiro compreensível;
- exige validar limites mínimos e máximos para compra livre;
- exige validar preço, oferta e quantidade no backend, sem confiar no frontend;
- webhooks da AbacatePay deverão ser autenticados e idempotentes.
