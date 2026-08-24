# ADR 0032 — Remoção do diagnóstico do MVP

- **Status:** aceito
- **Data:** 2026-08-24

## Contexto

O diagnóstico seria uma avaliação inicial separada da avaliação de redações. Porém, o MVP atual não terá plano de estudo, exercícios, recomendações ou uma jornada específica de diagnóstico.

## Decisão

Não haverá entidade `Diagnostico` no MVP. O domínio terá somente `Avaliacao`, sempre associada a uma `Redacao`.

Se uma avaliação inicial for necessária no futuro, ela poderá ser identificada por um campo de tipo na própria `Avaliacao`, sem duplicar a estrutura de notas e feedback.

Uma primeira avaliação gratuita e resumida poderá ser implementada como regra de produto, quota ou benefício de entrada. Ela não exige uma entidade `Diagnostico` nem um campo `tipo_avaliacao` enquanto o comportamento for igual ao de uma avaliação comum.

## Consequências

- reduz o número de entidades e fluxos;
- evita salvar respostas e resumos sem uma funcionalidade consumidora;
- concentra a lógica de avaliação em um único modelo;
- deixa a avaliação inicial futura como extensão da avaliação de redação.
