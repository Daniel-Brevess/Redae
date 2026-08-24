# ADR 0070 — Seeds por ambiente

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

O ambiente local poderá receber seeds sintéticos com:

- administrador fictício;
- estudantes fictícios;
- avaliações concluídas;
- notas e feedbacks de exemplo;
- ofertas de crédito;
- compras e transações de crédito de teste.

Homologação e produção não executarão seeds automáticos de usuários, avaliações, créditos ou compras. Dados desses ambientes deverão ser criados por fluxos próprios e controlados.

Nenhum seed conterá dados reais ou credenciais verdadeiras.

## Consequências

- desenvolvimento local fica reproduzível;
- reduz risco de contaminar ambientes compartilhados;
- exige diferenciar claramente configuração local de dados reais;
- testes de integração poderão criar e limpar seus próprios dados isoladamente.
