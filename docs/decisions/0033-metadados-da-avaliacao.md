# ADR 0033 — Metadados da avaliação

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

`Avaliacao` terá os campos principais:

- `id` UUID;
- `redacao_id` FK única para `Redacao`;
- `status`;
- `nota_final`;
- `versao` do contrato/formato de avaliação;
- `modelo_ia` usado na geração;
- `gerada_em`;
- `created_at`;
- `updated_at`.

O MVP não terá `tipo_avaliacao`. Uma eventual primeira avaliação gratuita e resumida será tratada por regra de produto, limite de uso ou plano, sem criar uma entidade ou variante estrutural.

## Consequências

- preserva rastreabilidade quando o modelo ou contrato mudar;
- mantém uma única estrutura de avaliação;
- evita misturar regra comercial com o modelo acadêmico;
- permite uma futura avaliação inicial sem migração obrigatória.
