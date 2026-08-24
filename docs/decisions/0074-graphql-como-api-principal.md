# ADR 0074 — GraphQL como API principal

- **Status:** superseded by ADR 0075
- **Data:** 2026-08-24

## Decisão

O frontend e o backend se comunicarão principalmente por GraphQL, usando o endpoint `/graphql`.

O schema será organizado por queries, mutations, tipos, inputs e enums. A evolução será compatível e orientada pelo schema, sem criar versões de URL como `/api/v1` para cada mudança.

Endpoints HTTP tradicionais ficarão restritos a integrações externas que exigirem esse formato. O webhook da AbacatePay será documentado separadamente e não passará pelo fluxo GraphQL do frontend.

## Consequências

- frontend pode consultar exatamente os campos necessários;
- contratos de avaliação, créditos e compras ficam centralizados no schema;
- exige governança de evolução e remoção de campos;
- exige limites de profundidade, complexidade e tamanho de query;
- erros GraphQL e erros HTTP de webhook terão tratamentos documentados separadamente;
- OpenAPI não será a especificação principal da aplicação, mas poderá documentar o webhook.
