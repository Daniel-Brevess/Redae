# ADR 0047 — Concessão inicial e campos de crédito

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

No cadastro do estudante, o sistema concederá 1 crédito inicial por meio de uma transação `CONCESSAO`. A primeira avaliação gratuita será consumida como qualquer outra avaliação, sem campo ou tipo especial.

`TransacaoCredito` terá:

- `id` UUID;
- `usuario_id` FK;
- `tipo`;
- `quantidade`;
- `avaliacao_id` opcional;
- `referencia_externa` opcional;
- `created_at`;
- `updated_at`.

`referencia_externa` poderá relacionar uma compra ou ajuste a um sistema externo no futuro, sem obrigar uma integração de pagamento no MVP.

## Consequências

- o benefício inicial fica auditável;
- não é necessário diferenciar avaliação gratuita no modelo de avaliação;
- o sistema pode evoluir para compra de créditos;
- a criação da conta deverá ser idempotente para não conceder o crédito duas vezes.
