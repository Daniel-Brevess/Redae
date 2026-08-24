# ADR 0010 — Uma avaliação por redação no MVP

- **Status:** aceito
- **Data:** 2026-08-24

## Contexto

Cada avaliação por IA gera custo e pode produzir pequenas variações de resultado. Permitir reavaliações livres aumentaria o custo, dificultaria explicar qual resultado é válido e poderia gerar uso abusivo da integração.

## Decisão

No MVP, cada redação enviada terá uma única avaliação válida. Novas chamadas à IA serão permitidas somente para:

- retentativa automática ou controlada após falha técnica;
- recuperação de uma operação interrompida antes de gerar resultado válido.

O estudante não poderá solicitar reavaliações livres da mesma redação. A nota e o feedback serão armazenados como uma versão identificável da avaliação e não serão substituídos silenciosamente.

## Consequências

- custo máximo mais previsível por redação;
- resultados mais consistentes e fáceis de explicar;
- necessidade de diferenciar falha técnica de avaliação concluída;
- uma eventual reavaliação pedagógica deverá ser decidida em uma fase futura, com regras próprias.
