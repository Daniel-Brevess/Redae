# ADR 0012 — Ambientes de homologação e produção

- **Status:** aceito
- **Data:** 2026-08-24

## Contexto

O sistema precisa validar mudanças sem misturar testes com dados reais. A hospedagem inicial prevista utiliza Vercel para o frontend e Render para o backend.

## Decisão

O Redaê terá três ambientes separados:

### Local

- frontend e backend executados pelo desenvolvedor;
- PostgreSQL via Docker;
- integrações configuradas por variáveis de ambiente;
- dados sintéticos, fictícios ou anonimizados.

### Homologação

- frontend e backend implantados separadamente para validação;
- banco PostgreSQL próprio, isolado da produção;
- dados fictícios ou anonimizados;
- limites, URLs, credenciais e nível de logs próprios do ambiente;
- pode usar uma configuração controlada do provedor de IA para testes.

### Produção

- frontend hospedado na Vercel;
- backend hospedado no Render;
- PostgreSQL separado e protegido;
- dados reais sujeitos a controle de acesso, backup e retenção;
- limites de IA, logs e monitoramento configurados para operação real.

Nenhum banco será compartilhado entre homologação e produção.

## Consequências

- reduz o risco de testes alterarem dados reais;
- permite validar deploy e integração antes da publicação;
- exige configuração independente e migrações controladas por ambiente;
- a disponibilidade e os recursos de produção poderão ser ampliados sem alterar o ambiente local.
