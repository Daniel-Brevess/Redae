# API

## Convenções

- Base URL: `/api/v1`.
- Autenticação: Bearer JWT, exceto rotas públicas e webhook.
- O usuário é obtido do `sub` do token; não se aceita `userId` para representar o proprietário.
- Sucesso: `{ data, meta, traceId }`.
- Erro: `{ error: { code, message, details }, traceId }`.
- `401` indica autenticação ausente ou inválida; `403`, falta de permissão.
- Operações sensíveis usam `Idempotency-Key`.
- O contrato OpenAPI está em [`api.openapi.yaml`](api.openapi.yaml).

## Autenticação e perfil

| Método | Rota | Regra |
|---|---|---|
| POST | `/auth/register` | cadastro público; recebe nome, email e senhas |
| POST | `/auth/login` | login público; retorna acesso e define refresh protegido |
| POST | `/auth/refresh` | renova pelo cookie HttpOnly |
| POST | `/auth/logout` | revoga a sessão e limpa o cookie |
| GET | `/profile` | consulta o usuário autenticado |
| PATCH | `/profile` | altera dados básicos permitidos |

Senhas nunca são retornadas ou armazenadas em texto puro. Falhas de login usam mensagem genérica.

## Avaliações

| Método | Rota | Regra |
|---|---|---|
| POST | `/evaluations` | cria avaliação de texto confirmado |
| GET | `/evaluations` | lista avaliações do usuário com paginação |
| GET | `/evaluations/{evaluationId}` | consulta avaliação do proprietário ou administrador |
| POST | `/evaluation-inputs/images` | upload temporário para transcrição |
| GET | `/evaluation-inputs/{inputId}` | consulta estado da transcrição |
| POST | `/evaluation-inputs/{inputId}/confirm` | confirma texto e cria avaliação |

Estados: `PENDENTE`, `PROCESSANDO`, `CONCLUIDA` e `FALHOU`. O resultado concluído contém nota final, C1–C5 e feedback conforme o tipo da avaliação.

## Créditos e compras

| Método | Rota | Regra |
|---|---|---|
| GET | `/credit-balance` | saldo derivado do ledger |
| GET | `/credit-offers` | ofertas ativas |
| POST | `/purchases` | inicia compra livre ou por oferta |
| GET | `/purchases/{purchaseId}` | consulta compra autorizada |
| POST | `/webhooks/abacatepay` | confirma pagamento de forma autenticada e idempotente |
| POST | `/admin/credit-adjustments` | ajuste exclusivo de administrador |

Somente pagamento aprovado credita o usuário. Estornos e ajustes geram transações auditáveis.

## Upload e limites

O upload aceita imagens JPG/PNG, até cinco arquivos, 8 MB por arquivo e 32 MB por requisição. A transcrição é temporária e deve ser revisada antes da confirmação.

## Paginação

```http
GET /api/v1/evaluations?fields=id,status,finalScore&first=20&orderBy=createdAt&order=desc
```

O cursor retornado em `meta.endCursor` pode ser enviado no parâmetro `after`.
