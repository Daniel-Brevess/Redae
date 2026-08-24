# ADR 0073 — Autorização e snapshot financeiro

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

Somente usuários com `tipo_usuario = ADMIN` poderão criar, alterar, ativar ou desativar `OfertaCredito` e `PrecoCredito`.

No momento da criação de uma `CompraCredito`, o backend salvará um snapshot imutável de:

- `quantidade_creditos`;
- `bonus_creditos`;
- `creditos_totais`;
- `valor`;
- `moeda`;
- `preco_credito_id`;
- `oferta_credito_id`, quando aplicável;
- condições relevantes da oferta.

Alterações futuras de ofertas ou preços não modificarão compras existentes.

## Consequências

- restringe mudanças comerciais a administradores;
- preserva o contexto financeiro de cada compra;
- permite auditar créditos concedidos sem recalcular o passado;
- exige aplicar autorização no backend, não apenas esconder ações no frontend.
