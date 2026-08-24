# ADR 0044 — Falha de avaliação sem erro permanente

- **Status:** superseded by ADR 0045
- **Data:** 2026-08-24

## Decisão

Quando o processamento da avaliação falhar:

- `Redacao.status` será `FALHOU`;
- `Avaliacao.status` será `FALHOU`;
- `nota_final`, `gerada_em`, `NotaCompetencia` e `FeedbackItem` permanecerão nulos ou inexistentes;
- o estudante receberá uma mensagem segura e compreensível;
- o diagnóstico técnico ficará no `Processamento` efêmero durante a janela de retentativa e nos metadados permitidos dos logs;
- não haverá um campo permanente de erro técnico exposto no resultado acadêmico.

Após uma retentativa técnica bem-sucedida, a redação e a avaliação poderão avançar para seus estados finais.

## Consequências

- evita expor detalhes internos ao estudante;
- mantém o histórico acadêmico livre de resultados incompletos;
- exige correlação entre trabalho técnico e mensagem pública sem persistir o erro integral;
- falhas expiradas dependerão dos logs permitidos para investigação.
