# ADR 0045 — Avaliação como agregado principal e créditos por uso

- **Status:** aceito
- **Data:** 2026-08-24

## Contexto

O produto será centrado na avaliação: o estudante fornece uma redação, o sistema processa o texto e entrega nota e feedback. O valor persistente para o estudante é o resultado da avaliação, não um cadastro separado de redações.

## Decisão

Não haverá uma entidade persistente `Redacao` no MVP. A redação será um objeto temporário de entrada durante a confirmação e o processamento.

`Avaliacao` será o registro principal e armazenará:

- `usuario_id`;
- `texto_confirmado`;
- `tema` obrigatório informado pelo estudante;
- `origem` (`DIGITADA` ou `IMAGEM`);
- status, nota, versão, modelo de IA e timestamps;
- notas por competência e feedbacks.

A relação principal será:

```text
Usuario 1 ─── N Avaliacao
Avaliacao 1 ─── N NotaCompetencia
NotaCompetencia 1 ─── N FeedbackItem
```

O histórico será uma consulta das avaliações do usuário. O progresso será calculado a partir delas.

## Créditos

O usuário consumirá créditos para iniciar avaliações. A regra de negócio inicial será:

```text
1 crédito = 1 avaliação
```

O modelo terá registros de crédito para representar compras, concessões, consumo, estorno e ajustes. O saldo não deverá depender somente de um número solto sem histórico.

O momento exato do consumo será definido na regra de cobrança: a recomendação é reservar/consumir o crédito quando o envio for confirmado e devolvê-lo somente em falha técnica definitiva sem avaliação válida.

## Consequências

- reduz uma entidade e várias FKs do modelo;
- mantém no histórico exatamente o que o estudante consultará;
- simplifica a associação de nota e feedback ao usuário;
- exige armazenar o texto confirmado dentro da avaliação;
- introduz um subdomínio de créditos e transações;
- substitui decisões anteriores que tratavam `Redacao` como entidade persistente.
