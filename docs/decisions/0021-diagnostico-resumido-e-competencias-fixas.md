# ADR 0021 — Diagnóstico resumido e competências fixas

- **Status:** parcialmente superseded
- **Data:** 2026-08-24

## Diagnóstico — decisão substituída

O diagnóstico inicial foi removido do MVP pela ADR 0032. A decisão atual não cria a entidade nem persiste suas respostas.

Qualquer diagnóstico futuro deverá ser revisitado em uma nova decisão.

## Competências

As competências C1, C2, C3, C4 e C5 serão códigos fixos no backend no MVP. Não haverá uma entidade ou tabela `Competencia` neste momento.

`NotaCompetencia` terá um campo `competencia_codigo` com valor restrito ao conjunto C1–C5 e uma FK para `Avaliacao`. Os itens de feedback continuarão ligados à `NotaCompetencia`.

```text
Avaliacao 1 ─── N NotaCompetencia
NotaCompetencia.competencia_codigo ∈ {C1, C2, C3, C4, C5}
NotaCompetencia 1 ─── N FeedbackItem
```

## Consequências

- reduz tabelas e joins no MVP;
- mantém o modelo suficiente para nota e feedback por competência;
- evita transformar uma estrutura fixa em dado administrável sem necessidade;
- uma futura configuração dinâmica de competências exigirá migração para uma tabela de catálogo.
