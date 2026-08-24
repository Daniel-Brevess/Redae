# ADR 0002 — Provedor único de IA no MVP

- **Status:** aceito
- **Data:** 2026-08-24

## Contexto

O Redaê precisa transcrever redações enviadas como imagem e gerar correção e feedback estruturado. Usar serviços diferentes para OCR e avaliação aumentaria a quantidade de integrações, os pontos de falha, o custo operacional e a complexidade dos testes.

## Decisão

O MVP usará o Gemini como provedor inicial para as capacidades de IA. O mesmo provedor será responsável por:

- transcrever imagens de redações para texto editável;
- receber o texto confirmado pelo estudante;
- gerar avaliação e feedback estruturado por competência.

A integração ficará isolada em uma interface própria do backend. O sistema poderá utilizar modelos diferentes do Gemini para transcrição e avaliação, conforme custo, qualidade e latência, sem alterar o restante do domínio.

Dados pessoais não serão enviados ao provedor. Para a avaliação, serão enviados somente o texto confirmado e o tema necessário para a correção.

## Consequências

### Positivas

- uma única credencial, integração e política de monitoramento;
- menor esforço de desenvolvimento e manutenção no MVP;
- fluxo multimodal consistente entre transcrição e avaliação;
- possibilidade de trocar o provedor no futuro sem espalhar dependências pelo sistema.

### Negativas e controles

- cria dependência inicial de um fornecedor;
- uma indisponibilidade afeta OCR e avaliação;
- a camada de abstração, limites de custo, retentativas e monitoramento serão obrigatórios;
- a qualidade da transcrição deverá ser validada pelo estudante antes da avaliação.

## Fora desta decisão

Esta ADR não fixa um modelo específico, limites financeiros definitivos ou o desenho final da fila assíncrona. Esses pontos serão definidos nas etapas de custo, ambientes, processamento e observabilidade.
