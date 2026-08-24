# ADR 0060 — Imutabilidade da avaliação concluída

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

Depois que `Avaliacao` atingir `CONCLUIDA`, não será permitido alterar:

- texto confirmado;
- tema;
- origem;
- nota final;
- notas por competência;
- feedbacks;
- versão ou modelo registrado.

Se o estudante corrigir o texto ou o tema, deverá criar uma nova avaliação e consumir um novo crédito conforme as regras de uso.

Falhas técnicas podem alterar somente o estado operacional antes da conclusão. Uma avaliação concluída não será reaberta nem reprocessada.

## Consequências

- histórico fica estável e auditável;
- cada resultado corresponde exatamente ao texto avaliado;
- correções do estudante geram novos registros, sem sobrescrever resultados antigos;
- exige bloquear updates indevidos no service e no banco quando aplicável.
