# ADR 0053 — Webhook de pagamento autenticado e idempotente

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

Somente webhook autenticado da AbacatePay poderá confirmar uma `CompraCredito` como paga. O frontend poderá iniciar o pagamento e consultar o estado, mas não poderá conceder créditos nem marcar a compra como paga.

Cada webhook deverá ser validado por:

- assinatura ou mecanismo oficial de autenticidade;
- referência externa da compra;
- estado atual da compra;
- identificação do evento já processado;
- consistência de valor, moeda e quantidade esperada.

Uma confirmação repetida não poderá criar uma segunda transação `COMPRA`.

## Consequências

- reduz fraude por alteração de respostas no frontend;
- evita créditos duplicados por repetição de webhook;
- exige armazenar referência/evento externo para idempotência;
- compra só se torna `PAGA` após validação no backend.
