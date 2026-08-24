# ADR 0013 — Secrets gerenciados fora do repositório

- **Status:** aceito
- **Data:** 2026-08-24

## Contexto

O Redaê terá chaves de IA, credenciais de banco, segredos de JWT e configurações específicas de cada ambiente. Esses valores não devem ser versionados nem compartilhados por meio de arquivos de configuração no GitHub.

## Decisão

Os secrets e valores sensíveis serão cadastrados diretamente na ferramenta correspondente ao ambiente:

- máquina local ou gerenciador local de desenvolvimento;
- ferramenta de deploy da homologação;
- ferramentas de deploy e infraestrutura da produção.

Local, homologação e produção terão secrets distintos. Não será criado ou versionado um `.env.example` nem outro arquivo com nomes e estrutura de configuração destinado a ser preenchido manualmente.

O código deverá ler configurações por meio do mecanismo padrão do ambiente, sem incluir valores reais, chaves, senhas, tokens ou strings de conexão. A documentação manterá apenas um catálogo conceitual das configurações necessárias, sem valores secretos.

## Consequências

- reduz o risco de publicação acidental de credenciais;
- evita que um secret de um ambiente seja reutilizado em outro;
- exige configurar cada ambiente antes de executar o sistema;
- problemas de configuração precisarão ser diagnosticados pela presença e validade dos secrets na ferramenta de deploy.

## Limite desta decisão

Esta ADR trata de secrets e configuração. Ela não decide quais partes do código-fonte ficarão em repositórios públicos ou privados.
