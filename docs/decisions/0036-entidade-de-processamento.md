# ADR 0036 — Entidade de processamento assíncrono

- **Status:** superseded by ADR 0039 and ADR 0055
- **Data:** 2026-08-24

## Decisão

O MVP terá uma entidade técnica temporária `Processamento` para controlar cada trabalho assíncrono de OCR ou avaliação.

Campos principais:

- `id` UUID;
- `tipo`, com valores `OCR` ou `AVALIACAO`;
- referência ao contexto de negócio (`redacao_id` ou `avaliacao_id`);
- `status`;
- `tentativas`;
- `erro_codigo` e mensagem técnica controlada;
- `iniciado_em`;
- `concluido_em`;
- `expira_em`;
- `created_at` e `updated_at`.

Para OCR, poderá haver uma referência temporária ao arquivo, seu tipo e prazo de expiração. A imagem não será armazenada no PostgreSQL. O arquivo permanecerá em armazenamento temporário e será excluído após confirmação ou expiração.

Após a conclusão bem-sucedida, o resultado será salvo em `Redacao` e `Avaliacao`, e o registro de `Processamento` será removido. Em caso de falha, o registro permanecerá somente durante a janela de retentativa e diagnóstico técnico.

## Consequências

- permite acompanhar e retentar trabalhos sem misturar estado técnico com entidades de negócio;
- facilita auditoria de tentativas e falhas;
- evita armazenar binários no banco principal;
- exige garantir que referências temporárias não sejam reutilizadas após expiração;
- exige regras de idempotência entre `Processamento` e `Avaliacao`;
- impede usar `Processamento` como fonte de histórico após o resultado final.
