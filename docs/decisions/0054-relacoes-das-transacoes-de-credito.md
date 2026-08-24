# ADR 0054 — Relações das transações de crédito

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

`CompraCredito` pertence a `Usuario`. `TransacaoCredito` também pertence a `Usuario` e poderá apontar para o contexto que originou a operação:

| Tipo | Referência principal |
| --- | --- |
| `COMPRA` | `compra_credito_id` |
| `CONSUMO` | `avaliacao_id` |
| `ESTORNO` | `compra_credito_id` |
| `CONCESSAO` inicial | somente `usuario_id` |
| `AJUSTE` | usuário e referência administrativa opcional |

As referências de contexto serão opcionais na tabela por causa dos diferentes tipos, mas cada tipo terá validação própria para exigir a referência correta quando necessário.

## Consequências

- histórico de crédito explica a origem e o destino de cada movimento;
- consumo fica ligado à avaliação que utilizou o crédito;
- estorno fica ligado à compra que originou os créditos;
- concessões não precisam simular uma compra;
- constraints e services deverão impedir combinações de tipo e referência inválidas.
