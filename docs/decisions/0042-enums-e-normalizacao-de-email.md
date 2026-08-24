# ADR 0042 — Enums legíveis e normalização de email

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

Estados e tipos do domínio serão persistidos como strings legíveis, com valores controlados pela aplicação e por constraints quando adequado. Exemplos: `STUDENT`, `ADMIN`, `DIGITADA`, `IMAGEM`, `CONFIRMADA`, `PENDENTE` e `CONCLUIDA`.

Não serão usados números ordinais para representar estados, evitando que a mudança na ordem de um enum altere o significado de registros antigos.

Emails serão normalizados para minúsculas antes da persistência e da comparação de unicidade. O valor normalizado será usado para login e busca da conta.

## Consequências

- facilita leitura manual e auditoria do banco;
- reduz risco de incompatibilidade entre versões do código;
- exige validação de valores permitidos;
- a normalização deve ser aplicada de forma consistente em cadastro, login e recuperação futura.
