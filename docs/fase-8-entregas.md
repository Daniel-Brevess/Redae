# Fase 8 — Entregas de diagnóstico e avaliação escrita

> Documento de trabalho temporário. Deve ser removido ao final da implementação da Fase 8.

## Escopo da fase

O usuário já entra no produto autenticado. A Fase 8 concentra-se no fluxo de escrita:

```text
Usuário autenticado
    ↓
Diagnóstico gratuito de escrita
    ↓
Resultado resumido
    ↓
Novas avaliações escritas
    ↓
Avaliação completa e histórico
```

O diagnóstico inicial será gratuito e terá resultado resumido. Depois desse primeiro uso, novas avaliações dependerão de créditos. A implementação de créditos, compras, pagamentos, saldo, consumo e estornos fica adiada para uma etapa posterior.

## 8.1 — Definir o diagnóstico gratuito

### Objetivo

Estabelecer a regra de entrada do usuário no fluxo de avaliação e delimitar o resultado gratuito.

### Entregas

- [x] Confirmar que o diagnóstico será feito por meio de uma redação escrita.
- [x] Permitir um primeiro envio gratuito por usuário.
- [x] Definir que o primeiro benefício será encerrado após o uso.
- [x] Definir que novas avaliações exigirão créditos.
- [x] Definir como o sistema identifica que o diagnóstico já foi realizado.
- [x] Definir os dados exibidos no resultado resumido: nota e alguns erros apresentados de forma breve.
- [x] Definir a chamada para compra de créditos após o resultado resumido.
- [x] Definir os dados da avaliação completa: tópicos C1 a C5, todos os erros encontrados, dicas de melhoria e exemplos aplicados ao texto do usuário.
- [x] Definir a mensagem apresentada após a conclusão do diagnóstico: “Diagnóstico finalizado. Você agora pode comprar créditos para ter uma avaliação completa e continuar evoluindo.”
- [x] Registrar a regra na documentação de produto e nos contratos necessários.

### Critério de conclusão

As regras do diagnóstico gratuito, do bloqueio posterior e dos dois níveis de resultado estão claras para produto, frontend e backend. A implementação do mecanismo de créditos permanece pendente.

## 8.2 — Fluxo de envio da redação

### Objetivo

Permitir que o usuário escreva, revise e confirme uma redação antes do processamento.

### Entregas

- [x] Usuário autenticado acessa a área de avaliação.
- [x] Usuário informa ou edita o tema.
- [x] Usuário escreve a redação.
- [x] Editor exibe a contagem do texto.
- [x] Sistema valida tema preenchido.
- [x] Sistema valida o tamanho mínimo do texto.
- [x] Usuário revisa a redação antes do envio.
- [x] Usuário confirma o envio.
- [x] Fluxo de imagem permanece fora desta entrega.

### Critério de conclusão

O usuário consegue concluir o fluxo de escrita, revisão e confirmação sem precisar enviar um rascunho manual.

## 8.3 — Processamento da avaliação

### Objetivo

Substituir a avaliação simulada por um fluxo real de criação e processamento da avaliação.

### Entregas

- [x] Frontend envia tema, origem digitada e texto confirmado para o backend.
- [x] Backend valida o texto e o tema.
- [x] Backend cria a avaliação vinculada ao usuário autenticado.
- [x] Backend registra a origem como texto digitado.
- [x] Backend inicia o processamento da avaliação.
- [x] Sistema controla os estados pendente, processando, concluída e falhou.
- [x] Usuário autenticado consegue consultar uma avaliação própria pelo identificador.
- [ ] Frontend consulta ou recebe o estado do processamento.
- [ ] Frontend exibe carregamento durante o processamento.
- [ ] Frontend exibe uma mensagem compreensível em caso de falha.
- [ ] O diagnóstico gratuito não menciona nem exige créditos nesta fase.

### Critério de conclusão

Uma redação confirmada gera uma avaliação persistida e o usuário consegue acompanhar o processamento até o resultado ou uma falha tratada.

## 8.4 — Resultado resumido do diagnóstico

### Objetivo

Entregar ao usuário uma devolutiva inicial útil, limitada ao escopo gratuito definido na Etapa 8.1.

### Entregas

