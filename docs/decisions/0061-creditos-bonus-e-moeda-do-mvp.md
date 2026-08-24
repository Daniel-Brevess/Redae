# ADR 0061 — Créditos, bônus e moeda do MVP

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

O MVP trabalhará somente com a moeda `BRL`.

Não haverá uma entidade local de pacote promocional. A oferta será identificada pela referência externa da AbacatePay e refletida no snapshot da compra.

`CompraCredito` diferenciará:

- `quantidade_creditos`: créditos comprados;
- `bonus_creditos`: créditos promocionais;
- `creditos_totais`: soma concedida pela compra.

Esses valores serão preservados no snapshot da compra. A transação `COMPRA` registrará o total de créditos concedidos, enquanto a compra manterá a separação entre valor contratado e bônus.

## Consequências

- histórico financeiro explica quanto foi comprado e quanto foi bônus;
- campanhas promocionais não alteram compras antigas;
- não há conversão cambial no MVP;
- suporte a outras moedas exigirá decisão própria sobre preços, arredondamento e pagamentos.
