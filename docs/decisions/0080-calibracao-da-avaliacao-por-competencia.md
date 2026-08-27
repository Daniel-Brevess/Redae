# ADR 0080 — Calibração da avaliação por competência

- **Status:** proposto
- **Data:** 2026-08-27

## Contexto

O fluxo de avaliação com IA está funcionando, mas uma redação cuja referência
é 980 recebeu uma distribuição de notas diferente entre C1, C2, C3, C4 e C5,
resultando em uma nota final de 600. O problema precisa ser tratado na
qualidade da análise de cada competência, e não apenas na soma da nota final.

## Objetivo

Tornar a avaliação por competência mais consistente com a matriz C1-C5 e com
redações de referência, reduzindo penalizações exageradas e exigindo que cada
desconto seja sustentado por evidências do texto.

## Refinamento planejado

### 1. Rubrica explícita por competência

O prompt da IA deverá apresentar, para cada competência:

- o que deve ser avaliado;
- o que caracteriza desempenho forte, intermediário e fraco;
- quais situações justificam desconto;
- quais situações não devem ser penalizadas sem evidência suficiente.

A matriz de avaliação em `docs/project/matriz-avaliacao-c1-c5.md` será a fonte
de referência do prompt.

### 2. Justificativa dos descontos

Para cada competência, a IA deverá informar:

- nível e pontuação atribuídos;
- pontos fortes identificados;
- problemas que justificam a redução;
- evidências literais da redação;
- impacto de cada problema na competência.

Uma competência não deverá receber uma redução relevante somente por uma
impressão geral ou por um erro isolado de baixo impacto.

### 3. Separação entre competências

O prompt deverá orientar a IA a não reutilizar o mesmo problema em várias
competências, salvo quando explicar impactos distintos e comprováveis.

Exemplos:

- erro de ortografia deve afetar principalmente C1;
- falta de conectivo deve ser analisada em C4;
- argumento pouco desenvolvido deve ser analisado em C3;
- ausência de agente, ação ou finalidade deve ser analisada em C5.

### 4. Redações de referência

Será criada uma coleção de redações avaliadas manualmente, contendo exemplos
de desempenho forte, intermediário e fraco em cada competência. Os exemplos
servirão para:

- calibrar o prompt;
- comparar a distribuição C1-C5;
- detectar penalizações incompatíveis com a referência;
- criar testes de regressão para futuras alterações.

As referências deverão conter a nota esperada por competência e a justificativa
da avaliação. A nota de referência não será usada para forçar a IA a produzir
um total específico sem justificativa.

### 5. Revisão da nota final

O backend continuará calculando a nota final pela soma das cinco competências.
Antes da persistência, a análise deverá ser revisada quanto a:

- presença de C1, C2, C3, C4 e C5;
- coerência entre nível, pontuação e justificativas;
- existência de evidência para cada desconto relevante;
- ausência de penalização duplicada sem justificativa;
- coerência entre a análise e o tema da redação.

Se a resposta não apresentar justificativa suficiente, ela deverá ser tratada
como inválida para processamento e não como uma nota artificialmente baixa.

## Fora deste refinamento

- alterar a escala oficial de 0 a 1000;
- substituir a soma determinística do backend;
- garantir que a IA reproduza exatamente uma nota humana;
- criar ainda o sistema de compra ou consumo de créditos;
- mudar o tratamento já implementado para `excerpt` não localizado.

## Critérios de aceitação

O refinamento será considerado adequado quando:

- uma redação de referência tiver sua distribuição C1-C5 analisada de forma
  coerente com a avaliação humana;
- cada desconto relevante possuir justificativa e evidência;
- problemas de uma competência não forem transferidos automaticamente para
  outras competências;
- a nota final continuar sendo calculada pelo backend;
- os casos de referência forem cobertos por testes de regressão;
- Spotless, testes backend, Prettier, lint e build frontend continuarem
  passando no CI.

## Próxima implementação

1. Consolidar a nota esperada e a justificativa da redação de referência de
   980.
2. Revisar o prompt por competência.
3. Ajustar o contrato estruturado caso sejam necessários campos de pontos
   fortes, impacto ou confiança.
4. Implementar validações de coerência no backend.
5. Adicionar testes com referências de diferentes níveis de desempenho.
6. Reavaliar a redação de 980 e comparar C1-C5, não apenas o total.
