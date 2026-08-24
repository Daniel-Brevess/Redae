# ADR 0027 — Histórico e progresso derivados

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

Não haverá entidades separadas `Historico` ou `RegistroProgresso` no MVP.

O histórico será obtido por consultas às redações, avaliações e datas de submissão pertencentes ao estudante. O progresso será calculado a partir desses registros, incluindo possíveis médias, evolução por competência e quantidade de redações avaliadas.

Não haverá duplicação de nota ou feedback em uma tabela de progresso. Caso o cálculo fique pesado no futuro, projeções ou cache poderão ser adicionados com estratégia própria.

## Consequências

- reduz duplicidade e risco de divergência entre histórico e avaliação;
- mantém a avaliação como fonte oficial dos resultados;
- simplifica exclusão de conta e privacidade;
- consultas de progresso poderão exigir índices adequados;
- métricas pré-calculadas ficam fora do MVP.
