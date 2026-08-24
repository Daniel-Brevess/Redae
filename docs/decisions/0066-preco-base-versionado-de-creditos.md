# ADR 0066 — Preço-base versionado de créditos

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

O preço-base por crédito será administrável pelo `ADMIN` e persistido em uma entidade versionada `PrecoCredito`.

Campos principais:

- `id` UUID;
- `valor_por_credito` em `NUMERIC(12,2)`;
- `moeda` `BRL`;
- `ativo`;
- `vigente_desde`;
- `vigente_ate` opcional;
- `administrador_id`;
- `created_at`;
- `updated_at`.

Uma alteração de preço criará uma nova versão, sem sobrescrever o valor histórico. `CompraCredito` apontará para o preço usado e também salvará o snapshot do valor final, quantidade, bônus e moeda.

Somente uma configuração deverá estar vigente para uma data e moeda, evitando sobreposição de preços ativos.

## Consequências

- administrador pode alterar preço sem novo deploy;
- compras antigas permanecem auditáveis;
- exige validar vigência e impedir duas versões simultâneas;
- cria uma relação adicional no modelo, mas evita histórico implícito e frágil.
