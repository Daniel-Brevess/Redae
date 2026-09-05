# Fluxo de pagamento de créditos

## Objetivo

Permitir que o usuário compre um crédito individual ou um pacote de créditos
por PIX e receba os créditos após a confirmação do pagamento pelo Mercado Pago.

## Fluxo principal

```text
Usuário escolhe um pacote
        ↓
Frontend solicita a criação da cobrança
        ↓
Backend cria a transação local como PENDENTE
        ↓
Backend solicita a cobrança PIX ao Mercado Pago
        ↓
Backend salva o identificador externo da cobrança
        ↓
Backend retorna QR Code e código copia e cola
        ↓
Frontend exibe os dados do PIX
        ↓
Usuário realiza o pagamento
        ↓
Mercado Pago envia um webhook
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
- solicitar a cobrança ao Mercado Pago;
- armazenar o identificador externo;
- receber e validar o webhook;
- atualizar o status da transação;
- adicionar os créditos somente após confirmação válida;
- impedir o processamento duplicado do mesmo webhook.

### MercadoPagoClient

O cliente do Mercado Pago ficará atrás da interface `PaymentGatewayProvider` e
será responsável por:

- criar pagamentos PIX usando o SDK Java oficial;
- retornar os dados necessários para o checkout;
- validar a autenticidade dos webhooks;
- consultar o pagamento no Mercado Pago a partir do identificador recebido;
- converter os status do provedor para o estado interno da transação.

O controller do webhook receberá a requisição HTTP e delegará o processamento ao
cliente e ao service. As regras de negócio não ficarão no controller.

## SDK Java e configuração do Mercado Pago

Para a implementação Java, será utilizada a biblioteca oficial do Mercado Pago
como dependência Maven. A documentação oficial apresenta esta referência:

```xml
<dependency>
  <groupId>com.mercadopago</groupId>
  <artifactId>sdk-java</artifactId>
  <version>2.1.7</version>
</dependency>
```

Durante a implementação, a versão será confirmada na documentação oficial e
fixada no `pom.xml`. O `MercadoPagoClient` deverá configurar o SDK com o token
de acesso por variável de ambiente. O token nunca será salvo no banco, enviado
ao frontend ou versionado.

Também será necessário configurar no painel do Mercado Pago as notificações
HTTPS para o endpoint de webhook da aplicação. O Pix exige que as chaves Pix
estejam cadastradas na conta do Mercado Pago.

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

- o token de acesso do Mercado Pago ficará somente no backend;
- webhooks deverão ser validados antes de alterar a transação;
- o retorno do usuário ao frontend não será considerado confirmação de pagamento;
- transações deverão ser associadas ao usuário autenticado;
- valores e pacotes serão validados no backend;
- nenhum segredo ou dado sensível será enviado ao frontend ou versionado.

## Estado atual

O módulo `gateway` possui somente a estrutura inicial de pacotes. A entidade,
o repository, o service, o `MercadoPagoClient`, os endpoints e as migrations
ainda serão implementados por partes.

O fluxo segue a mesma separação planejada anteriormente: a aplicação cria a
transação local, o provedor cria o pagamento, o usuário paga via Pix e o
webhook dispara a confirmação. A diferença fica na integração específica com
o Mercado Pago, nos status retornados e na validação da notificação.

Referências oficiais:

- [SDKs oficiais e dependência Maven do Mercado Pago](https://www.mercadopago.com.br/developers/pt/docs/checkout-pro-preferences/configure-development-enviroment);
- [Integração de pagamentos Pix](https://www.mercadopago.com.br/developers/pt/docs/checkout-api-orders/payment-integration/pix);
- [Configuração de notificações Webhooks](https://www.mercadopago.com.br/developers/pt/docs/checkout-api-orders/notifications).
