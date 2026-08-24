# ADR 0004 — Polling para status de processamento no MVP

- **Status:** aceito
- **Data:** 2026-08-24

## Contexto

O frontend precisa informar ao estudante quando a transcrição ou a avaliação terminarem. WebSockets e notificações push atenderiam ao problema, mas adicionariam infraestrutura e complexidade antes de existir volume suficiente para justificar essas tecnologias.

## Decisão

O MVP usará polling simples para consultar o status do processamento:

- enquanto a tela de processamento estiver aberta, o frontend consultará o backend em intervalos controlados;
- o polling será interrompido quando o estado for `CONCLUÍDO` ou `FALHOU`;
- ao retornar à tela ou abrir o histórico, o frontend fará uma consulta imediata;
- o intervalo exato poderá ser ajustado por configuração e não será tratado como regra de domínio.

## Consequências

- menor complexidade operacional e de implementação;
- nenhuma conexão persistente será necessária no MVP;
- o estudante poderá perceber uma pequena demora entre a conclusão do trabalho e a atualização da tela;
- o backend deverá aplicar limites de requisição para impedir polling excessivo.

WebSockets, Server-Sent Events e notificações push ficam fora do MVP e poderão ser avaliados quando houver evidência de necessidade.
