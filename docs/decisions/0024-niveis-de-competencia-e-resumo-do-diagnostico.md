# ADR 0024 — Níveis de competência e resumo do diagnóstico

- **Status:** aceito
- **Data:** 2026-08-24

## Escala da avaliação

Cada competência C1–C5 será avaliada pela IA em um nível inteiro de 0 a 5. O backend converterá o nível para pontos usando a escala:

| Nível | Pontos |
| ---: | ---: |
| 0 | 0 |
| 1 | 40 |
| 2 | 80 |
| 3 | 120 |
| 4 | 160 |
| 5 | 200 |

O backend soma as cinco pontuações para obter a nota final de 0 a 1000.

## Diagnóstico

A IA poderá gerar um resumo textual do diagnóstico inicial com base nas respostas do estudante. O backend validará o formato, associará o resumo ao diagnóstico e o persistirá junto das respostas, data e status.

O resumo é uma orientação personalizada do produto, não uma nota oficial ou diagnóstico clínico.

## Consequências

- a regra de conversão fica centralizada e determinística no backend;
- a IA trabalha com níveis compreensíveis para o contrato de avaliação;
- o resumo do diagnóstico pode ser consultado sem repetir a chamada à IA;
- respostas inválidas ou resumo ausente impedem a conclusão do diagnóstico.
