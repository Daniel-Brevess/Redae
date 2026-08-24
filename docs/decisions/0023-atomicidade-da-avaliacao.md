# ADR 0023 — Atomicidade da avaliação

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

A chamada ao Gemini ocorrerá fora de uma transação do PostgreSQL. Depois que o backend receber e validar a resposta, abrirá uma transação curta para persistir atomicamente:

- avaliação;
- nota de cada competência;
- feedbacks;
- resultado resumido e metadados da avaliação.

Se a validação falhar, nenhum resultado parcial será persistido. O trabalho será marcado como falho e poderá sofrer retentativa técnica controlada.

O processamento deverá ser idempotente: uma mesma conclusão não poderá criar duas avaliações válidas para a mesma redação.

## Consequências

- evita nota sem feedback ou feedback sem nota;
- reduz o tempo de bloqueio do banco;
- separa falha da IA de falha de persistência;
- exige estados e identificadores de trabalho bem definidos;
- a chamada externa não poderá ser desfeita por rollback do banco, por isso a persistência só começa após a resposta validada.
