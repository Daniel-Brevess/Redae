# Roteiro de validação e critérios de aceite do MVP

> **Status:** versão inicial  
> **Escopo:** validação interna antes de qualquer piloto externo

Este roteiro define como verificar se o MVP do Redaê está funcional e apresentável. Ele não substitui testes automatizados; serve como checklist de validação do produto e como referência para decidir se o MVP pode avançar para apresentação.

## 1. Preparação

- executar frontend e backend em ambiente local;
- usar uma conta de teste limpa;
- separar uma redação digitada de teste;
- separar imagens legíveis de uma redação manuscrita ou impressa;
- registrar versão, data, ambiente e problemas encontrados;
- testar em pelo menos um desktop e uma largura de tela mobile.

## 2. Roteiro principal

### Fluxo A — acesso

1. Abrir a landing page.
2. Acessar cadastro.
3. Criar uma conta com dados válidos.
4. Fazer logout.
5. Fazer login novamente.
6. Tentar acessar uma área privada sem autenticação.

**Resultado esperado:** a conta é criada, o login funciona, o logout encerra o acesso e a área privada não fica disponível para usuários não autenticados.

### Fluxo B — onboarding e diagnóstico

1. Informar objetivo de estudo.
2. Informar disponibilidade.
3. Concluir o onboarding.
4. Iniciar o diagnóstico.
5. Responder ao diagnóstico.
6. Consultar o resultado ou a próxima recomendação.

**Resultado esperado:** os dados são salvos, o onboarding não precisa ser repetido sem motivo e o diagnóstico gera um próximo passo compreensível.

### Fluxo C — redação digitada

1. Receber ou informar a proposta/tema associado à redação.
2. Abrir o editor.
3. Escrever ou colar uma redação.
4. Decidir se deseja confirmar o texto.
5. Confirmar e enviar a redação.
6. Acompanhar o processamento.
7. Consultar o feedback.

**Resultado esperado:** somente o texto confirmado é persistido, o envio muda o estado da redação e o feedback é associado à redação correta.

### Fluxo D — redação por imagem

1. Escolher entrada por imagem.
2. Enviar de 1 a 5 imagens JPG ou PNG.
3. Confirmar que arquivos acima do limite são rejeitados com mensagem clara.
4. Aguardar a transcrição.
5. Revisar e editar o texto reconhecido.
6. Confirmar a transcrição.
7. Enviar a redação para análise.
8. Consultar o feedback.

**Resultado esperado:** imagens válidas são aceitas, a transcrição pode ser corrigida antes da análise e o texto confirmado é o conteúdo usado no restante do fluxo.

### Fluxo E — histórico e progresso

1. Consultar redações enviadas.
2. Abrir uma redação antiga.
3. Consultar seu feedback.
4. Verificar o progresso ou a próxima recomendação.

**Resultado esperado:** cada usuário vê somente seus próprios dados e consegue entender o que fez e qual é o próximo passo.

## 3. Critérios mínimos de aceite

O MVP será aceito internamente quando todos os critérios abaixo forem atendidos:

### Funcionais

- [ ] cadastro, login e logout funcionam com validações básicas;
- [ ] rotas privadas bloqueiam usuários não autenticados;
- [ ] onboarding e diagnóstico salvam os dados esperados;
- [ ] redação digitada pode ser criada, salva, retomada e enviada;
- [ ] imagens JPG e PNG podem ser enviadas dentro do limite definido;
- [ ] arquivos inválidos ou acima do limite geram erro compreensível;
- [ ] OCR produz uma transcrição editável ou informa claramente quando falha;
- [ ] a transcrição precisa ser conferida antes da análise;
- [ ] feedback é exibido para a redação correta;
- [ ] histórico e progresso básico podem ser consultados;
- [ ] um usuário não consegue consultar dados de outro usuário.

### Qualidade e experiência

- [ ] os fluxos principais não possuem erro bloqueador conhecido;
- [ ] loading, erro, vazio e sucesso possuem estados compreensíveis;
- [ ] formulários exibem mensagens de validação próximas ao campo relevante;
- [ ] a navegação principal pode ser feita sem depender exclusivamente do mouse;
- [ ] o conteúdo permanece utilizável em desktop e mobile;
- [ ] o sistema não expõe segredos, tokens ou dados de teste na interface;
- [ ] as instruções para executar o projeto estão atualizadas.

### Evidências

- [ ] cada fluxo A–E foi executado e registrado;
- [ ] problemas encontrados possuem severidade, descrição e status;
- [ ] existe pelo menos uma evidência visual ou registro de execução por fluxo;
- [ ] decisões e limitações conhecidas estão documentadas;
- [ ] uma nova execução limpa consegue reproduzir o resultado.

## 4. Classificação de problemas

| Severidade | Definição | Regra para aceite |
|---|---|---|
| Bloqueador | impede cadastro, acesso, envio, análise ou consulta dos dados | nenhum permitido |
| Alto | fluxo funciona apenas parcialmente ou pode causar perda de dados | corrigir antes da apresentação |
| Médio | problema relevante, mas há alternativa de uso | documentar e priorizar |
| Baixo | ajuste visual, texto ou melhoria sem impacto no fluxo | pode ficar para depois |

## 5. Resultado da validação

Ao finalizar, preencher:

- **Data:** a definir
- **Versão:** a definir
- **Ambiente:** a definir
- **Responsável:** desenvolvedor
- **Fluxos executados:** a preencher
- **Bloqueadores encontrados:** a preencher
- **Decisão:** aprovado / aprovado com ressalvas / reprovado
- **Próximas ações:** a preencher