- [ ] Exibir a identificação da avaliação e o tema avaliado.
- [ ] Exibir a nota geral, caso ela faça parte do resultado gratuito.
- [ ] Exibir o resumo das competências incluídas no diagnóstico.
- [ ] Usar as competências fixas C1, C2, C3, C4 e C5 quando aplicável.
- [ ] Ocultar informações reservadas para a avaliação completa.
- [ ] Exibir uma síntese dos pontos fortes.
- [ ] Exibir uma orientação inicial de melhoria.
- [ ] Indicar claramente que o resultado é uma avaliação automática estimada.

### Critério de conclusão

O usuário entende o resultado inicial e identifica pelo menos um próximo ponto de melhoria sem acessar o conteúdo reservado da avaliação completa.

## 8.5 — Avaliação completa de escrita

### Objetivo

Permitir que o usuário envie novas redações e receba a avaliação completa enquanto o sistema de créditos estiver adiado.

### Entregas

- [ ] Permitir iniciar uma nova avaliação a partir da área inicial quando houver crédito disponível.
- [ ] Reutilizar o editor e o fluxo de confirmação da redação.
- [ ] Bloquear novos envios quando o usuário não tiver créditos.
- [ ] Processar a redação confirmada pelas competências do ENEM.
- [ ] Exibir nota por competência, organizada em C1, C2, C3, C4 e C5.
- [ ] Exibir nota geral.
- [ ] Exibir feedback estruturado por competência.
- [ ] Exibir todos os erros ou trechos relevantes da redação.
- [ ] Explicar o motivo de cada apontamento.
- [ ] Sugerir formas de melhoria para cada apontamento.
- [ ] Gerar exemplos de melhoria relacionados ao trecho da redação do usuário.

### Critério de conclusão

O usuário consegue enviar uma nova redação e consultar um resultado completo de escrita.

## 8.6 — Histórico de avaliações

### Objetivo

Permitir que o usuário consulte avaliações anteriores e seus resultados.

### Entregas

- [ ] Listar as avaliações do usuário autenticado.
- [ ] Exibir tema, data e status de cada avaliação.
- [ ] Diferenciar o diagnóstico inicial das avaliações posteriores, se essa distinção fizer parte da regra definida.
- [ ] Permitir abrir uma avaliação específica.
- [ ] Exibir o resultado resumido ou completo conforme o tipo de avaliação.
- [ ] Exibir estado de avaliações ainda em processamento.
- [ ] Tratar avaliações que falharam sem exibir dados incompletos como resultado final.
- [ ] Garantir que o usuário não consiga consultar avaliações de outra conta.

### Critério de conclusão

O usuário consegue consultar suas avaliações anteriores e abrir os resultados correspondentes.

## 8.7 — Progresso básico

### Objetivo

Apresentar uma visão simples da evolução do usuário a partir das avaliações realizadas.

### Entregas

- [ ] Exibir a última nota disponível.
- [ ] Comparar resultados quando houver mais de uma avaliação concluída.
- [ ] Exibir evolução por competência quando houver dados suficientes.
- [ ] Mostrar pontos fortes recorrentes.
- [ ] Mostrar competências que precisam de atenção.
- [ ] Sugerir o próximo foco de estudo.
- [ ] Exibir estado vazio para usuário sem avaliação concluída.
- [ ] Evitar apresentar evolução com base em dados insuficientes.

### Critério de conclusão

O usuário consegue entender sua situação atual e identificar um próximo foco de prática.

## 8.8 — Validação do fluxo completo

### Objetivo

Confirmar que o fluxo principal da Fase 8 funciona de ponta a ponta.

### Entregas

- [ ] Usuário autenticado inicia o diagnóstico gratuito.
- [ ] Usuário informa o tema e escreve a redação.
- [ ] Usuário revisa e confirma o envio.
- [ ] Sistema cria e processa a avaliação.
- [ ] Usuário recebe o resultado resumido do diagnóstico.
- [ ] Usuário inicia uma nova avaliação escrita.
- [ ] Usuário recebe a avaliação completa.
- [ ] Usuário consulta as duas avaliações no histórico.
- [ ] Usuário visualiza o progresso básico.
- [ ] Estados de carregamento são compreensíveis.
- [ ] Erros de validação são exibidos no contexto correto.
- [ ] Falhas de processamento não geram resultado falso ou incompleto.
- [ ] O diagnóstico gratuito não depende de compra ou saldo de créditos.
- [ ] O sistema solicita créditos para novas avaliações após o primeiro uso.

