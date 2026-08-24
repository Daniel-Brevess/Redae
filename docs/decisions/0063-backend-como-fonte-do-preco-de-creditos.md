# ADR 0063 — Backend como fonte do preço e dos créditos

- **Status:** parcialmente superseded
- **Data:** 2026-08-24

## Decisão

O estudante escolherá a quantidade de créditos dentro do Redaê. O backend será responsável por calcular:

- preço da compra;
- bônus promocional, quando aplicável;
- quantidade total de créditos;
- moeda e valor final enviados à AbacatePay.

A AbacatePay será responsável somente pelo processamento financeiro e pela confirmação da transação. Ela não será a fonte da regra de quantos créditos o estudante deve receber.

Ao criar a cobrança, o backend salvará `CompraCredito` com o snapshot da quantidade, bônus, valor e regra aplicada. O webhook autenticado confirmará o pagamento usando essa compra como referência.

O backend nunca aceitará do frontend um valor final ou quantidade concedida sem recalcular e validar os dados.

A configuração de ofertas promocionais será complementada pela ADR 0064. O backend continuará sendo a fonte do preço e dos créditos concedidos.

## Consequências

- regra de preço, crédito e promoção fica centralizada no domínio do Redaê;
- AbacatePay pode ser substituída futuramente;
- exige definir preço-base, limites de compra e regras promocionais no backend;
- compra livre e campanhas promocionais não dependem de catálogo de produtos do provedor;
- o valor confirmado no webhook deverá ser comparado ao snapshot da compra.
