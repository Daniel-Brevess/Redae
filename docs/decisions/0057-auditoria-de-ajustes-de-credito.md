# ADR 0057 — Auditoria de ajustes de crédito

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

Valores de nota, nível, pontos e quantidades de crédito serão inteiros:

- nota final: 0 a 1000;
- nível de competência: 0 a 5;
- pontos de competência: 0 a 200;
- quantidades de crédito: inteiros positivos.

Transações do tipo `AJUSTE` terão campos adicionais:

- `motivo` obrigatório;
- `administrador_id` obrigatório e relacionado a um usuário com `tipo_usuario = ADMIN`.

Ajustes não poderão ser criados por estudantes nem sem justificativa registrada.

## Consequências

- facilita auditoria de alterações manuais;
- evita valores fracionários em notas e créditos;
- exige autorização administrativa no endpoint de ajuste;
- permite identificar quem alterou o saldo e por qual motivo.
