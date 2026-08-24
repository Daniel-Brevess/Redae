# ADR 0034 — Notas por competência e itens de feedback

- **Status:** aceito
- **Data:** 2026-08-24

## NotaCompetencia

Cada avaliação terá uma nota para C1, C2, C3, C4 e C5. `NotaCompetencia` terá:

- `id` UUID;
- `avaliacao_id` FK;
- `competencia_codigo` com valor C1–C5;
- `nivel` de 0 a 5;
- `pontos` calculados pelo backend;
- `resumo` da avaliação da competência.

Uma restrição única em `(avaliacao_id, competencia_codigo)` impedirá duas notas para a mesma competência dentro da mesma avaliação.

## FeedbackItem

Cada `NotaCompetencia` poderá possuir vários `FeedbackItem`, contendo:

- `id` UUID;
- `nota_competencia_id` FK;
- `trecho` literal da redação, quando houver evidência;
- `problema` identificado;
- `explicacao` do impacto;
- `como_melhorar`;
- `limitacao` opcional.

## Consequências

- permite vários erros e orientações por competência;
- mantém a nota separada das evidências pedagógicas;
- permite ausência de trecho quando não houver evidência suficiente;
- exige validar que trechos citados existam no texto confirmado.
