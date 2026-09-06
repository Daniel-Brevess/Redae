# Fluxo de pagamento de créditos

## Objetivo

Permitir que o usuário compre um crédito individual ou um pacote de créditos
por PIX e receba os créditos após a confirmação do pagamento pela Stripe.

## Fluxo principal

```text
Usuário escolhe um pacote
        ↓
Frontend solicita a criação da cobrança
        ↓
Backend cria a transação local como PENDENTE
        ↓
Backend solicita a cobrança PIX à Stripe
        ↓
Backend salva o identificador externo da cobrança
        ↓
Backend retorna QR Code e código copia e cola
        ↓
Frontend exibe os dados do PIX
        ↓
Usuário realiza o pagamento
        ↓
Stripe envia um webhook
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
- solicitar a cobrança à Stripe;
- armazenar o identificador externo;
- receber e validar o webhook;
- atualizar o status da transação;
- adicionar os créditos somente após confirmação válida;
- impedir o processamento duplicado do mesmo webhook.

### StripeClient

O cliente da Stripe ficará atrás da interface `PaymentGatewayProvider` e
será responsável por:

- criar pagamentos PIX usando o SDK Java oficial;
- retornar os dados necessários para o checkout;
- validar a autenticidade dos webhooks;
- consultar o pagamento na Stripe a partir do identificador recebido;
- converter os status do provedor para o estado interno da transação.

O controller do webhook receberá a requisição HTTP e delegará o processamento ao
cliente e ao service. As regras de negócio não ficarão no controller.

## SDK Java e configuração da Stripe

Para a implementação Java, será utilizada a biblioteca oficial da Stripe
como dependência Maven. A documentação oficial apresenta esta referência:

```xml
<dependency>
  <groupId>com.stripe</groupId>
  <artifactId>sdk-java</artifactId>
  <version>2.1.7</version>
</dependency>
```

Durante a implementação, a versão será confirmada na documentação oficial e
fixada no `pom.xml`. O `StripeClient` deverá configurar o SDK com a chave
de acesso por variável de ambiente. O token nunca será salvo no banco, enviado
ao frontend ou versionado.

Também será necessário configurar no painel da Stripe as notificações
HTTPS para o endpoint de webhook da aplicação. O Pix exige que as chaves Pix
estejam cadastradas na conta da Stripe.

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

- a chave secreta da Stripe ficará somente no backend;
- webhooks deverão ser validados antes de alterar a transação;
- o retorno do usuário ao frontend não será considerado confirmação de pagamento;
- transações deverão ser associadas ao usuário autenticado;
- valores e pacotes serão validados no backend;
- nenhum segredo ou dado sensível será enviado ao frontend ou versionado.

## Estado atual

O módulo `gateway` possui somente a estrutura inicial de pacotes. A entidade,
o repository, o service, o `StripeClient`, os endpoints e as migrations
ainda serão implementados por partes.

O fluxo segue a mesma separação planejada anteriormente: a aplicação cria a
transação local, o provedor cria o pagamento, o usuário paga via Pix e o
webhook dispara a confirmação. A diferença fica na integração específica com
a Stripe, nos status retornados e na validação da notificação.

Para desenvolvimento local, `PAYMENT_PROVIDER=fake` seleciona o
`FakePaymentGatewayClient`. Ele gera uma referência fictícia e aprova a
transação imediatamente, sem checkout ou chamada externa. Esse modo serve
apenas para validar o fluxo da API e não substitui o webhook nem a integração
real. O ledger de créditos ainda será implementado antes da concessão efetiva
de saldo ao usuário.

Referências oficiais:

- [SDK Java oficial da Stripe](https://github.com/stripe/stripe-java);
- [Integração de pagamentos Pix](https://docs.stripe.com/payments/pix);
- [Validação de assinatura de Webhooks](https://docs.stripe.com/webhooks/signature).