### Critério de conclusão

Um usuário autenticado consegue realizar o diagnóstico gratuito, fazer novas avaliações escritas, consultar os resultados e visualizar seu progresso básico.

## Estado da implementacao das etapas 1, 2 e 3

**Status:** concluida tecnicamente; aceite manual ainda pendente.

- Calibracao de prompt: mantida fora desta entrega; o Prompt V4 continua
  experimental e sera validado manualmente.
- Tipo da avaliacao: implementado com `DIAGNOSTICO` e `COMPLETA`.
- Primeiro diagnostico: decidido pelo backend e limitado a um por usuario.
- Resultado resumido: feedbacks completos nao sao expostos para diagnosticos;
  destaques breves ficam disponiveis.
- Resultado completo: feedbacks, evidencias, dicas e exemplos ficam disponiveis
  somente para avaliacoes `COMPLETA`.
- Creditos reais: ainda nao implementados. A propriedade
  `EVALUATION_REQUIRE_CREDIT_FOR_COMPLETE` controla temporariamente o bloqueio.

## Fora do escopo desta fase

- Compra de créditos.
- Saldo de créditos.
- Consumo e estorno de créditos.
- Integração com provedor de pagamento.
- Avaliação por imagem ou OCR.
- Onboarding de objetivo e disponibilidade.
- Plano de estudos avançado.
- Recomendações personalizadas avançadas.

## Pendências para concluir a Fase 8

Esta seção registra o estado real após a implementação inicial do fluxo de
avaliação com IA. Os itens abaixo ainda precisam ser implementados ou
validados para que a fase seja considerada concluída.

### 1. Calibração da avaliação por competência

**Estado:** pendente de implementação.

- [ ] Revisar o prompt com uma rubrica explícita para C1, C2, C3, C4 e C5.
- [ ] Definir níveis de desempenho forte, intermediário e fraco por competência.
- [ ] Exigir justificativa para cada desconto relevante.
- [ ] Evitar que o mesmo problema seja penalizado em várias competências sem
      impactos diferentes e comprováveis.
- [ ] Criar redações de referência com notas esperadas por competência.
- [ ] Criar testes de regressão para redações de referência.
- [ ] Comparar a distribuição entre C1-C5, e não apenas a nota final.
- [ ] Reavaliar a redação de referência que recebeu nota 600 quando a
      expectativa era 980.

O planejamento detalhado está registrado em
`docs/decisions/0080-calibracao-da-avaliacao-por-competencia.md`.

### 2. Regra do diagnóstico gratuito

**Estado:** regra definida; controle de acesso pendente.

- [ ] Identificar no backend se o usuário já consumiu o diagnóstico gratuito.
- [ ] Permitir somente o primeiro diagnóstico sem crédito.
- [ ] Bloquear novas avaliações sem saldo de crédito.
- [ ] Exibir uma mensagem clara quando o usuário estiver bloqueado.
- [ ] Manter o diagnóstico inicial sem exigir compra ou saldo.

A compra e o pagamento de créditos continuam fora do escopo. Para esta fase,
é suficiente que o backend utilize uma regra temporária ou um saldo simulado
para proteger novas avaliações.

### 3. Resultado resumido do diagnóstico

**Estado:** resultado técnico disponível; separação de acesso pendente.

- [ ] Definir quais campos pertencem ao resultado gratuito.
- [ ] Exibir somente a nota e os erros breves permitidos no diagnóstico.
- [ ] Ocultar feedback completo, exemplos e detalhes reservados.
- [ ] Exibir uma síntese de pontos fortes.
- [ ] Exibir uma orientação inicial de melhoria.
- [ ] Informar que a nota é uma estimativa automática.
- [ ] Exibir a chamada para comprar créditos e acessar a avaliação completa.
- [ ] Exibir a mensagem final do diagnóstico conforme a decisão de produto.

### 4. Avaliação completa de escrita

