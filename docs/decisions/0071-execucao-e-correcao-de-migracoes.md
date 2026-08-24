# ADR 0071 — Execução e correção de migrações

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

O Flyway executará automaticamente as migrações pendentes na inicialização do backend, respeitando a ordem e o histórico registrado.

Uma migração já aplicada nunca será editada, renomeada ou removida. Correções serão feitas por uma nova migração versionada, preservando a sequência e a auditabilidade do schema.

Migrações destrutivas ou de produção deverão ser revisadas antes do deploy e possuir estratégia para preservar ou tratar os dados existentes.

## Consequências

- ambientes convergem automaticamente para o schema esperado;
- o histórico de alterações permanece confiável;
- rollback lógico exige nova migração, não restauração silenciosa do histórico;
- alterações de alto risco precisam de revisão e backup operacional.
