# ADR 0064 — Ofertas de crédito gerenciadas pelo administrador

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

O MVP terá uma entidade `OfertaCredito` administrável pelo `ADMIN`. O administrador poderá criar, alterar, ativar e desativar ofertas promocionais. No fluxo normal, “remover” significa desativar. A deleção física de uma oferta usada fica fora do fluxo administrativo comum.

Campos principais:

- `id` UUID;
- `nome`;
- `creditos_inclusos`;
- `bonus_creditos`;
- `preco` em `NUMERIC(12,2)`;
- `moeda` `BRL`;
- `ativo`;
- `vigencia_inicio` opcional;
- `vigencia_fim` opcional;
- `limite_de_uso` opcional;
- `created_at`;
- `updated_at`.

`CompraCredito` poderá apontar para uma oferta ou representar uma compra livre. No momento da criação, o backend salvará um snapshot dos créditos, bônus, preço, moeda e condições aplicadas. Alterações posteriores na oferta não mudarão compras antigas.

O administrador não poderá editar compras pagas nem alterar transações de crédito existentes. Ajustes deverão usar `TransacaoCredito` do tipo `AJUSTE`, com motivo e administrador responsável.

## Consequências

- permite campanhas promocionais sem depender do provedor de pagamento;
- mantém compra livre e ofertas no mesmo fluxo;
- exige autorização administrativa para CRUD de ofertas;
- evita exclusão física acidental de ofertas já usadas e preserva o histórico;
- cria um catálogo administrável, mas somente para créditos, não para temas.
