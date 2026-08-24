# ADR 0035 — Estados de redação e avaliação

- **Status:** superseded by ADR 0045
- **Data:** 2026-08-24

## Redacao

`Redacao.status` terá os valores:

- `CONFIRMADA`: texto confirmado e persistido;
- `EM_AVALIACAO`: avaliação assíncrona em andamento;
- `AVALIADA`: avaliação concluída com nota e feedback válidos;
- `FALHOU`: avaliação não foi concluída por falha técnica ou resposta inválida.

## Avaliacao

`Avaliacao.status` terá os valores:

- `PENDENTE`: avaliação criada e aguardando processamento;
- `PROCESSANDO`: worker executando a avaliação;
- `CONCLUIDA`: nota e feedback persistidos com sucesso;
- `FALHOU`: processamento encerrado sem resultado válido.

Uma transição para `AVALIADA`/`CONCLUIDA` só ocorrerá depois da validação e persistência atômica do resultado.
