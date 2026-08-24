# ADR 0065 — Desativação de ofertas usadas

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

Uma `OfertaCredito` que já tenha sido usada em uma compra não será apagada pelo fluxo administrativo normal. Ela será marcada como `ativo = false` e deixará de aparecer para novas compras.

Compras antigas manterão o snapshot da oferta, preço e créditos concedidos. Uma deleção física poderá ser executada futuramente por procedimento administrativo específico, desde que não quebre referências ou auditoria.

## Consequências

- preserva o histórico comercial;
- impede que uma compra antiga perca seu contexto;
- simplifica consultas de ofertas vigentes;
- exige distinguir desativação de deleção física.
