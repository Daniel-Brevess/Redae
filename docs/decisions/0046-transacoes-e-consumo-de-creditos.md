# ADR 0046 — Transações e consumo de créditos

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

O saldo de créditos será derivado de um histórico de transações. As operações previstas são:

- `COMPRA`;
- `CONCESSAO`;
- `CONSUMO`;
- `ESTORNO`;
- `AJUSTE`.

Uma avaliação consome um crédito quando o estudante confirma o envio e o sistema aceita a solicitação. Se o processamento falhar definitivamente por problema técnico sem avaliação válida, o crédito será estornado.

Falha causada por conteúdo inválido, ausência de tema ou violação de regra de entrada não deverá gerar consumo definitivo; a regra exata será aplicada antes da criação da avaliação.

## Consequências

- mantém histórico auditável de saldo e uso;
- evita alterar um saldo sem explicação;
- exige idempotência no consumo;
- exige vincular consumo e estorno à avaliação correspondente;
- permite conceder o primeiro uso gratuito sem criar um tipo especial de avaliação.
