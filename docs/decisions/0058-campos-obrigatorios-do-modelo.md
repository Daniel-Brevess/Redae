# ADR 0058 — Campos obrigatórios do modelo

- **Status:** aceito
- **Data:** 2026-08-24

## Usuario

São obrigatórios:

- `nome`;
- `email` normalizado;
- `senha_hash`;
- `tipo_usuario`.

Estudantes e administradores usam a mesma estrutura de conta. A diferença de acesso vem de `tipo_usuario`.

## Avaliacao

São obrigatórios na criação:

- `usuario_id`;
- `texto_confirmado`;
- `tema`;
- `origem`;
- `status`.

Como a avaliação só é criada depois da confirmação do texto, não haverá `Avaliacao` persistida com texto ou tema ausente.

`nota_final` e `gerada_em` permanecem opcionais até a avaliação ser concluída.
