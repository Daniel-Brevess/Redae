# ADR 0079 — Prazo e escopo do MVP de agosto

- **Status:** aceito
- **Data:** 2026-08-27
- **Prazo:** 2026-08-31

## Contexto

O projeto precisa chegar ao fim de agosto com uma versão funcional para
demonstração e validação. Restam poucos dias de desenvolvimento, portanto o
escopo precisa ser reduzido ao fluxo que comprova o valor principal do Redaê.

As fases 8 e 9 do roadmap possuem um escopo maior, incluindo onboarding,
diagnóstico, treino, qualidade, segurança, operação, monitoramento e backup.
Concluir todos esses itens até 31/08 não é compatível com o prazo disponível.

## Decisão

Até 31/08, o objetivo será entregar um MVP demonstrável, e não uma versão
completa e pronta para operação pública.

O MVP terá como fluxo principal:

```text
Cadastro/login
    ↓
Área privada
    ↓
Editor de redação digitada
    ↓
Confirmação e envio
    ↓
Persistência da redação
    ↓
Avaliação com nota e feedback
    ↓
Consulta do resultado e histórico básico
```

## Escopo incluído

- cadastro e login;
- rotas privadas;
- editor de redação digitada;
- confirmação e envio do texto;
- persistência da redação;
- avaliação com nota e feedback por competência;
- consulta do resultado;
- histórico básico;
- deploy funcional para demonstração.

## Escopo adiado

Os itens abaixo não fazem parte do caminho crítico deste prazo:

- confirmação de e-mail;
- troca e recuperação de senha;
- redação por imagem e OCR;
- onboarding completo;
- diagnóstico avançado;
- créditos e pagamentos;
- testes ponta a ponta completos;
- monitoramento avançado;
- backup e estratégia operacional robusta de produção.

A confirmação de e-mail permanece preparada no código, mas desligada pela
feature flag existente. Ela será ativada somente quando houver domínio,
configuração do Resend e necessidade real de uso.

## Plano de execução

| Data | Entrega principal |
|---|---|
| 27/08 | Congelamento do escopo e dos contratos da feature principal |
| 28/08 | Backend de redação, avaliação e persistência |
| 29/08 | Integração do frontend com as rotas reais |
| 30/08 | Testes do fluxo principal, integração e correções |
| 31/08 | Deploy, demonstração e documentação das pendências |

## Critério de saída

Um estudante autenticado deve conseguir escrever uma redação, confirmar o
envio, receber uma avaliação e consultar o resultado posteriormente.

## Riscos aceitos

- o MVP poderá utilizar uma implementação inicial controlada do avaliador caso
  a integração definitiva com o provedor de IA não esteja pronta;
- o histórico será básico e limitado ao necessário para demonstrar o fluxo;
- a versão entregue não será considerada pronta para uma operação pública sem
  a conclusão da fase 9;
- funcionalidades não essenciais não serão adicionadas para preservar o prazo.

## Consequências

- o valor principal do produto será validado mais cedo;
- o escopo fica controlável para o prazo de quatro dias;
- algumas decisões de produção permanecerão pendentes;
- a fase 9 continuará necessária para qualidade, segurança e operação real.
