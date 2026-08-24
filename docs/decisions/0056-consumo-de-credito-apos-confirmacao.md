# ADR 0056 — Consumo de crédito após confirmação

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

No fluxo digitado, o crédito será consumido quando o estudante confirmar o texto e a `Avaliacao` for criada.

No fluxo por imagem, o OCR não consumirá crédito. O crédito somente será consumido depois que:

1. a transcrição for gerada;
2. o estudante revisar e confirmar o texto;
3. a `Avaliacao` for criada.

Falha de OCR ou abandono antes da confirmação não gera consumo nem estorno. Falha técnica após a criação da avaliação segue a regra de estorno técnico.

## Consequências

- o estudante não perde crédito por uma falha de transcrição;
- compra e avaliação ficam vinculadas somente quando há texto confirmado;
- o consumo precisa ser idempotente com a criação da avaliação;
- o upload e OCR podem ocorrer antes de validar saldo, desde que não criem avaliação nem cobrem crédito.
