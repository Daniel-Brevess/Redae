# Calibração dos prompts de avaliação

## Objetivo

Comparar versões do prompt usando redações com notas humanas conhecidas por
competência, evitando ajustar a IA para uma única redação.

## Redação de referência já testada

- **Nota humana:** 920
- **Prompt V1:** 800
- **Prompt V2:** 760
- **Próximo teste:** uma redação de nota humana 600 usando V1 e V2.

As notas devem ser comparadas por C1, C2, C3, C4 e C5, e não somente pelo
total. A nota humana por competência ainda deve ser registrada para que a
comparação seja conclusiva.

## Prompt V1 — versão atualmente utilizada

**Fonte operacional:**
`backend/src/main/java/br/com/redae/evaluation/service/AIEvaluationAnalyzer.java`

**Características:**

- define a IA como avaliadora especialista nos critérios do ENEM;
- solicita avaliação independente de C1 a C5;
- orienta a considerar o conjunto da redação;
- desencoraja descontos significativos por erros isolados;
- descreve os critérios gerais de cada competência;
- exige evidências literais nos feedbacks;
- permite `feedbackItems` vazio quando não houver problema relevante;
- solicita níveis inteiros de 0 a 5;
- informa que o backend calcula a nota final;
- exige JSON contendo exatamente C1, C2, C3, C4 e C5.

O V1 é o prompt que produziu nota 800 na redação de referência. Ele será
utilizado no código enquanto o V2 estiver sendo comparado experimentalmente.

## Prompt V2 — versão experimental

**Origem:** alteração local aplicada em 2026-08-28 e posteriormente removida
do código para repetir os testes com o V1. Não foi commitada como versão
operacional.

**Características adicionadas ao V1:**

- seção explícita de regras gerais;
- ordem obrigatória de análise: pontos fortes, limitações, impacto e nível;
- definição textual dos níveis 0 a 5;
- instrução para não reduzir a competência por oportunidade de melhoria;
- regra para considerar elemento implícito na C5;
- regra para diferenciar conectivo inadequado de conectivo apenas repetido;
- regra para não considerar tese aperfeiçoável como compreensão insuficiente;
- orientação para não reduzir C3 quando a argumentação for pertinente, mesmo
  que possa ser aprofundada;
- orientação para considerar a gravidade e a frequência dos desvios em C1;
- reforço contra penalização duplicada entre competências;
- reforço para não informar ou perseguir uma nota-alvo.

O V2 produziu nota 760 na mesma redação cuja referência humana é 920. Esse
resultado não é suficiente para concluir que o V2 é pior, pois a avaliação de
um único texto não mede generalização.

## Registro dos resultados

| Redação | Nota humana | Prompt | Nota IA | Observação |
|---|---:|---|---:|---|
| Referência alta | 920 | V1 | 800 | Primeira comparação registrada |
| Referência alta | 920 | V2 | 760 | Versão experimental mais conservadora |
| Referência média/baixa | 600 | V1 | pendente | Próximo teste |
| Referência média/baixa | 600 | V2 | pendente | Próximo teste |

Para cada novo teste, registrar também:

- C1, C2, C3, C4 e C5 da avaliação humana;
- C1, C2, C3, C4 e C5 retornadas pela IA;
- justificativa de cada desconto;
- evidências utilizadas;
- modelo, temperatura e demais parâmetros da requisição;
- data e horário do teste.

## Regra para decidir a próxima versão

Não ajustar o prompt para alcançar 920 na redação de referência. Uma alteração
só deve ser mantida quando corrigir um padrão observado em várias redações,
especialmente quando a mesma competência for subavaliada ou superavaliada em
textos de temas e níveis diferentes.

O conjunto de validação deve conter redações de notas alta, média e baixa, com
temas variados e notas humanas por C1-C5. Sempre que possível, uma parte das
redações deve ficar reservada para validação cega, sem ser usada na criação do
prompt.

## Estado atual

- O código voltou a utilizar o Prompt V1.
- O Prompt V2 está documentado como experimento, mas não está ativo.
- Nenhum resultado da redação de nota 600 foi registrado ainda.
- A calibração ainda não está concluída.
