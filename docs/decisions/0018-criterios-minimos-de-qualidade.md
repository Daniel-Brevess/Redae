# ADR 0018 — Critérios mínimos de qualidade para funcionalidades

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

Uma funcionalidade do MVP só será considerada pronta quando cumprir os critérios aplicáveis:

- possui testes unitários para services e regras de aplicação;
- possui teste de integração do fluxo principal com persistência;
- possui testes de autorização para acesso permitido, negado e isolamento por usuário;
- respeita o contrato de requests, responses e erros da API;
- valida respostas estruturadas de OCR/IA e trata falhas, retentativas e estados terminais;
- apresenta estados de carregamento, sucesso, vazio e erro;
- possui validação básica de acessibilidade, incluindo teclado, foco, labels e contraste;
- não expõe secrets, dados pessoais ou conteúdo sensível em respostas e logs.

## Consequências

- reduz a chance de considerar pronta uma feature que só funciona no caminho feliz;
- aumenta o trabalho inicial de cada entrega, mas diminui regressões;
- exige que o pipeline de CI execute os testes adequados antes da integração;
- critérios específicos poderão ser adicionados por funcionalidade sem remover este mínimo.
