# ADR 0062 — Ofertas de crédito configuradas no provedor

- **Status:** superseded
- **Data:** 2026-08-24

## Decisão original

O MVP não terá entidade `PacoteCredito` nem catálogo local de promoções. A regra posterior está definida na ADR 0063.

O webhook ou a referência externa deverá identificar a oferta adquirida. O backend validará a correspondência entre a oferta, o valor pago e os créditos concedidos. Depois da confirmação, `CompraCredito` armazenará um snapshot de:

- referência da oferta;
- quantidade de créditos;
- bônus;
- total concedido;
- valor e moeda;
- referência externa do pagamento.

O frontend não poderá informar livremente quantos créditos devem ser concedidos após o pagamento.

## Consequências originais

- reduz tabelas e administração duplicada de ofertas;
- permite alterar preços na AbacatePay;
- exige integração confiável entre oferta externa e regra de crédito;
- mudanças de oferta não alteram compras antigas;
- a compra livre deverá ser representada por uma oferta ou referência externa validável.
