# ADR 0031 — Campos de usuário e redação

- **Status:** superseded by ADR 0045
- **Data:** 2026-08-24

## Usuario

`Usuario` terá os campos principais:

- `id` UUID;
- `nome`;
- `email` único;
- `senha_hash`;
- `tipo_usuario`, com valores `STUDENT` ou `ADMIN`;
- `created_at`;
- `updated_at`.

O campo `email_verificado` fica fora do MVP atual. Confirmação de email poderá ser adicionada posteriormente.

## Redacao

`Redacao` terá os campos principais:

- `id` UUID;
- `usuario_id` FK para `Usuario`;
- `texto_confirmado`;
- `tema` obrigatório em texto livre;
- `origem`, com valores `DIGITADA` ou `IMAGEM`;
- `status` do ciclo de submissão;
- `created_at`;
- `updated_at`.

A redação persistida representa o texto confirmado. Rascunhos não serão armazenados no MVP. A avaliação terá sua própria FK para `Redacao`.

## Consequências

- `tipo_usuario` comunica diretamente a finalidade da coluna;
- evita adicionar fluxo de confirmação de email antes de ser necessário;
- diferencia a origem da redação sem criar subclasses;
- mantém o texto submetido associado exclusivamente ao estudante proprietário.
