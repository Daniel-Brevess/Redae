# ADR 0011 — Ambiente local com PostgreSQL em Docker

- **Status:** aceito
- **Data:** 2026-08-24

## Contexto

O backend Spring Boot precisa de um banco PostgreSQL para desenvolvimento e testes locais. O ambiente deve ser reproduzível sem depender de uma instalação manual do banco na máquina do desenvolvedor.

## Decisão

O ambiente local usará PostgreSQL executado via Docker. A configuração do banco, da aplicação e do provedor de IA ficará fora do código, por meio de variáveis de ambiente e arquivos de exemplo sem segredos reais.

O acesso à IA será configurável no ambiente local, mas nenhuma chave real será versionada. Os dados usados localmente deverão ser fictícios, sintéticos ou anonimizados; dados reais de estudantes não serão usados no desenvolvimento.

## Consequências

- ambiente de desenvolvimento mais próximo do banco oficial do MVP;
- onboarding reproduzível para novos desenvolvedores;
- necessidade de documentar comandos de inicialização, credenciais locais e migrações;
- o Docker local não define a topologia de produção.
