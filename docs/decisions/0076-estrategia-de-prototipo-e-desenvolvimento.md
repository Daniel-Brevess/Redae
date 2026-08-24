# ADR 0076 — Estratégia de protótipo e desenvolvimento

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

O Redaê não produzirá wireframes formais como entrega obrigatória da fase 5. A fase documentará fluxos, objetivos das telas, estados, textos, responsividade, acessibilidade e critérios de aceitação visual.

Após a fase 6, será criado um protótipo focado no fluxo principal para avaliação do proprietário do produto. O protótipo servirá para validar a experiência antes de expandir a implementação.

Depois da validação, o desenvolvimento seguirá por fatias verticais e independentes: cada feature deverá atravessar interface, contrato de API, service, repository, banco e testes necessários.

## Fluxo inicial recomendado

Cadastro/login → criação de avaliação digitada → processamento → consulta da nota e feedback.

## Consequências

- reduz o tempo investido em artefatos visuais que podem mudar;
- mantém as decisões de experiência claras o suficiente para orientar o protótipo;
- permite validar cedo o fluxo mais importante do produto;
- evita construir várias features sem feedback visual e funcional;
- exige disciplina para não transformar o protótipo em código de produção sem revisão.
