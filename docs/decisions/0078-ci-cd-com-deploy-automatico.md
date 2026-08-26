# ADR 0078 — CI/CD com deploy automático

- **Status:** aceito
- **Data:** 2026-08-26

## Contexto

O Redaê já utiliza GitHub Actions para validação contínua do frontend e do
backend. O projeto também precisa publicar as mudanças aprovadas sem depender
de uma execução manual de deploy.

O frontend e o backend serão hospedados separadamente, conforme a decisão de
ambientes do projeto: frontend na Vercel e backend no Render.

## Decisão

O Redaê adotará CI/CD com a seguinte regra:

1. Pull requests executam somente as validações de CI.
2. Pushes na branch `main` executam o CI novamente.
3. O deploy automático só pode começar depois que as validações do CI forem
   concluídas com sucesso.
4. O frontend será implantado na Vercel.
5. O backend será implantado no Render usando sua imagem Docker.
6. As migrations do Flyway serão executadas pelo backend no ambiente de
   destino, respeitando a ordem e o versionamento das migrations.

O CD não armazenará secrets no repositório. Tokens de deploy, URLs privadas,
credenciais de banco, chaves JWT e chaves de integrações permanecerão nas
variáveis protegidas do GitHub, Vercel, Render ou do ambiente correspondente.

## Fluxo

```text
Pull Request
    ↓
CI: testes, qualidade, segurança e build

Merge ou push na main
    ↓
CI novamente
    ↓
Deploy automático
    ├── Frontend → Vercel
    └── Backend  → Render
```

## Condições operacionais

- A branch `main` será a fonte oficial de publicação.
- Um CI com falha bloqueia o deploy.
- O deploy deve utilizar as variáveis do ambiente de destino, nunca as
  credenciais do Docker Compose local.
- Frontend e backend devem possuir URLs de ambiente configuradas
  explicitamente.
- O backend deve disponibilizar um health check para validação após o deploy.
- Mudanças de banco devem ser feitas por uma nova migration Flyway revisada.
- Um problema no frontend CI deve ser investigado no log específico da execução
  antes de alterar o pipeline de CD.

## Consequências

### Benefícios

- reduz o trabalho manual após cada mudança aprovada;
- mantém o deploy condicionado à qualidade mínima do código;
- publica frontend e backend a partir da mesma versão da `main`;
- preserva a separação entre ambiente local e ambientes hospedados.

### Custos e riscos

- o estado da `main` passa a ter impacto direto nos ambientes publicados;
- uma falha de configuração na Vercel ou no Render pode interromper o deploy;
- migrations incompatíveis exigem cuidado antes do merge;
- será necessário configurar secrets e permissões nas plataformas de deploy.

## Fora desta decisão

Esta ADR não define ainda:

- domínio de produção;
- banco PostgreSQL específico de produção;
- estratégia de rollback automatizado;
- blue-green deploy ou múltiplas réplicas;
- observabilidade avançada.

Esses pontos poderão ser registrados em decisões próprias quando a operação
de produção for configurada.
