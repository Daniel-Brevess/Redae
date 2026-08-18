# Requisitos não funcionais do MVP

> **Status:** rascunho para validação  
> **Fase:** 1 — Descoberta e requisitos

Estes requisitos definem o nível mínimo de qualidade necessário para o MVP funcionar com segurança e ser compreensível para estudantes. Eles não estabelecem metas de escala, disponibilidade ou infraestrutura de produção avançada.

## RNF-01 — Usabilidade básica

O estudante deve conseguir entender o que fazer em cada etapa sem instruções externas.

**Critérios de aceitação:**

- ações principais possuem textos claros;
- erros informam o que aconteceu e como tentar novamente;
- estados de carregamento, sucesso, vazio e falha são apresentados;
- o resultado da avaliação destaca nota, erro, explicação e como melhorar.

## RNF-02 — Responsividade

As telas principais devem funcionar em desktop e em uma largura de tela mobile.

**Critérios de aceitação:**

- conteúdo não fica cortado horizontalmente;
- editor e feedback permanecem utilizáveis em tela pequena;
- envio de imagem pode ser realizado em dispositivo móvel;
- botões e campos continuam acessíveis ao toque.

## RNF-03 — Acessibilidade básica

As interfaces principais devem seguir práticas básicas de acessibilidade.

**Critérios de aceitação:**

- campos possuem rótulos associados;
- navegação principal funciona com teclado;
- foco do teclado permanece visível;
- imagens informativas possuem texto alternativo;
- mensagens de erro não dependem apenas de cor;
- contraste e tamanho de texto permitem leitura confortável.

## RNF-04 — Desempenho percebido

O sistema deve responder rapidamente nas operações comuns e informar o progresso nas operações demoradas.

**Critérios de aceitação:**

- telas comuns exibem conteúdo ou estado de carregamento em até 3 segundos em ambiente de referência;
- operações de OCR e análise por IA exibem progresso, sem deixar o estudante sem resposta;
- o estudante não precisa reenviar uma redação apenas por aguardar o processamento;
- erros de timeout podem ser identificados e tratados.

O tempo de resposta da IA será medido separadamente, pois depende do serviço externo utilizado.

## RNF-05 — Segurança de acesso

Os dados de cada estudante devem ser protegidos contra acesso não autorizado.

**Critérios de aceitação:**

- senhas nunca são armazenadas em texto puro;
- áreas privadas exigem autenticação;
- cada consulta valida o usuário proprietário do dado;
- entradas do usuário são validadas no frontend e backend;
- segredos e chaves não ficam no código ou no repositório;
- ambiente de produção utiliza conexão segura.

## RNF-06 — Privacidade

O MVP deve coletar e armazenar somente os dados necessários para oferecer a avaliação.

**Critérios de aceitação:**

- dados coletados possuem finalidade identificada;
- o estudante é informado de que a redação será processada por IA;
- o sistema documenta quais dados são enviados ao provedor de IA;
- redações e resultados ficam vinculados somente à conta correta;
- não são usados dados reais em desenvolvimento sem autorização;
- existe um procedimento definido para excluir uma conta e suas redações antes do piloto.

## RNF-07 — Confiabilidade do resultado

Falhas de OCR, IA ou rede não devem produzir um resultado apresentado como válido.

**Critérios de aceitação:**

- falha de processamento gera estado de erro explícito;
- análise incompleta não recebe status de concluída;
- o texto original e o texto revisado não são substituídos silenciosamente;
- o sistema não exibe nota quando não possui avaliação suficiente;
- o estudante pode tentar novamente sem duplicar a redação indevidamente.

## RNF-08 — Transparência da avaliação

O estudante deve saber que a nota e o feedback são produzidos automaticamente e podem conter limitações.

**Critérios de aceitação:**

- a avaliação automática é identificada na interface;
- o sistema não promete equivalência com uma correção oficial;
- limitações ou baixa confiança são apresentadas quando aplicável;
- cada apontamento de erro possui evidência ou informa que não foi possível localizar uma evidência.

## RNF-09 — Manutenibilidade básica

O projeto deve permanecer compreensível e executável pelo próprio desenvolvedor após a primeira semana de implementação.

**Critérios de aceitação:**

- instruções de execução estão atualizadas;
- erros relevantes são registrados com contexto suficiente;
- mudanças importantes ficam documentadas;
- o frontend e o backend podem ser testados e gerados seguindo o README.

## RNF-10 — Disponibilidade no escopo do MVP

O sistema deve estar disponível durante demonstrações e validações planejadas, sem compromisso de SLA público.

**Critérios de aceitação:**

- frontend e backend iniciam seguindo a documentação;
- existe uma forma simples de verificar se o backend está ativo;
- falha de um serviço apresenta mensagem compreensível;
- não há promessa de disponibilidade contínua antes da definição do ambiente de produção.

## Fora do escopo não funcional inicial

- alta disponibilidade;
- escalabilidade horizontal;
- suporte a grandes volumes de usuários;
- acordo formal de nível de serviço;
- observabilidade avançada;
- auditoria completa;
- testes de carga abrangentes.

