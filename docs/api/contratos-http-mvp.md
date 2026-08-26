# Contratos HTTP do MVP

## Convenções

- Base URL: `/api/v1`.
- Autenticação: `Authorization: Bearer <access_token>`.
- O usuário estudante é obtido do `sub` do token validado; não é aceito `userId` para representar o proprietário.
- Respostas de sucesso usam `{ data, meta, traceId }`.
- Erros usam `{ error: { code, message, details }, traceId }`.
- `401` indica token ausente ou inválido; `403` indica falta de permissão.
- Coleções aceitam `fields`, `expand`, filtros permitidos, `orderBy`, `order`, `first` e `after`.
- Operações sensíveis aceitam `Idempotency-Key`.

## Autenticação e perfil

| Método | Rota | Regra |
|---|---|---|
| POST | `/auth/register` | público; recebe `name`, `email`, `password` e `passwordConfirmation`; normaliza o email e persiste somente o hash da senha |
| POST | `/auth/login` | público; recebe `email`, `password`; retorna access token e define refresh token protegido |
| POST | `/auth/refresh` | usa refresh token em cookie `HttpOnly`, `Secure`, `SameSite` |
| POST | `/auth/logout` | revoga refresh token e limpa cookie |
| GET | `/profile` | usuário autenticado |
| PATCH | `/profile` | usuário autenticado; altera dados básicos permitidos |

Falhas de login usam mensagem genérica e limitação de tentativas. Senhas nunca são retornadas ou armazenadas em texto puro.

## Avaliações

| Método | Rota | Regra |
|---|---|---|
| POST | `/evaluations` | texto confirmado; recebe `theme`, `origin=DIGITADA`, `text`; consome 1 crédito |
| GET | `/evaluations` | histórico do usuário autenticado; suporta cursor, filtros e ordenação |
| GET | `/evaluations/{evaluationId}` | proprietário ou administrador autorizado |
| POST | `/evaluation-inputs/images` | upload temporário `multipart/form-data`; até 5 imagens JPG/PNG, 8 MB cada e 32 MB por requisição |
| GET | `/evaluation-inputs/{inputId}` | consulta transcrição temporária e estado do OCR |
| POST | `/evaluation-inputs/{inputId}/confirm` | confirma transcrição, cria avaliação, consome 1 crédito e inicia processamento |

Estados de processamento: `PENDENTE`, `PROCESSANDO`, `CONCLUIDA` e `FALHOU`. O resultado concluído inclui `finalScore`, notas C1–C5 e feedbacks.

## Créditos e pagamentos

| Método | Rota | Regra |
|---|---|---|
| GET | `/credit-balance` | saldo derivado do ledger do usuário autenticado |
| GET | `/credit-offers` | ofertas ativas |
| POST | `/purchases` | recebe exatamente `creditQuantity` ou `offerId`; retorna compra pendente e link da AbacatePay |
| GET | `/purchases/{purchaseId}` | proprietário ou administrador autorizado |
| POST | `/webhooks/abacatepay` | endpoint externo; autenticação própria, validação de evento e idempotência |
| POST | `/admin/credit-adjustments` | somente administrador; exige quantidade, tipo, motivo e idempotência |

Somente confirmação aprovada pela AbacatePay credita créditos. Estornos e ajustes geram lançamentos no ledger. Ofertas e preços são administrados no backend, com desativação em vez de exclusão física.

## Segurança e privacidade

Não registrar redação, transcrição ou feedback completo em logs. Não retornar stack trace, segredos ou detalhes internos. Toda resposta possui `traceId`, e toda autorização administrativa é aplicada pelo Spring Security antes do controller.

Exemplo de paginação:

```http
GET /api/v1/evaluations?fields=id,status,finalScore&first=20&orderBy=createdAt&order=desc
```

O `meta.endCursor` pode ser enviado em `after` para buscar a próxima página.
