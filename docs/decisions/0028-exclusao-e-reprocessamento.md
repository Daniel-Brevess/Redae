# ADR 0028 — Exclusão de dados e reprocessamento

- **Status:** aceito
- **Data:** 2026-08-24

## Exclusão da conta

Quando o estudante excluir a conta, o sistema excluirá suas redações, avaliações, feedbacks, diagnósticos e demais dados acadêmicos associados. Imagens temporárias também deverão ser removidas.

Somente metadados técnicos anonimizados poderão permanecer quando forem necessários para segurança, auditoria operacional ou métricas agregadas. Esses metadados não poderão permitir a reconstrução da identidade, do texto ou do feedback do estudante.

## Reprocessamento

Uma avaliação concluída não será reprocessada automaticamente. Novas chamadas à IA serão permitidas somente para recuperar falhas técnicas sem resultado válido, respeitando idempotência e limites de tentativa.

Se o estudante quiser uma nova avaliação de conteúdo, deverá enviar uma nova redação confirmada. A nova submissão terá seu próprio registro e avaliação.

## Consequências

- simplifica o direito de exclusão e reduz retenção de dados;
- evita resultados múltiplos e conflitantes para a mesma redação;
- exige cuidado com cascatas de exclusão e referências externas;
- mantém métricas operacionais somente em forma anonimizada.
