# ADR 0019 — Redação confirmada e feedback por competência

- **Status:** superseded by ADR 0045
- **Data:** 2026-08-24

## Decisão

O MVP não persistirá rascunhos. O texto digitado ou editado no frontend só será enviado ao backend quando o estudante confirmar o envio.

Após a confirmação, o texto será salvo como uma versão imutável da redação submetida. A imagem usada para OCR continuará temporária e não fará parte do registro permanente da redação.

Uma redação terá no máximo uma avaliação válida. A avaliação poderá ser tentada novamente apenas quando a operação anterior falhar tecnicamente.

O resultado será estruturado assim:

- `Avaliacao` pertence a uma `Redacao`;
- `Avaliacao` possui uma nota para cada competência C1–C5;
- cada nota de competência pertence a uma `Competencia`;
- cada nota de competência pode possuir vários `FeedbackItem`;
- cada feedback poderá conter trecho citado, explicação e orientação de melhoria.

## Relacionamentos principais

```text
Redacao 1 ─── 0..1 Avaliacao
Avaliacao 1 ─── N NotaCompetencia
Competencia 1 ─── N NotaCompetencia
NotaCompetencia 1 ─── N FeedbackItem
```

As chaves estrangeiras ficam nas entidades dependentes. Uma restrição de unicidade em `Avaliacao.redacao_id` impede duas avaliações válidas para a mesma redação.

## Consequências

- preserva o texto exato usado na avaliação;
- permite consultar o histórico sem guardar imagens ou rascunhos;
- suporta vários erros e orientações por competência;
- facilita validar trechos citados contra o texto confirmado;
- exige tratar a redação submetida como imutável e criar nova submissão se o estudante quiser enviar outro texto.
