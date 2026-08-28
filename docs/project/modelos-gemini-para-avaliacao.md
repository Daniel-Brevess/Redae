# Modelos Gemini para avaliação de redações

## Objetivo

Registrar o modelo atualmente utilizado pelo Redaê e os modelos Gemini que serão
avaliados na próxima rodada de calibração da nota por competência.

## Situação atual

- Modelo em uso: `gemini-3.6-flash`.
- Provedor: Google Gemini API, por meio do cliente de IA já integrado ao backend.
- Prompt ativo: Prompt V2, registrado em
  [`calibracao-prompts-avaliacao.md`](./calibracao-prompts-avaliacao.md).
- Formato de saída: JSON estruturado, validado pelo backend antes da persistência.
- Competências avaliadas: C1, C2, C3, C4 e C5.
- Nota final: calculada pelo sistema a partir dos cinco níveis retornados pela IA.

### Resultados já observados

Os testes registrados até o momento indicaram:

| Nota de referência | Resultado observado |
| ---: | ---: |
| 920 | 760 em um dos testes; 800 em teste anterior |
| 600 | 520 |

Esses resultados são indicadores de calibração, não uma medição definitiva de
qualidade. A comparação deverá usar o mesmo conjunto de redações, temas, prompt e
configurações para todos os modelos.

## Modelo atualmente selecionado para teste

### Gemini 3.6 Flash

O modelo configurado no backend para a próxima rodada de validação. A troca foi
feita mantendo o mesmo Prompt V2, schema JSON e fluxo de persistência, para que a
comparação com os modelos anteriores seja válida.

Identificador configurado: `gemini-3.6-flash`.

## Modelos selecionados para teste

### Gemini 3.7 Flash

Modelo já testado na comparação. Ele permanece documentado como candidato, mas
não será o modelo ativo nesta etapa.

Identificador: `gemini-3.7-flash`.

## Modelos fora desta rodada

Não serão testados nesta etapa:

- `gemini-3.1-flash-lite`;
- `gemini-2.5-flash-lite`;
- modelos Gemini Pro;
- modelos DeepSeek.

Esses modelos podem ser considerados posteriormente, mas não fazem parte da
comparação atual definida para reduzir o número de variáveis.

## Plano de comparação

Para cada redação do banco de referência:

1. enviar exatamente o mesmo tema e texto;
2. usar o mesmo Prompt V2;
3. manter o mesmo schema JSON;
4. registrar as notas de C1 a C5;
5. registrar a nota total calculada pelo sistema;
6. comparar os erros, os feedbacks e os trechos utilizados como evidência;
7. observar custo, tempo de resposta e ocorrência de respostas inválidas.

O modelo não será escolhido apenas pela maior nota. A decisão deverá considerar a
proximidade da avaliação humana, a consistência entre diferentes redações e a
qualidade das justificativas.

## Fontes

- [Preços oficiais da Gemini API](https://ai.google.dev/gemini-api/docs/pricing)
- [Informações oficiais sobre o Gemini 3.7 Flash](https://ai.google.dev/gemini-api/docs/latest-model)
