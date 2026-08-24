# ADR 0038 — Integridade e índices iniciais

- **Status:** aceito
- **Data:** 2026-08-24

## Restrições

- `Usuario.email` será único;
- `Avaliacao.redacao_id` será único;
- `(NotaCompetencia.avaliacao_id, NotaCompetencia.competencia_codigo)` será único;
- `Redacao.usuario_id` será obrigatório;
- `Redacao.tema` e `Redacao.texto_confirmado` serão obrigatórios após a confirmação;
- `NotaCompetencia.nivel` ficará entre 0 e 5;
- FKs essenciais serão obrigatórias quando o ciclo de vida exigir proprietário ou pai;
- estados e códigos serão restringidos por enum ou constraint equivalente.

## Índices iniciais

- `Redacao.usuario_id`;
- `Redacao.created_at`;
- `Avaliacao.status`;
- `Processamento.status`;
- `Processamento.expira_em`;
- combinações de proprietário e data para consultas de histórico, se o plano de execução justificar.

Os índices serão revisados após observar consultas reais; não haverá criação indiscriminada de índices.
