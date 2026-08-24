# ADR 0041 — Tipos e limites de texto

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

- `Redacao.texto_confirmado` usará `TEXT` no PostgreSQL;
- `Redacao.tema` usará `VARCHAR(500)`;
- `NotaCompetencia.resumo` usará `TEXT`;
- `FeedbackItem.trecho`, `problema`, `explicacao` e `como_melhorar` usarão `TEXT`;
- `FeedbackItem.limitacao` será opcional e usará `TEXT`;
- limites de upload, tamanho de requisição e quantidade de feedbacks serão aplicados na aplicação e na API, não por um limite artificial pequeno no tipo do banco.

## Consequências

- comporta redações e orientações longas;
- mantém o banco adequado para conteúdo textual variável;
- exige validação de tamanho máximo na entrada para proteger custo, desempenho e uso da IA;
- o limite de 500 caracteres do tema mantém o contexto controlável.
