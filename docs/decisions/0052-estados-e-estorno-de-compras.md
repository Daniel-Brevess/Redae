# ADR 0052 — Estados e estorno de compras de crédito

- **Status:** aceito
- **Data:** 2026-08-24

## Estados

`CompraCredito.status` terá os valores:

- `CRIADA`;
- `PENDENTE`;
- `PAGA`;
- `CANCELADA`;
- `FALHOU`;
- `ESTORNADA`.

Somente `PAGA` concede créditos. Estados pendentes, cancelados ou falhos não criam transação `COMPRA`.

## Estorno

Quando uma compra paga for estornada, o sistema criará uma transação `ESTORNO` para remover os créditos ainda disponíveis daquela compra.

Créditos já consumidos por avaliações concluídas não serão removidos retroativamente. A política de estorno deverá impedir saldo negativo e registrar qualquer diferença para tratamento administrativo.

## Consequências

- separa pagamento confirmado de crédito concedido;
- evita conceder créditos por callback não confirmado;
- mantém histórico de estorno;
- exige rastrear a origem dos créditos para saber quanto permanece disponível;
- exige tratar estorno parcial ou insuficiente em decisão de pagamentos.
