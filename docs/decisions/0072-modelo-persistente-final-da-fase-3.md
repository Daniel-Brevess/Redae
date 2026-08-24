# ADR 0072 — Modelo persistente final da fase 3

- **Status:** aceito
- **Data:** 2026-08-24

## Entidades persistentes

O modelo final do MVP contém:

- `Usuario`;
- `Avaliacao`;
- `NotaCompetencia`;
- `FeedbackItem`;
- `OfertaCredito`;
- `PrecoCredito`;
- `CompraCredito`;
- `TransacaoCredito`.

`Redacao` é objeto temporário de entrada e não possui tabela própria. `Processamento` é estrutura técnica efêmera e fica fora do histórico funcional.

## Relações centrais

```text
Usuario 1 ─── N Avaliacao
Avaliacao 1 ─── N NotaCompetencia
NotaCompetencia 1 ─── N FeedbackItem
Usuario 1 ─── N CompraCredito
Usuario 1 ─── N TransacaoCredito
OfertaCredito 0..1 ─── N CompraCredito
PrecoCredito 1 ─── N CompraCredito
CompraCredito 0..1 ─── N TransacaoCredito
Avaliacao 0..1 ─── N TransacaoCredito
```

O texto avaliado, tema e origem ficam em `Avaliacao`. O usuário é o proprietário direto da avaliação e das transações de crédito.
