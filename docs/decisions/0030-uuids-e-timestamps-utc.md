# ADR 0030 — UUIDs e timestamps em UTC

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

As entidades principais do modelo usarão UUID como chave primária. FKs para essas entidades também usarão UUID e manterão o mesmo tipo.

Entidades persistidas relevantes terão:

- `created_at`: data e hora de criação, imutável;
- `updated_at`: data e hora da última alteração relevante.

Os timestamps serão armazenados em UTC e convertidos para o fuso do usuário apenas na apresentação.

## Consequências

- reduz exposição de sequência de registros em URLs e APIs;
- facilita geração de identificadores em diferentes processos;
- evita ambiguidade entre ambientes e fusos horários;
- exige padronizar geração e serialização de UUIDs;
- tabelas puramente técnicas ou de associação poderão ser avaliadas caso a caso.
