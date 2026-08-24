# ADR 0043 — Nulidade e persistência atômica da avaliação

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

Enquanto `Avaliacao.status` for `PENDENTE`, `PROCESSANDO` ou `FALHOU`:

- `nota_final` permanecerá nula;
- `gerada_em` permanecerá nulo;
- não existirão `NotaCompetencia` nem `FeedbackItem` associados como resultado válido.

Somente após a resposta da IA ser validada e a transação de persistência concluir com sucesso serão criados:

- as cinco `NotaCompetencia`;
- os `FeedbackItem` correspondentes;
- `nota_final`;
- `gerada_em`;
- estado `CONCLUIDA`.

## Consequências

- impede exibir resultados parciais ou falsos;
- simplifica consultas: resultado válido implica avaliação concluída;
- exige que a transação salve o conjunto completo ou não salve nenhum item;
- falhas ficam representadas pelo estado, sem dados acadêmicos incompletos.
