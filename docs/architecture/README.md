# Arquitetura do Redaê

## Visão geral

- [Contexto do sistema](contexto-do-sistema.md)
- [Containers do sistema](containers-do-sistema.md)
- [Fluxo de dados entre componentes](fluxo-de-dados-entre-componentes.md)
- [Fluxo de redação digitada](fluxo-redacao-digitada.md)
- [Fluxo de redação por imagem](fluxo-redacao-por-imagem.md)
- [Fluxo de avaliação por IA](fluxo-avaliacao-por-ia.md)

## Resumo da solução

O Redaê usa um frontend React, TypeScript, Vite e Tailwind CSS, consumindo um backend Spring Boot em monólito modular. O backend possui os módulos `identity`, `auth`, `essays`, `processing`, `evaluation`, `history` e `support`, organizados internamente em camadas de controller, service, repository, DTO, entity e config.

O PostgreSQL é a fonte oficial dos dados persistentes. O Gemini é o provedor inicial de IA para transcrição e avaliação. Imagens são temporárias, OCR e avaliação são assíncronos, e o frontend acompanha o processamento por polling.

O código das features pode ser público. Secrets, dados reais, bancos, backups, logs e configurações operacionais permanecem fora do repositório.

## Caminho resumido de uma redação

1. O estudante usa o frontend.
2. O frontend envia dados ao backend autenticado.
3. O backend persiste somente o que foi confirmado e cria um trabalho assíncrono.
4. O processador chama o Gemini com o mínimo necessário.
5. O backend valida e salva o resultado da avaliação.
6. O frontend consulta o status e apresenta nota e feedback.
