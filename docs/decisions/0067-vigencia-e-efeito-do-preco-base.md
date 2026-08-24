# ADR 0067 — Vigência e efeito do preço-base

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

Para `BRL`, somente uma versão de `PrecoCredito` poderá estar vigente em cada momento. Alterações de preço criarão uma nova versão com período de vigência próprio.

O novo preço valerá somente para novas compras criadas após o início de sua vigência. Compras existentes, pagas ou pendentes, manterão o snapshot do valor calculado no momento da criação, conforme a política de validade da cobrança.

## Consequências

- evita ambiguidade no cálculo do preço;
- preserva o histórico de compras;
- permite agendar alteração de preço;
- exige validar sobreposição de períodos e vigência futura.
