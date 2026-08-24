# ADR 0049 — Compra de créditos no MVP

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

O MVP incluirá compra de créditos por meio de um provedor externo de pagamentos. O modelo terá uma entidade `CompraCredito` para acompanhar o ciclo do pagamento.

Campos principais de `CompraCredito`:

- `id` UUID;
- `usuario_id` FK;
- `referencia_externa` única quando fornecida pelo provedor;
- `status`, como `CRIADA`, `PENDENTE`, `PAGA`, `CANCELADA`, `FALHOU` ou `ESTORNADA`;
- `quantidade_creditos`;
- `valor` e `moeda`;
- `paga_em` opcional;
- `created_at` e `updated_at`.

Somente uma confirmação válida de pagamento criará `TransacaoCredito` do tipo `COMPRA`. A operação será idempotente por compra e referência externa.

Se uma compra for estornada, o sistema criará uma transação `ESTORNO` para remover créditos ainda não utilizados. Créditos já consumidos não serão removidos retroativamente da avaliação concluída.

O provedor de pagamento será escolhido em decisão posterior. A integração ficará atrás de uma interface para não espalhar dependências pelo domínio.

## Consequências

- compra de créditos faz parte do escopo funcional do MVP;
- o saldo continua derivado das transações, não da compra diretamente;
- webhooks e callbacks precisam de validação de autenticidade e idempotência;
- pagamentos pendentes não concedem créditos;
- cancelamentos e estornos exigem transações de crédito correspondentes.