**Estado:** processamento e exibição técnica implementados; regra de acesso
pendente.

- [ ] Liberar a avaliação completa somente quando houver crédito disponível.
- [ ] Garantir que o diagnóstico inicial não consuma crédito.
- [ ] Exibir as cinco competências com pontuação, resumo e feedback.
- [ ] Exibir erros, trechos, explicações, dicas e exemplos.
- [ ] Exibir limitações quando a IA não localizar uma evidência literal.
- [ ] Validar que o resultado completo não mostre dados incompletos.

### 5. Histórico de avaliações

**Estado:** listagem e consulta por usuário implementadas; diferenciação de
tipos pendente.

- [ ] Identificar cada avaliação como diagnóstico gratuito ou avaliação completa.
- [ ] Exibir essa identificação no histórico.
- [ ] Exibir corretamente tema, data, nota e status.
- [ ] Permitir abrir uma avaliação concluída pelo histórico.
- [ ] Exibir o resultado resumido ou completo conforme o tipo da avaliação.
- [ ] Exibir estados pendente, processando e falhou sem simular resultado.
- [ ] Validar o histórico com múltiplas avaliações do mesmo usuário.
- [ ] Validar que uma avaliação de outra conta não possa ser consultada.

### 6. Progresso básico

**Estado:** layout inicial existente; dados reais e regras de negócio
pendentes.

- [ ] Calcular a última nota concluída a partir da API.
- [ ] Comparar a primeira avaliação com avaliações posteriores.
- [ ] Calcular evolução por C1-C5.
- [ ] Identificar competências recorrentes que precisam de atenção.
- [ ] Identificar pontos fortes recorrentes.
- [ ] Sugerir o próximo foco com base em dados suficientes.
- [ ] Exibir estado vazio para usuários sem avaliação concluída.
- [ ] Não apresentar evolução quando houver dados insuficientes.

### 7. Validação ponta a ponta

**Estado:** validação manual parcial; aceite final pendente.

- [ ] Testar o diagnóstico gratuito em uma conta nova.
- [ ] Confirmar que o segundo envio é bloqueado sem crédito.
- [ ] Confirmar que o resultado gratuito não expõe dados completos.
- [ ] Testar uma avaliação completa com crédito simulado.
- [ ] Confirmar o processamento assíncrono até `CONCLUIDA`.
- [ ] Confirmar o tratamento de `FALHOU` com causa compreensível.
- [ ] Confirmar o histórico com diagnóstico e avaliação completa.
- [ ] Confirmar o progresso com duas ou mais avaliações concluídas.
- [ ] Testar atualização da página durante o processamento.
- [ ] Testar acesso direto às rotas `/home` e ao resultado no Docker.
- [ ] Testar uma resposta da IA com excerpt inválido sem interromper a avaliação.
- [ ] Testar respostas inválidas da IA e confirmar que não geram nota falsa.

### 8. Qualidade técnica antes do encerramento

- [ ] Atualizar os checklists das etapas 8.1 a 8.8 conforme o comportamento
      final implementado.
- [ ] Atualizar os contratos HTTP e a documentação da API quando o resultado
      resumido e o tipo da avaliação forem adicionados.
- [ ] Adicionar testes backend para bloqueio, tipo de resultado e calibração.
- [ ] Adicionar testes frontend para carregamento, falha, bloqueio e resultado
      resumido.
- [ ] Executar Spotless e testes backend.
- [ ] Executar Prettier, lint, testes e build frontend.
- [ ] Construir as imagens Docker e validar as migrações em um banco limpo.
- [ ] Confirmar que nenhuma chave de API ou segredo foi versionado.

## Ordem recomendada para finalizar

1. Calibrar a IA usando a matriz C1-C5 e redações de referência.
2. Implementar o tipo da avaliação e a regra do primeiro diagnóstico.
3. Separar o resultado resumido do resultado completo no backend e frontend.
4. Implementar o bloqueio temporário de novas avaliações sem crédito.
5. Finalizar histórico e progresso com dados reais.
6. Adicionar testes de regressão e executar a validação ponta a ponta.
7. Atualizar os checklists e remover este documento temporário somente após o
   aceite da Fase 8.
