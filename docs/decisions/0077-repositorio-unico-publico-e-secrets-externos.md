# ADR 0077 — Repositório único público e secrets externos

- **Status:** aceito
- **Data:** 2026-08-25
- **Supersede:** ADR 0014

## Contexto

O projeto inicialmente considerou separar um repositório de demonstração
público de um repositório privado com dados ou configurações sensíveis. Essa
separação não é necessária quando o código, as migrations e a documentação
podem ser públicos e os valores sensíveis ficam exclusivamente nos ambientes
de execução.

## Decisão

O Redaê usará um único repositório público para o código-fonte do projeto.

O repositório público poderá conter:

- frontend e backend;
- migrations e contratos de API;
- documentação técnica e decisões arquiteturais;
- dados sintéticos, quando necessários para testes;
- implementações fake ou sandbox de integrações externas.

Secrets, credenciais e dados reais serão configurados fora do Git, diretamente
no ambiente correspondente:

- gerenciador local de desenvolvimento;
- secrets do CI;
- plataforma de hospedagem;
- ferramenta de infraestrutura ou banco.

Não serão versionados `.env`, `.env.example`, arquivos de configuração de
exemplo, senhas padrão, tokens, strings de conexão com credenciais, dumps,
backups ou dados reais.

## Configuração local

Os nomes conceituais das variáveis podem ser documentados, mas seus valores
devem ser fornecidos pelo ambiente. O Docker Compose exigirá os valores
necessários para PostgreSQL sem oferecer uma senha padrão no repositório.

## Consequências

- qualquer pessoa pode inspecionar o código e as migrations;
- não existe sincronização entre dois repositórios de código;
- cada ambiente precisa ser configurado antes da execução;
- perda ou exposição de um secret deve ser tratada no gerenciador do ambiente;
- o repositório público nunca deve ser tratado como local seguro para dados.

## Regra de segurança

Antes de cada commit, deve-se verificar que não foram adicionados secrets,
credenciais, dados reais ou arquivos ignorados forçados ao Git.
