# ADR 0069 — Cálculo de compra livre e oferta

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

Uma `OfertaCredito` terá preço próprio, quantidade de créditos inclusos e bônus. Quando o usuário selecionar uma oferta, o backend usará esses valores para calcular a compra.

Quando o usuário fizer uma compra livre:

- a quantidade será informada pelo usuário;
- o backend usará o `PrecoCredito` vigente;
- não haverá `oferta_credito_id`;
- o preço, a quantidade e o total serão salvos no snapshot da `CompraCredito`.

O frontend nunca será a fonte do preço final ou dos créditos concedidos.

## Consequências

- permite campanhas com preço próprio;
- mantém compra livre simples;
- diferencia claramente oferta promocional e preço-base;
- exige validar no backend qual cálculo se aplica à compra;
- compras antigas não mudam quando a oferta ou o preço-base forem alterados.
