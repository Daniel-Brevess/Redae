# Testes manuais da Fase 8

## Cenarios adicionados nesta entrega

- [ ] Confirmar que a primeira avaliacao recebe tipo `DIAGNOSTICO`.
- [ ] Confirmar que um diagnostico nao retorna `feedbackItems` completos.
- [ ] Confirmar que o diagnostico retorna apenas destaques resumidos.
- [ ] Ativar `EVALUATION_REQUIRE_CREDIT_FOR_COMPLETE=true` e confirmar o bloqueio
      de uma nova avaliacao sem credito.
- [ ] Com a exigencia desativada no ambiente local, confirmar tipo `COMPLETA`.
- [ ] Confirmar que uma avaliacao `COMPLETA` retorna todos os feedbacks, exemplos
      e evidencias autorizados.
- [ ] Confirmar que o historico informa o tipo e nao expoe o texto.

## Responsabilidade pelos testes

Os testes manuais da Fase 8 serão executados pelo responsável pelo projeto.
O objetivo é praticar a criação, execução, observação e documentação de testes
de comportamento real do produto.

Durante a implementação, o agente deverá:

- preparar o sistema para ser testado;
- adicionar ou ajustar testes automatizados quando forem necessários para a
  segurança do código;
- manter neste documento a lista dos testes manuais que precisam ser feitos;
- não declarar o fluxo como validado sem a execução manual correspondente.

Ao responsável pelo projeto caberá:

- executar os cenários manualmente;
- registrar o resultado de cada cenário;
- informar erros encontrados;
- repetir os cenários após as correções;
- confirmar o aceite da funcionalidade.

Não utilizar redações, contas, e-mails, tokens ou outros dados reais nos testes
registrados no repositório. Usar dados fictícios e ambiente local.

## Como registrar um teste

Para cada cenário, registrar:

- data;
- ambiente utilizado;
- usuário ou conta fictícia;
- passos executados;
- resultado esperado;
- resultado obtido;
- status: `PASSOU`, `FALHOU` ou `BLOQUEADO`;
- evidência ou observação relevante;
- referência de uma issue, quando existir.

## Checklist inicial da implementação dos itens 1, 2 e 3

### Diagnóstico gratuito de uso único

- [ ] Criar uma conta fictícia sem avaliações.
- [ ] Enviar uma primeira redação digitada.
- [ ] Confirmar que a avaliação foi criada como diagnóstico.
- [ ] Confirmar que o diagnóstico não exige crédito.
- [ ] Confirmar que o diagnóstico é persistido para o usuário correto.
- [ ] Tentar enviar uma segunda redação sem crédito.
- [ ] Confirmar que o backend bloqueia o segundo envio.
- [ ] Confirmar o status HTTP e o código de erro retornado.
- [ ] Confirmar que a mensagem de bloqueio é compreensível no frontend.
- [ ] Confirmar que o usuário não consegue contornar o bloqueio alterando a
  requisição no navegador.

### Separação entre diagnóstico e avaliação completa

- [ ] Confirmar que a resposta da API informa o tipo da avaliação.
- [ ] Confirmar que o primeiro envio possui tipo `DIAGNOSTICO`.
- [ ] Confirmar que o frontend não consegue escolher livremente o tipo para
  obter novos diagnósticos gratuitos.
- [ ] Confirmar que avaliações futuras poderão ser identificadas como
  `COMPLETA` quando o controle de créditos for integrado.
- [ ] Confirmar que o histórico preserva o tipo da avaliação.
- [ ] Confirmar que uma avaliação de outra conta não pode ser consultada.

### Resultado resumido do diagnóstico

- [ ] Concluir o processamento de um diagnóstico.
- [ ] Confirmar que o tema é exibido.
- [ ] Confirmar que a nota geral é exibida.
- [ ] Confirmar que apenas os apontamentos resumidos permitidos são exibidos.
- [ ] Confirmar que o resultado não exibe todos os feedbacks completos.
- [ ] Confirmar que exemplos completos não são exibidos no diagnóstico.
- [ ] Confirmar que existe uma orientação inicial de melhoria.
- [ ] Confirmar que a chamada para comprar créditos é exibida.
- [ ] Confirmar que a mensagem final do diagnóstico é exibida.
- [ ] Confirmar que o resultado informa que a avaliação é automática e
  estimada.

### Estados e falhas

- [ ] Atualizar a página enquanto a avaliação estiver processando.
- [ ] Confirmar que o estado de carregamento é compreensível.
- [ ] Simular uma falha do provedor de IA.
- [ ] Confirmar que uma mensagem de falha é exibida.
- [ ] Confirmar que uma avaliação falha não aparece como concluída.
- [ ] Confirmar que uma resposta inválida da IA não gera nota falsa.

## Registro da execução

Esta seção será preenchida pelo responsável após a implementação.

| Data | Cenário | Ambiente | Resultado | Status | Observações |
|---|---|---|---|---|---|
| — | — | — | — | — | — |

## Critério de aceite manual

Os itens 1, 2 e 3 somente serão considerados validados quando os cenários
obrigatórios forem executados manualmente, os resultados forem registrados e
nenhum cenário crítico permanecer com status `FALHOU` ou `BLOQUEADO` sem uma
decisão documentada.
