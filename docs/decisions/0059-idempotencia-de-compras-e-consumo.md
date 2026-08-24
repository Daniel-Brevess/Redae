# ADR 0059 — Idempotência de compras e consumo

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

`CompraCredito.referencia_externa` será única quando preenchida. Uma confirmação repetida da mesma compra não poderá criar outra transação `COMPRA`.

`TransacaoCredito.avaliacao_id` será única para transações do tipo `CONSUMO`. Uma avaliação não poderá consumir dois créditos, mesmo que a requisição de confirmação ou o webhook interno seja repetido.

As garantias serão implementadas por constraints no banco e verificações idempotentes no service, dentro da mesma transação do consumo ou concessão.

## Consequências

- evita duplicidade de créditos e cobranças;
- protege contra repetição de requisições e concorrência;
- exige tratar referências nulas com cuidado em índices únicos;
- erros de conflito devem ser convertidos em respostas seguras e previsíveis.
