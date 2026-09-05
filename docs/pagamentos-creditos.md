# Fluxo de pagamento de créditos

## Objetivo

Permitir que o usuário compre um crédito individual ou um pacote de créditos
por PIX e receba os créditos após a confirmação do pagamento pela AbacatePay.

## Fluxo principal

```text
Usuário escolhe um pacote
        ↓
Frontend solicita a criação da cobrança
        ↓
Backend cria a transação local como PENDENTE
        ↓
Backend solicita a cobrança PIX à AbacatePay
        ↓
Backend salva o identificador externo da cobrança
        ↓
Backend retorna QR Code e código copia e cola
        ↓
Frontend exibe os dados do PIX
        ↓
Usuário realiza o pagamento
        ↓
AbacatePay envia um webhook
        ↓
Backend valida e normaliza o webhook
        ↓
Backend confirma a transação e adiciona os créditos
        ↓
Transação fica CONCLUIDA
```

## Responsabilidades

### Frontend

- exibir os pacotes disponíveis;
- solicitar a criação da cobrança;
- mostrar o QR Code e o código PIX;
- consultar o status da transação;
- informar quando os créditos forem confirmados.

O frontend não confirma pagamentos por conta própria e não recebe credenciais do
gateway.

### Backend

- validar o pacote escolhido;
- criar e persistir a transação;
- solicitar a cobrança à AbacatePay;
- armazenar o identificador externo;
- receber e validar o webhook;
- atualizar o status da transação;
- adicionar os créditos somente após confirmação válida;
- impedir o processamento duplicado do mesmo webhook.

### AbacatePayClient

O cliente da AbacatePay ficará atrás da interface `PaymentGatewayProvider` e
será responsável por:

- criar cobranças PIX;
- retornar os dados necessários para o checkout;
- validar a autenticidade dos webhooks;
- converter eventos da AbacatePay para um formato interno.

O controller do webhook receberá a requisição HTTP e delegará o processamento ao
cliente e ao service. As regras de negócio não ficarão no controller.

## Estados da transação

- `PENDENTE`: cobrança criada e aguardando pagamento;
- `CONCLUIDA`: pagamento confirmado e créditos adicionados;
- `CANCELADA`: cobrança cancelada;
- `EXPIRADA`: cobrança vencida sem confirmação de pagamento.

## Persistência

A entidade `PaymentTransaction` deverá guardar, no mínimo:

- usuário;
- pacote e quantidade de créditos;
- valor;
- status;
- provedor de pagamento;
- identificador externo da cobrança;
- código PIX ou referência necessária para consulta;
- datas de criação, atualização e pagamento.

O identificador externo deverá ser usado para garantir idempotência. Um mesmo
webhook recebido mais de uma vez não pode gerar créditos novamente.

## Segurança

- a chave da AbacatePay ficará somente no backend;
- webhooks deverão ser validados antes de alterar a transação;
- o retorno do usuário ao frontend não será considerado confirmação de pagamento;
- transações deverão ser associadas ao usuário autenticado;
- valores e pacotes serão validados no backend;
- nenhum segredo ou dado sensível será enviado ao frontend ou versionado.

## Estado atual

O módulo `gateway` possui somente a estrutura inicial de pacotes. A entidade,
o repository, o service, o cliente da AbacatePay, os endpoints e as migrations
ainda serão implementados por partes.
