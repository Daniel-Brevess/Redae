# AGENTS.md — Guia de desenvolvimento do Redaê

Este arquivo é o ponto de entrada para qualquer agente que trabalhe no repositório.

Antes de modificar código, leia também:

- [`agents/PROJECT_RULES.md`](agents/PROJECT_RULES.md), com as regras detalhadas de desenvolvimento;
- [`agents/instructions/general.md`](agents/instructions/general.md), com as instruções gerais;
- a documentação relevante em [`docs/`](docs/README.md);
- o estado atual do Git e dos arquivos envolvidos.

## Hierarquia das instruções

1. Instruções do sistema e do usuário;
2. este arquivo;
3. `agents/PROJECT_RULES.md`;
4. `agents/instructions/general.md`;
5. documentação específica da tarefa.

Se houver conflito, não invente uma solução: informe o conflito e peça orientação quando ele puder mudar o comportamento, a arquitetura ou a segurança do produto.

## Princípios

- Faça a menor alteração que resolva a tarefa.
- Preserve o comportamento existente quando a tarefa for estrutural.
- Não invente requisitos nem implemente escopo futuro sem autorização.
- Prefira código simples, explícito, testável e fácil de manter.
- Analise o código e a documentação antes de editar.
- Não faça refatorações “já que estou aqui”.
- Quando uma mudança for brusca, destrutiva ou puder alterar contratos, pare e peça confirmação.

## Arquitetura atual

O backend é um monólito modular Spring Boot, organizado por contexto:

```text
backend/src/main/java/br/com/redae/
├── user/       # identidade, usuários, perfis e papéis
├── auth/       # autenticação, sessões e autorização
├── evaluation/ # redação, processamento, notas e feedback
├── ai/         # AIClient e adaptadores de provedores
└── shared/     # componentes transversais
```

As camadas internas são `controller`, `service`, `repository`, `dto`, `entity` e `config`, quando aplicáveis.

Regras arquiteturais:

- `evaluation` contém o ciclo da avaliação e seu processamento;
- `ai` contém a interface `AIClient` e clientes de provedores;
- serviços de avaliação dependem da interface de IA, não de um provedor concreto;
- controllers não acessam repositories diretamente para executar regras de negócio;
- não criar microservices, filas, Redis, Kafka, CQRS ou novas camadas sem necessidade real e decisão documentada;
- endpoints, migrations, contratos e persistência não devem mudar em uma refatoração estrutural.

## Segurança e dados

Nunca versionar:

- API keys, tokens, senhas ou JWT secrets;
- arquivos `.env` com valores reais;
- credenciais, certificados privados, dumps, logs ou dados de produção;
- redações, fotos, e-mails ou fixtures com dados reais.

Use variáveis de ambiente e dados fictícios. Nunca coloque secrets no frontend, Dockerfile, Compose ou workflows. Não remova controles de autenticação, autorização ou validação para contornar um erro.

O backend deve validar entradas, proteger recursos por usuário e papel, evitar stack trace nas respostas e não registrar redações, feedbacks completos ou credenciais em logs.

## Regras por tecnologia

### Backend

- Java 21 e Spring Boot;
- validação no backend com Bean Validation;
- regras de negócio em services;
- persistência via repositories;
- PostgreSQL e migrations Flyway;
- erros públicos consistentes e sem detalhes internos;
- respostas da IA sempre validadas antes da persistência.

### Frontend

- React, TypeScript, Vite e Tailwind CSS;
- componentes com responsabilidades claras;
- evitar `any` sem justificativa;
- chamadas de API organizadas;
- não confiar no frontend para segurança;
- manter responsividade, acessibilidade e identidade visual do Redaê.

### IA

- chamadas somente pelo backend;
- provedor isolado atrás de `AIClient`;
- tratar indisponibilidade, timeout e respostas inválidas;
- validar schema, notas, competências e evidências;
- não repetir chamadas sem necessidade;
- não apresentar avaliação automática como nota oficial.

## Fluxo obrigatório de trabalho

1. Identificar o objetivo e o escopo da tarefa.
2. Ler as instruções aplicáveis.
3. Inspecionar arquivos, dependências, testes e documentação relacionados.
4. Verificar o estado do Git antes de editar.
5. Planejar a menor alteração necessária.
6. Implementar preservando contratos e comportamento não envolvidos.
7. Atualizar documentação quando a mudança alterar arquitetura, API, banco, segurança ou comportamento importante.
8. Executar testes e verificações proporcionais ao risco.
9. Revisar `git diff`, `git diff --check` e arquivos modificados.
10. Relatar alterações, validações, limitações e qualquer decisão pendente.

## Testes e qualidade

No backend, usar conforme o escopo:

```bash
mvn --batch-mode verify
mvn --batch-mode spotless:check
```

No frontend, usar conforme o escopo:

```bash
npm run format:check
npm run lint
npm run typecheck
npm test -- --run
npm run build
```

Não criar testes artificiais apenas para elevar cobertura. Teste comportamento real, falhas, autorização, validação, idempotência e efeitos de persistência quando aplicável.

## Git

- Commits devem representar uma alteração lógica.
- Prefira Conventional Commits (`feat:`, `fix:`, `refactor:`, `test:`, `docs:`, `chore:`).
- Nunca incluir secrets, `node_modules`, `target` ou arquivos temporários.
- Não reescrever histórico nem executar comandos destrutivos sem autorização explícita.
- Antes de commit ou push, revisar o diff e confirmar que não há alterações não relacionadas.
- Não fazer commit ou push sem solicitação do usuário.

## Documentação

Os documentos canônicos ficam diretamente em `docs/`:

- `produto.md`: visão, escopo e regras do produto;
- `arquitetura.md`: módulos, fluxos, persistência e segurança;
- `api.md` e `api.openapi.yaml`: contratos HTTP;
- `decisoes.md`: decisões vigentes consolidadas;
- `calibracao.md`: redações, prompts e resultados de comparação;
- `design.md`: direção visual e telas;
- `testes.md`: validações automatizadas e manuais;
- `roadmap.md`, `fase-8-entregas.md`: planejamento e acompanhamento.

Documentação deve refletir o estado real do código. Ao tomar uma decisão estrutural, atualize o documento canônico correspondente.

## Critério de conclusão

Uma tarefa só está pronta quando:

- o escopo solicitado foi atendido;
- o comportamento não relacionado foi preservado;
- testes e verificações relevantes passaram;
- não há secrets ou dados reais adicionados;
- a documentação necessária foi atualizada;
- o diff foi revisado;
- o relatório informa claramente o que foi feito e o que não foi possível validar.

