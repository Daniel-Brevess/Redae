# Fluxo de pagamento de créditos

## Objetivo

Permitir que o usuário compre um crédito individual ou um pacote de créditos
por PIX e receba os créditos após a confirmação do pagamento pela Stripe.

## Fluxo principal

```text
Usuário informa a quantidade de créditos
        ↓
Frontend solicita a criação da cobrança
        ↓
Backend cria a transação local como PENDENTE
        ↓
Backend cria uma Checkout Session na Stripe
        ↓
Backend salva o identificador externo e retorna a URL do Checkout
        ↓
Frontend redireciona o usuário para o Checkout hospedado
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

- exibir o campo para quantidade de créditos;
- solicitar a criação da cobrança;
- redirecionar o usuário para a URL do Checkout;
- informar quando os créditos forem confirmados.

O frontend não confirma pagamentos por conta própria e não recebe credenciais do
gateway.

### Backend

- validar a quantidade escolhida;
- aplicar o preço fixo de R$ 3,00 por crédito;
- criar e persistir a transação;
- criar a Checkout Session na Stripe;
- armazenar o identificador externo da sessão;
- receber e validar o webhook;
- atualizar o status da transação;
- adicionar os créditos somente após confirmação válida;
- impedir o processamento duplicado do mesmo webhook.

### StripeClient

O cliente da Stripe ficará atrás da interface `PaymentGatewayProvider` e
será responsável por:

- criar uma Checkout Session usando o SDK Java oficial;
- retornar a URL para o Checkout hospedado;
- converter os eventos do Checkout para o estado interno da transação.

O controller do webhook receberá a requisição HTTP, validará a assinatura e
delegará a confirmação ao service. As regras de negócio não ficarão no controller.

## SDK Java e configuração da Stripe

Para a implementação Java, será utilizada a biblioteca oficial da Stripe
como dependência Maven. A documentação oficial apresenta esta referência:

```xml
<dependency>
  <groupId>com.stripe</groupId>
  <artifactId>stripe-java</artifactId>
  <version>33.4.1</version>
</dependency>
```

Durante a implementação, a versão será confirmada na documentação oficial e
fixada no `pom.xml`. O `StripeClient` deverá configurar o SDK com a chave
de acesso por variável de ambiente. O token nunca será salvo no banco, enviado
ao frontend ou versionado.

Também será necessário configurar no painel da Stripe as notificações HTTPS
para o endpoint de webhook da aplicação. As URLs de sucesso e cancelamento são
configuradas por variáveis de ambiente e não contêm segredos.

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
- quantidade e valor serão validados no backend;
- nenhum segredo ou dado sensível será enviado ao frontend ou versionado.

## Estado atual

O fluxo de Checkout está implementado no módulo `gateway`. A aplicação cria a
transação local como `PENDENTE`, cria a sessão na Stripe e só concede créditos
depois de um webhook válido. Os eventos `checkout.session.completed`,
`checkout.session.async_payment_succeeded` e `checkout.session.expired` são
tratados pelo endpoint `/api/v1/webhooks/stripe`.

As variáveis necessárias para usar a Stripe são:

```text
PAYMENT_PROVIDER=stripe
STRIPE_SECRET_KEY=sk_test_...
STRIPE_WEBHOOK_SECRET=whsec_...
STRIPE_SUCCESS_URL=http://localhost:5173/home
STRIPE_CANCEL_URL=http://localhost:5173/home
```

O frontend recebe somente a `checkoutUrl` retornada pelo backend. A confirmação
da compra não depende da página de sucesso e o provider fake continua disponível
para testes locais.

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
