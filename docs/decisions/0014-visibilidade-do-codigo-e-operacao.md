# ADR 0014 — Visibilidade do código e operação privada

- **Status:** aceito
- **Data:** 2026-08-24

## Problema

O projeto deseja disponibilizar um repositório público com frontend e backend parcialmente funcionais, mantendo privados dados sensíveis, secrets e eventualmente componentes operacionais.

## Opção recomendada

Manter o código das features no repositório público e separar o que é privado por natureza:

### Repositório público

- frontend e backend funcionais;
- regras de produto necessárias para demonstrar as features;
- contratos de API e documentação;
- implementações fake ou sandbox para integrações externas;
- dados sintéticos e fixtures de desenvolvimento;
- testes sem dados reais.

### Ambiente ou repositório privado

- secrets e credenciais;
- configurações de deploy;
- infraestrutura e operações internas;
- bancos e backups;
- dados reais de estudantes;
- integrações privadas, caso existam regras que não devam ser publicadas.

O repositório público nunca conterá banco de produção, dumps, imagens de usuários, tokens, senhas, strings de conexão ou logs reais.

## Alternativa para código de negócio privado

Se também houver necessidade de esconder regras de negócio, o backend público deverá depender de interfaces e adaptadores fake, enquanto as implementações privadas ficarão em outro repositório ou pacote privado. Essa opção aumenta a complexidade de build e deploy e deixa o projeto público menos completo.

## Decisão validada

As regras de negócio e as features do MVP poderão permanecer públicas. A privacidade ficará restrita a dados reais, secrets, credenciais, infraestrutura operacional, configurações de deploy e integrações que eventualmente contenham informação proprietária.
