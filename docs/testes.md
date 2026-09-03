# Testes

## Responsabilidade

O responsável pelo projeto executa e aceita os testes manuais. O agente prepara o sistema, cria testes automatizados quando necessários e mantém os cenários documentados, mas não declara o fluxo manual validado sem a execução correspondente.

Não registrar dados reais, tokens, e-mails ou secrets. Usar ambiente local e contas fictícias.

## Verificações automatizadas

Backend:

```bash
mvn --batch-mode verify
mvn --batch-mode spotless:check
```

Frontend:

```bash
npm run format:check
npm run lint
npm run typecheck
npm test -- --run
npm run build
```

## Cenários manuais do MVP

### Conta e segurança

- criar conta e fazer login;
- validar campos obrigatórios e credenciais inválidas;
- renovar sessão, fazer logout e tentar acessar recurso protegido;
- confirmar que um usuário não consulta avaliação de outra conta;
- confirmar que mensagens não expõem dados internos.

### Diagnóstico e avaliação completa

- criar conta fictícia sem avaliações;
- enviar a primeira redação digitada;
- confirmar tipo `DIAGNOSTICO` e ausência de feedback completo;
- confirmar nota e apontamentos resumidos;
- confirmar chamada para comprar créditos;
- tentar novo diagnóstico sem crédito e confirmar bloqueio;
- com crédito, criar avaliação `COMPLETA`;
- confirmar retorno de todos os feedbacks, exemplos e evidências autorizados;
- confirmar que o tipo é preservado no histórico.

### Fase 8

- confirmar que a primeira avaliação recebe tipo `DIAGNOSTICO`;
- confirmar que o diagnóstico não exige crédito e retorna apenas resumo;
- ativar `EVALUATION_REQUIRE_CREDIT_FOR_COMPLETE=true` e confirmar o bloqueio sem crédito;
- confirmar que uma avaliação `COMPLETA` retorna feedbacks, exemplos e evidências autorizados;
- atualizar a página durante o processamento;
- simular falha do provedor e resposta inválida;
- confirmar que falhas não aparecem como avaliação concluída;
- registrar data, ambiente, conta fictícia, passos, esperado, obtido, status e evidências.

### Texto e imagem

- confirmar que o texto inteiro é salvo;
- consultar a avaliação pelo histórico e abrir a redação correta;
- enviar imagem válida, acompanhar OCR e editar a transcrição;
- confirmar transcrição e iniciar avaliação;
- testar arquivo inválido, tamanho excedido, retry e expiração;
- confirmar exclusão da imagem temporária após a confirmação.

### IA, falhas e créditos

- simular indisponibilidade do provedor;
- simular resposta JSON inválida;
- confirmar que avaliação falha não aparece como concluída;
- confirmar que evidência inexistente não é persistida;
- repetir requisição com a mesma chave de idempotência;
- confirmar consumo de crédito somente após confirmação;
- confirmar webhook aprovado, duplicado, recusado e estorno.

## Como registrar

| Data | Cenário | Ambiente | Esperado | Obtido | Status | Observação |
|---|---|---|---|---|---|---|
| — | — | — | — | — | — | — |

Status permitidos: `PASSOU`, `FALHOU` e `BLOQUEADO`. Um cenário crítico não deve permanecer falho ou bloqueado sem decisão registrada.
