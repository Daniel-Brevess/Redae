# Produto

## Visão

O Redaê é uma plataforma de treinamento para estudantes que se preparam para a redação do ENEM. O produto deve ajudar o estudante a entender onde errou, por que errou e qual ação pode tomar para melhorar.

Fluxo central: **Escrever → Analisar → Diagnosticar → Treinar → Evoluir**.

## Público e problema

O público inicial é o estudante autodidata que pratica redação, mas não sabe exatamente onde perde pontos, recebe feedback pouco acionável ou não consegue acompanhar sua evolução.

O produto prioriza clareza, confiança, feedback específico e progresso visível. Não promete equivalência com a nota oficial nem substitui um corretor humano.

## Escopo atual do MVP

- cadastro, login, sessão e perfil;
- redação digitada;
- redação por imagem, com transcrição e conferência previstas;
- avaliação automática pelas competências C1 a C5;
- nota estimada na escala do ENEM;
- feedback por competência, com evidência literal quando disponível;
- persistência da redação, nota e feedback;
- histórico do estudante;
- créditos e compra integrados de forma progressiva;
- diagnóstico inicial separado da avaliação completa.

Ficam fora do MVP: comunidade, ranking, gamificação, plano de estudos completo, outros vestibulares, correção humana integrada e recomendações avançadas de longo prazo.

## Diagnóstico e avaliação completa

O primeiro envio do usuário é um diagnóstico. O diagnóstico pode apresentar nota e apontamentos resumidos, além da chamada para comprar créditos. Avaliações completas usam créditos e exibem os feedbacks detalhados, exemplos e evidências permitidos pelo contrato.

O tipo da avaliação é definido pelo backend e persistido. O frontend não pode escolher livremente o tipo para obter novos diagnósticos gratuitos.

## Fluxo do estudante

1. O estudante entra ou cria uma conta.
2. Escolhe uma nova avaliação.
3. Informa o tema e digita o texto ou envia imagens.
4. Revisa a transcrição quando a entrada for uma imagem.
5. Confirma o texto.
6. Acompanha o processamento.
7. Consulta o diagnóstico ou a avaliação completa.
8. Pratica novamente e acompanha o histórico.

## Competências

- **C1:** domínio da modalidade escrita formal;
- **C2:** compreensão da proposta e desenvolvimento do tema;
- **C3:** seleção, organização e interpretação de informações e argumentos;
- **C4:** mecanismos linguísticos para a argumentação;
- **C5:** proposta de intervenção.

A escala interna usa níveis inteiros de 0 a 5. A nota da redação é calculada pelo backend a partir dos cinco níveis; a IA não define uma nota total diretamente.

Feedbacks devem citar apenas trechos literais encontrados no texto. Quando não houver evidência segura, o trecho deve ficar vazio. Problemas isolados não devem derrubar significativamente uma competência sem impacto real.

## Critérios de sucesso

- o estudante entende o próximo passo em cada tela;
- cada nota vem acompanhada de explicação;
- o feedback orienta uma ação prática;
- a transcrição pode ser revisada antes da análise;
- o histórico permite perceber evolução;
- falhas de IA, OCR, rede ou pagamento não geram resultado falso.

## Glossário resumido

| Termo | Significado |
|---|---|
| Diagnóstico | Primeira avaliação resumida do estudante. |
| Avaliação completa | Avaliação detalhada liberada conforme a regra de créditos. |
| Competência | Um dos cinco critérios C1–C5 do ENEM. |
| Nota de competência | Nível e pontuação atribuídos a uma competência. |
| Feedback item | Problema, explicação, melhoria e evidência associados a uma competência. |
| Texto confirmado | Texto que o estudante revisou e autorizou para avaliação. |
| Ledger de créditos | Registro das entradas e saídas usadas para calcular o saldo. |
