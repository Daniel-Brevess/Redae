# ADR 0068 — Preço referenciado na compra

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

Toda `CompraCredito` terá `preco_credito_id` apontando para a versão do preço-base usada no cálculo, inclusive quando a compra utilizar uma `OfertaCredito`.

Além da FK, a compra armazenará snapshot do valor final, moeda, quantidade, bônus e condições aplicadas. A referência ao preço não substituirá o snapshot.

`OfertaCredito` não terá campos `criado_por` ou `atualizado_por` no MVP. A alteração de ofertas será protegida por autorização administrativa, enquanto o histórico comercial ficará preservado nas compras e nos snapshots.

## Consequências

- permite auditar qual preço-base participou do cálculo;
- mantém histórico mesmo se a oferta ou o preço forem desativados;
- evita adicionar campos administrativos sem necessidade imediata;
- logs de auditoria poderão registrar o administrador responsável pela operação quando necessário.
