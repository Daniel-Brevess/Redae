# ADR 0029 — Flyway como fonte oficial do schema

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

O schema do PostgreSQL será criado e alterado por migrações SQL versionadas com Flyway. Cada mudança estrutural deverá ser revisável, reproduzível e aplicada na mesma ordem em local, homologação e produção.

O Hibernate não será a fonte oficial de alterações do banco:

- homologação e produção usarão `ddl-auto=validate`;
- testes isolados poderão usar `create-drop` quando o banco for descartável;
- desenvolvimento local poderá usar `update` temporariamente para acelerar prototipação, mas toda alteração deverá ser convertida em migração Flyway antes de integração;
- a nulidade, os defaults, as chaves e as restrições serão definidos explicitamente nas migrações.

Uma coluna nullable será representada normalmente na migração, sem depender de criação automática do Hibernate.

## Dados de desenvolvimento

Seeds e fixtures serão sintéticos, contendo usuários, redações, diagnósticos, avaliações e feedbacks fictícios. Não serão usados dados reais ou anonimizados sem necessidade.

## Consequências

- histórico e ordem das mudanças ficam explícitos;
- ambientes mantêm o mesmo contrato de banco;
- reduz risco de alteração implícita ou divergência de schema;
- desenvolvimento local mantém uma opção rápida, mas não pode depender dela para publicar mudanças.
