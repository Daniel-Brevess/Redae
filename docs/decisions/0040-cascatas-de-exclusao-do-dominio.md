# ADR 0040 — Cascatas de exclusão do domínio

- **Status:** superseded by ADR 0045
- **Data:** 2026-08-24

## Decisão

As exclusões respeitarão a propriedade dos dados:

- excluir `Usuario` exclui suas `Redacao`, `Avaliacao`, `NotaCompetencia` e `FeedbackItem`;
- excluir `Redacao` exclui sua `Avaliacao`, `NotaCompetencia` e `FeedbackItem`;
- `Processamento` efêmero não será mantido como dependência permanente e será limpo por conclusão ou expiração;
- não haverá registros acadêmicos órfãos após a exclusão do proprietário ou da redação.

As cascatas serão implementadas com cuidado nas FKs e nos services de aplicação, com testes de exclusão e verificação de que nenhum dado de outro estudante seja afetado.

## Consequências

- simplifica exclusão de conta e cumprimento da política de retenção;
- reduz registros sem proprietário;
- exige testes transacionais e validação de escopo antes de executar exclusões;
- metadados técnicos anonimizados ficam fora da cascata acadêmica quando houver justificativa operacional.
