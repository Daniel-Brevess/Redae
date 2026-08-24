# ADR 0007 — Formato padrão de erros da API

- **Status:** aceito
- **Data:** 2026-08-24

## Contexto

Frontend e backend precisam tratar erros de forma previsível. Mensagens diretamente acopladas a exceções internas dificultam a experiência do estudante e podem revelar informações sensíveis da implementação.

## Decisão

As respostas de erro da API seguirão um envelope comum:

```json
{
  "code": "ESSAY_NOT_FOUND",
  "message": "Não foi possível encontrar essa redação.",
  "details": [],
  "traceId": "8f8c1b7e..."
}
```

Regras:

- `code` será estável e destinado ao tratamento programático do frontend;
- `message` será segura e adequada ao usuário;
- `details` será opcional e usado principalmente para erros de validação;
- `traceId` permitirá correlacionar a resposta com os logs internos;
- stack traces, SQL, nomes de tabelas, tokens, credenciais e detalhes de infraestrutura nunca serão enviados ao cliente;
- os códigos HTTP continuarão indicando a categoria geral do erro.

## Consequências

- o frontend poderá exibir mensagens consistentes e tratar códigos específicos;
- suporte e desenvolvimento poderão localizar uma ocorrência por `traceId`;
- exceções internas precisarão ser mapeadas para erros públicos;
- será necessário manter um catálogo de códigos de erro durante a implementação.
