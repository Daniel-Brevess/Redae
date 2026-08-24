# ADR 0008 — Logs estruturados e proteção de conteúdo sensível

- **Status:** aceito
- **Data:** 2026-08-24

## Contexto

O sistema precisa investigar falhas, latência e custo de OCR e avaliação por IA. Ao mesmo tempo, redações, imagens, dados pessoais, prompts e feedback podem conter conteúdo privado do estudante e não devem ser copiados indiscriminadamente para logs.

## Decisão

O backend produzirá logs estruturados com metadados operacionais, incluindo, quando aplicável:

- `traceId` e identificador da operação;
- módulo, endpoint e resultado;
- duração e tentativa;
- identificador interno não diretamente identificável do usuário;
- provedor, modelo e tipo de operação de IA;
- consumo estimado, limites aplicados e código de erro.

Os logs não poderão conter:

- texto integral de redações ou feedback;
- imagens ou conteúdo bruto enviado pelo estudante;
- prompts completos e respostas completas do provedor;
- e-mails, senhas, tokens, chaves ou cookies;
- dados pessoais desnecessários para diagnóstico.

Quando uma investigação exigir conteúdo, ele deverá ser acessado por fluxo controlado e auditado, não por log permanente.

## Consequências

- melhora a investigação de custo, desempenho e falhas;
- reduz a exposição de dados pessoais e acadêmicos;
- exige mascaramento e revisão de campos antes de registrar novos eventos;
- logs de produção deverão possuir retenção e acesso restritos.
