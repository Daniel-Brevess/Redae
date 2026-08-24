# ADR 0020 — Usuário único e diagnóstico inicial

- **Status:** superseded
- **Data:** 2026-08-24

## Decisão original

O MVP terá uma única entidade `Usuario` para representar a conta e o perfil básico. Ela conterá os dados necessários para autenticação, identificação, papel e status da conta.

Os papéis serão `STUDENT` e `ADMIN`. O administrador não terá um perfil de estudante separado; sua diferença estará nas permissões e nas informações operacionais que pode consultar.

Não haverá uma entidade `PerfilEstudante` separada no MVP. Dados específicos de aprendizagem serão associados às entidades próprias, como `Diagnostico`, `Redacao`, `Avaliacao` e `RegistroProgresso`.

Cada estudante poderá realizar um único diagnóstico inicial no MVP. O diagnóstico, suas respostas e seu resultado ficarão associados ao `Usuario` e não haverá novas tentativas pelo fluxo normal do produto.

## Substituição

O diagnóstico inicial foi removido do MVP pela ADR 0032. A análise do estudante será representada por `Avaliacao` associada a uma `Redacao`.

## Consequências originais

- reduz duplicação entre conta e perfil;
- simplifica autenticação, autorização e consultas básicas;
- mantém dados de aprendizagem separados sem criar uma camada de perfil desnecessária;
- uma nova tentativa de diagnóstico exigirá decisão futura sobre substituição ou versionamento;
- permissões administrativas continuam sendo controladas pelo papel, não por uma entidade de perfil diferente.
