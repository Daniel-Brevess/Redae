# ADR 0026 — Tema livre na redação

- **Status:** superseded by ADR 0045
- **Data:** 2026-08-24

## Decisão

O campo `Redacao.tema` será obrigatório e preenchido livremente pelo estudante. O MVP não terá busca, catálogo, seleção prévia ou gerenciamento administrativo de temas.

O tema será persistido junto da redação confirmada e enviado à IA como contexto da avaliação. Não haverá entidade ou FK de tema.

## Consequências

- permite avaliar qualquer proposta informada pelo estudante;
- reduz telas, tabelas e manutenção de conteúdo;
- exige validação de tamanho e conteúdo mínimo do campo;
- a qualidade da avaliação de C2 e C5 dependerá da clareza do tema informado;
- um catálogo de temas poderá ser adicionado futuramente sem alterar a avaliação básica.
