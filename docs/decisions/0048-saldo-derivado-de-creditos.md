# ADR 0048 — Saldo derivado de créditos

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

`TransacaoCredito.quantidade` será sempre um inteiro positivo. O efeito da operação será determinado por `tipo`:

- entradas: `COMPRA`, `CONCESSAO`, `ESTORNO`;
- saída: `CONSUMO`;
- `AJUSTE` deverá registrar explicitamente se é entrada ou saída por regra própria, sem usar quantidade negativa.

O saldo será calculado pela soma das entradas menos as saídas. Não haverá coluna `saldo` em `Usuario` nem outro saldo duplicado como fonte oficial.

O cálculo e o consumo deverão ocorrer com proteção contra concorrência para impedir que duas avaliações usem o mesmo crédito.

Compras confirmadas gerarão uma transação `COMPRA`. A confirmação deverá ser idempotente para que repetição de webhook ou callback não duplique créditos.

## Consequências

- histórico explica sempre a origem do saldo;
- evita divergência entre saldo e transações;
- exige consulta ou projeção eficiente quando houver muitas transações;
- exige transação/lock adequado no consumo concorrente.
