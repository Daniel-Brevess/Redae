# ADR 0075 — API HTTP orientada a grafo

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

O Redaê usará uma API HTTP versionada e orientada a grafo, inspirada na Graph API da Meta. A API será composta por nós, edges e fields, sem adotar a linguagem ou o endpoint do GraphQL.

Os recursos serão expostos sob `/api/v1`. As relações principais serão representadas por edges, como `User.evaluations`, `Evaluation.competencyScores`, `Evaluation.feedbackItems`, `User.creditTransactions` e `User.purchases`. A seleção de fields, filtros, paginação e expansão de edges será feita por parâmetros HTTP documentados.

Para operações do estudante sobre os próprios dados, o backend extrairá o identificador do usuário autenticado a partir do `subject` (`sub`) do token validado. As rotas serão orientadas ao recurso, sem inserir `/me` ou `userId` para representar o proprietário; por exemplo, o histórico será consultado em `/api/v1/evaluations`. O controller receberá o usuário autenticado do contexto do Spring Security e encaminhará essa identidade ao service, que fará a consulta por meio do repository.

O token considerado para as requisições protegidas será o access token atual enviado no cabeçalho `Authorization: Bearer`. O backend validará o token antes de criar o contexto autenticado e só então buscará o usuário correspondente.

Rotas com identificadores explícitos de usuários ficarão restritas a operações administrativas e sempre dependerão de autorização no backend; um identificador fornecido pelo cliente nunca será considerado prova de identidade ou autorização.

O webhook da AbacatePay continuará sendo um endpoint HTTP separado, por ser uma integração externa.

As respostas aceitarão os parâmetros `fields` para seleção explícita de propriedades e `expand` para carregar edges relacionados. A resposta padrão será mínima, sem expansão automática de relações.

Respostas de sucesso usarão o envelope `{ data, meta, traceId }`. Em coleções, `data` conterá os itens e `meta` conterá informações de paginação ou outros metadados da consulta.

Coleções usarão paginação por cursor. `first` definirá a quantidade solicitada e `after` indicará o cursor a partir do qual continuar. O backend aplicará um limite máximo por requisição, e `meta` retornará `hasNextPage` e `endCursor` quando aplicável.

Filtros e ordenação serão enviados por parâmetros explícitos, como `status`, `origin`, `orderBy` e `order`. Cada recurso terá uma lista permitida de filtros, campos de ordenação e direções; parâmetros desconhecidos ou não permitidos serão rejeitados.

Erros usarão um envelope com `error.code`, `error.message`, `error.details` e `traceId`. As respostas não incluirão stack traces, segredos ou detalhes internos da infraestrutura.

Operações sensíveis aceitarão o header `Idempotency-Key`. A chave será associada ao usuário autenticado, à operação e ao resultado durante uma janela de retenção definida, permitindo devolver o mesmo resultado em retries sem repetir efeitos financeiros, consumo de créditos ou criação de avaliações.

A autenticação usará access token de curta duração e refresh token para renovação da sessão. O logout invalidará o refresh token no backend; o access token já emitido expirará naturalmente dentro de sua curta validade.

Cadastro e login usarão, respectivamente, `POST /api/v1/auth/register` e `POST /api/v1/auth/login`. O cadastro receberá nome, email e senha; o email será normalizado em lowercase e a senha será persistida somente como hash. Falhas de login usarão mensagem genérica e terão proteção contra tentativas excessivas, sem revelar se o email está cadastrado.

O refresh token será mantido em cookie com `HttpOnly`, `Secure` e política `SameSite`. A renovação ocorrerá em `POST /api/v1/auth/refresh`. Os dados do usuário autenticado serão consultados e atualizados em `/api/v1/profile`, sem `userId` na rota.

O logout ocorrerá em `POST /api/v1/auth/logout`, revogando o refresh token no backend e limpando o cookie. A autorização administrativa será aplicada pelo Spring Security antes da execução do controller, com base em `tipo_usuario = ADMIN`; não haverá endpoint público para validar permissões.

Redações digitadas serão confirmadas pelo frontend e então enviadas a `POST /api/v1/evaluations` com tema, origem `DIGITADA` e texto. Imagens serão enviadas separadamente a `POST /api/v1/evaluation-inputs/images` usando `multipart/form-data`; a transcrição permanecerá temporária até a confirmação do estudante.

O processamento temporário da imagem será consultado por `GET /api/v1/evaluation-inputs/{inputId}`. A confirmação ocorrerá em `POST /api/v1/evaluation-inputs/{inputId}/confirm`; nesse momento o backend criará a avaliação persistida, consumirá um crédito e iniciará o processamento da avaliação.

O processamento da avaliação será acompanhado por `GET /api/v1/evaluations/{evaluationId}`, com estados `PENDENTE`, `PROCESSANDO`, `CONCLUIDA` e `FALHOU`. O endpoint só retornará a avaliação quando o usuário autenticado for seu proprietário ou tiver autorização administrativa. Uma avaliação concluída incluirá `finalScore`, notas das competências `C1` a `C5` e os feedbacks correspondentes.

O saldo derivado será consultado em `GET /api/v1/credit-balance`. Ofertas ativas serão listadas em `GET /api/v1/credit-offers`. Compras serão iniciadas em `POST /api/v1/purchases`, aceitando compra livre ou oferta e permanecendo pendentes até a confirmação do pagamento. O webhook da AbacatePay será separado, protegido por autenticação própria da integração e processado de forma idempotente.

Uma compra receberá exatamente `creditQuantity` ou `offerId`; o backend calculará o valor usando a configuração vigente e retornará `purchaseId`, status pendente e o link de pagamento. Apenas a confirmação aprovada pelo webhook creditará os créditos. A compra poderá ser consultada pelo proprietário ou por administrador. Estornos e ajustes administrativos gerarão lançamentos no ledger, com motivo e rastreabilidade, sem alterar artificialmente o saldo derivado.

A primeira concessão gratuita será feita automaticamente uma única vez após o cadastro, registrada como `CONCESSAO` no ledger e incluída no saldo derivado. Ajustes administrativos exigirão rota protegida, motivo, quantidade, tipo e idempotência.

Requisições sem access token ou com token inválido retornarão `401 Unauthorized`. Usuários autenticados sem permissão para a operação retornarão `403 Forbidden`.

## Consequências

- frontend e backend compartilharão contratos HTTP explícitos e compatíveis;
- OpenAPI continuará sendo adequada para documentar os contratos;
- a organização por nós e edges facilita navegar pelas relações do domínio;
- o backend precisará limitar fields, expansão, filtros e paginação para evitar respostas excessivas;
- a evolução incompatível exigirá nova versão no prefixo da API.
- rotas de autoatendimento usam URLs limpas orientadas ao recurso, sem `/me` ou `userId` controlado pelo cliente;
- o identificador usado nas consultas do estudante vem do token validado, e não da requisição;
- o usuário atual é resolvido pelo access token atual, após a validação do token;
- operações administrativas exigem autorização explícita para o usuário consultado.
