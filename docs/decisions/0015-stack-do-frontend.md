# ADR 0015 — Stack do frontend

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

O frontend do Redaê usará React, TypeScript, Vite e Tailwind CSS. A aplicação será responsável pela experiência do usuário, formulários, editor, envio, acompanhamento do processamento e exibição dos resultados recebidos pela API do backend.

O frontend não será responsável por regras de autorização, persistência direta, segredos ou comunicação direta com o banco de dados.

## Consequências

- mantém a direção técnica já utilizada na fundação do projeto;
- permite tipagem compartilhada por contratos de API, quando aplicável;
- concentra regras de segurança e negócio no backend;
- exige estados explícitos de carregamento, erro, vazio e sucesso nas telas.
