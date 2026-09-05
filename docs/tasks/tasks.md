# Tasks do MVP

> Documento temporário de acompanhamento. A pasta `docs/tasks/` deve ser removida
> após a conclusão e revisão das tarefas do MVP.

## Objetivo

Concluir o fluxo principal da Fase 8, mantendo a calibração da IA para uma etapa
posterior. O foco deste documento é transformar o processamento técnico já
existente em uma experiência completa para o usuário.

### Implementacao da Task 1

O frontend agora controla o ciclo de processamento com polling cancelavel,
timeout de dois minutos, estados visuais para `PENDENTE` e `PROCESSANDO`,
protecao contra envio duplicado e retry explicito em falhas.

## 1. Estados do processamento

- [x] Exibir carregamento enquanto a IA processa a avaliação.
- [x] Consultar o status da avaliação no frontend.
- [x] Atualizar a tela até a avaliação ser concluída ou falhar.
- [x] Exibir mensagem clara em caso de falha.
- [x] Evitar que o usuário interprete o processamento como travado.

## 2. Diagnóstico gratuito de uso único

- [ ] Identificar no backend se o usuário já utilizou o diagnóstico.
- [ ] Permitir somente o primeiro diagnóstico gratuitamente.
- [ ] Bloquear novos envios sem crédito.
- [ ] Usar saldo ou regra simulada enquanto créditos reais não forem implementados.
- [ ] Manter o diagnóstico inicial sem exigir compra ou saldo.

## 3. Resultado resumido do diagnóstico

- [x] Exibir a nota geral.
- [x] Exibir poucos apontamentos resumidos.
- [ ] Exibir pontos fortes.
- [ ] Exibir uma orientação inicial de melhoria.
- [x] Ocultar exemplos, evidências e feedbacks completos na interface.
- [ ] Exibir chamada para compra de créditos.
- [ ] Informar que a nota é automática e estimada.

## 4. Avaliação completa

- [x] Permitir iniciar uma avaliação completa.
- [ ] Validar a disponibilidade de crédito.
- [x] Processar a redação pelas competências C1, C2, C3, C4 e C5.
- [x] Exibir a nota de cada competência.
- [x] Exibir a nota geral.
- [x] Exibir erros, trechos, explicações, dicas e exemplos.
- [x] Garantir que o resultado completo não seja exibido para um diagnóstico.
- [x] Garantir que respostas inválidas da IA não sejam persistidas como concluídas.

## 5. Histórico de avaliações

- [x] Listar as avaliações do usuário autenticado.
- [x] Exibir tema, data, nota, status e tipo.
- [x] Diferenciar `DIAGNOSTICO` e `COMPLETA`.
- [x] Permitir abrir uma avaliação pelo histórico.
- [x] Exibir a redação salva.
- [x] Exibir o resultado correspondente ao tipo da avaliação.
- [ ] Tratar avaliações pendentes, processando e falhas.
- [x] Garantir que o usuário não acesse avaliações de outra conta.

## 6. Progresso básico

- [ ] Exibir a última nota concluída.
- [ ] Comparar avaliações concluídas.
- [ ] Exibir evolução por competência.
- [ ] Identificar competências que precisam de atenção.
- [ ] Identificar pontos fortes recorrentes.
- [ ] Sugerir um próximo foco de estudo.
- [ ] Criar estado vazio para usuários sem avaliações suficientes.
- [ ] Evitar apresentar evolução com dados insuficientes.

## 7. Validação ponta a ponta

- [ ] Criar uma conta fictícia e realizar o diagnóstico.
- [ ] Confirmar que o segundo envio é bloqueado sem crédito.
- [ ] Confirmar que o diagnóstico não expõe dados completos.
- [ ] Realizar uma avaliação completa com crédito simulado.
- [ ] Confirmar o processamento até `CONCLUIDA`.
- [ ] Confirmar o tratamento de `FALHOU`.
- [ ] Confirmar o histórico com diagnóstico e avaliação completa.
- [ ] Confirmar o progresso com duas ou mais avaliações.
- [ ] Atualizar a página durante o processamento.
- [ ] Simular indisponibilidade do provedor.
- [ ] Simular resposta inválida da IA.
- [ ] Confirmar que uma avaliação de outra conta não pode ser acessada.

## 8. Fechamento técnico

- [ ] Atualizar os checklists da Fase 8.
- [ ] Atualizar os contratos HTTP e a documentação da API.
- [ ] Adicionar testes frontend de carregamento, erro, bloqueio e resultados.
- [ ] Adicionar testes backend para tipo, acesso e regra de diagnóstico.
- [x] Executar testes, lint, typecheck, build e verificações de formatação.
- [ ] Construir as imagens Docker.
- [ ] Validar as migrações em um banco limpo.
- [ ] Confirmar que nenhum segredo foi versionado.
- [ ] Remover `docs/tasks/` após o aceite do MVP.

## 9. Calibração da IA — etapa posterior

- [ ] Reunir redações de referência com notas oficiais por competência.
- [ ] Comparar resultados por C1, C2, C3, C4 e C5.
- [ ] Repetir avaliações para medir variação do modelo.
- [ ] Refinar o prompt sem usar notas-alvo específicas.
- [ ] Criar testes de regressão para as redações de referência.
- [ ] Avaliar a calibração somente após o fluxo funcional do MVP estar concluído.

## Ordem recomendada

1. Estados do processamento.
2. Diagnóstico único e bloqueio temporário.
3. Resultado resumido.
4. Avaliação completa.
5. Histórico.
6. Progresso.
7. Validação ponta a ponta.
8. Fechamento técnico.
9. Calibração da IA.
