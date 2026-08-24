# ADR 0022 — Cálculo da nota da redação

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

O Gemini será responsável por produzir uma nota para cada competência C1, C2, C3, C4 e C5, acompanhada dos feedbacks correspondentes.

O backend será responsável por:

- validar que as cinco competências estão presentes;
- validar que cada nota está dentro da escala permitida;
- calcular a nota final pela soma das cinco notas;
- rejeitar respostas incompletas, inválidas ou fora da escala;
- persistir a nota final somente depois da validação.

A escala de referência do MVP seguirá o ENEM: cinco competências, cada uma de 0 a 200 pontos, e nota total de 0 a 1000. Os intervalos discretos permitidos e as regras detalhadas de cada competência serão definidos na matriz/rubrica da avaliação.

O resultado do Redaê será apresentado como estimativa gerada por IA, não como nota oficial do ENEM.

## Consequências

- a regra matemática fica determinística e auditável no backend;
- a IA não pode definir sozinha a nota total;
- respostas incompletas não geram avaliações falsas;
- alterações futuras na rubrica não exigem transferir o cálculo para o provedor de IA;
- a matriz de níveis das competências ainda precisa ser documentada.
