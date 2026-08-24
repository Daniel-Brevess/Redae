# ADR 0025 — Tema como contexto da redação

- **Status:** superseded by ADR 0045
- **Data:** 2026-08-24

## Decisão

O MVP não terá uma entidade `Tema`, uma entidade `Exercicio` nem questionários separados. O tema será um campo textual da própria redação. O fluxo será baseado no texto enviado pelo estudante e no contexto textual informado:

```text
Redacao(tema) → Avaliacao → FeedbackItem
```

`Redacao` terá um campo `tema` obrigatório em texto, preenchido livremente pelo estudante. Esse valor será enviado ao serviço de IA junto com o texto confirmado para orientar a avaliação, especialmente em C2 e C5.

Como o tema será copiado dentro da redação, o histórico preservará o contexto usado no momento da submissão.

## Fora do MVP

- catálogo ou busca de temas;
- exercícios independentes;
- questionários de múltipla escolha;
- plano de estudo completo;
- sequência obrigatória de atividades.

## Consequências

- reduz o modelo de domínio e o número de fluxos;
- deixa a prática centrada na redação e no feedback;
- preserva o contexto do tema diretamente na redação;
- permite adicionar catálogo de temas no futuro sem alterar o fluxo básico de redação.
