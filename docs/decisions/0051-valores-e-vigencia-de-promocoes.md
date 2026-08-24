# ADR 0051 — Valores monetários e vigência de promoções

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

Valores financeiros serão representados por `BigDecimal` na aplicação Java e `NUMERIC(12,2)` no PostgreSQL, com moeda explícita `BRL`.

As configurações promocionais ficarão na AbacatePay e poderão ter:

- `nome`;
- `creditos_inclusos`;
- `bonus_creditos`;
- `preco`;
- `ativo`;
- `vigencia_inicio`;
- `vigencia_fim`;
- `limite_de_uso` opcional.

Ao confirmar uma compra, o backend salvará em `CompraCredito` um snapshot da quantidade, preço, bônus e referência da oferta aplicados. A compra não será recalculada se a configuração na AbacatePay mudar depois.

## Consequências

- evita erros de ponto flutuante em cobrança;
- permite campanhas temporárias e limitadas;
- preserva o histórico financeiro;
- exige validação de vigência e limite no backend;
- exige definir a política de fuso horário para início e fim das promoções.
