# ADR 0037 — Classificação e retenção de dados do domínio

- **Status:** aceito
- **Data:** 2026-08-24

## Classificação

Serão tratados como dados pessoais associados ao estudante:

- nome e email;
- redações e temas informados;
- notas e feedbacks;
- histórico de avaliações e uso;
- dados de processamento que possam ser associados ao estudante.

O sistema não deverá classificar automaticamente esses dados como dados sensíveis sem análise específica da finalidade e da legislação aplicável. Ainda assim, todo o conteúdo acadêmico será tratado com acesso restrito e proteção equivalente a conteúdo privado do estudante.

## Retenção

- imagens enviadas para OCR: até 10 minutos ou até a confirmação da transcrição, o que ocorrer primeiro;
- redação confirmada, nota e feedback: até a exclusão da conta;
- dados de processamento: somente pelo tempo necessário para operação, suporte e auditoria técnica;
- exclusão da conta: remove os dados acadêmicos associados e permite manter somente metadados técnicos anonimizados quando justificável.

## Consequências

- limita a retenção de arquivos e conteúdo acadêmico;
- facilita responder a solicitações de exclusão;
- exige tarefas de limpeza para imagens e dados técnicos expirados;
- requer controle de acesso em todas as consultas de histórico e avaliação.
