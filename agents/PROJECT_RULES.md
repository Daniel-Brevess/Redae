# Redaê — Regras de Desenvolvimento para Agentes

## 1. Regra principal

Todo agente deve ler este arquivo antes de modificar qualquer código do projeto.

O agente deve entender o estado atual do projeto antes de fazer alterações.

Nunca assumir que uma funcionalidade, arquitetura ou requisito existe sem verificar primeiro.

---

## 2. Princípios gerais

- Priorizar simplicidade.
- Evitar overengineering.
- Não criar abstrações sem necessidade.
- Não implementar funcionalidades não solicitadas.
- Não inventar requisitos.
- Não alterar código fora do escopo da tarefa.
- Reutilizar código existente quando apropriado.
- Preferir soluções claras e fáceis de manter.
- Manter responsabilidades bem definidas.
- Evitar duplicação desnecessária.
- Manter consistência com os padrões existentes no projeto.
- Fazer alterações pequenas e controladas.
- Não introduzir tecnologias novas sem justificativa.

Regra fundamental:

> Se uma solução simples resolve o problema, não criar uma solução complexa.

---

## 3. Segurança

Esta seção deve ser tratada como prioridade máxima.

O agente NUNCA deve:

- colocar API keys no código;
- colocar passwords no código;
- colocar JWT secrets no código;
- colocar tokens no Git;
- colocar credenciais do Oracle no Git;
- colocar credenciais do Resend no Git;
- colocar credenciais da Azure no Git;
- criar arquivos `.env` contendo secrets reais;
- publicar certificados privados;
- publicar arquivos `.pem`, `.key` ou equivalentes;
- expor tokens em logs;
- expor passwords em logs;
- expor dados sensíveis em respostas da API.

Utilizar variáveis de ambiente para configurações sensíveis.

Nunca modificar `.gitignore` para permitir a entrada de secrets.

Nunca remover mecanismos de segurança simplesmente para fazer algo funcionar.

Se um secret aparecer acidentalmente no código:

1. interromper a alteração;
2. informar o problema;
3. recomendar rotação/revogação do secret;
4. não continuar tratando o secret como se fosse seguro.

---

## 4. Dados de usuários

O Redaê poderá trabalhar com:

- nome;
- e-mail;
- redações;
- fotografias;
- histórico de desempenho;
- dados relacionados à preparação para o ENEM.

Agentes nunca devem:

- colocar dados reais de usuários no repositório;
- utilizar redações reais como fixtures;
- colocar fotos reais no projeto;
- colocar e-mails reais em seeds;
- colocar informações pessoais em testes;
- registrar dados sensíveis em logs.

Para testes e demonstrações, utilizar dados fictícios.

---

## 5. Git e GitHub

Estabeleça boas práticas para Git.

Commits devem ser claros e representar uma alteração lógica.

Preferir Conventional Commits quando apropriado:

```text
feat:
fix:
refactor:
test:
docs:
chore:
build:
ci:
```

Evitar commits como:

- "teste"
- "coisas"
- "mudanças"
- "final"
- "agora vai"

Nunca fazer commit de:

- `.env`;
- credentials;
- secrets;
- logs;
- dumps;
- arquivos temporários;
- `node_modules`;
- `target`;
- arquivos gerados desnecessários.

Antes de considerar uma alteração concluída, verificar o `git diff`.

Não alterar o histórico do Git de maneira destrutiva sem autorização explícita.

---

## 6. Branches e Pull Requests

Preferir branches específicas para alterações:

- `feature/*`;
- `fix/*`;
- `refactor/*`;
- `docs/*`;
- `chore/*`.

Evitar trabalhar diretamente na branch principal quando a alteração for significativa.

Pull Requests devem explicar:

- o que foi alterado;
- por que foi alterado;
- como foi testado;
- possíveis impactos.

---

## 7. Backend — Java/Spring Boot

O backend utiliza:

- Java 21;
- Spring Boot;
- Maven;
- Spring Web;
- Spring Data JPA;
- Hibernate;
- Spring Security;
- JWT;
- Bean Validation;
- Oracle;
- OpenAPI/Swagger.

Regras:

- seguir convenções idiomáticas do Java;
- utilizar nomes claros;
- evitar métodos excessivamente grandes;
- evitar classes com responsabilidades demais;
- validar dados recebidos pela API;
- não confiar no frontend para segurança;
- manter regras de negócio no backend;
- não acessar banco diretamente a partir de controllers;
- não colocar lógica de negócio em DTOs sem necessidade;
- utilizar tratamento adequado de exceções;
- evitar retornar stack traces para usuários;
- não expor informações internas da aplicação.

Não criar arquitetura complexa sem necessidade.

---

## 8. Arquitetura

O projeto inicialmente utiliza um monólito modular.

Não criar microservices apenas por preferência.

Não adicionar:

- Kafka;
- Redis;
- Kubernetes;
- service mesh;
- event sourcing;
- CQRS;
- arquitetura distribuída;

sem uma necessidade real e uma decisão documentada.

Não criar Clean Architecture, Hexagonal Architecture ou DDD complexo apenas para aumentar a quantidade de camadas.

A arquitetura deve evoluir conforme as necessidades reais do produto.

---

## 9. Banco de dados

O banco principal planejado é Oracle.

Regras:

- nunca utilizar banco de produção durante testes;
- nunca colocar credenciais no código;
- nunca versionar dumps com dados reais;
- utilizar migrations quando o projeto adotar uma ferramenta para isso;
- criar índices apenas quando houver justificativa;
- evitar queries desnecessariamente complexas;
- considerar performance sem fazer otimizações prematuras;
- respeitar integridade referencial;
- utilizar transações corretamente.

---

## 10. Frontend

O frontend utiliza:

- React;
- TypeScript;
- Vite;
- Tailwind CSS.

Regras:

- componentes devem possuir responsabilidades claras;
- evitar componentes gigantes;
- evitar duplicação;
- tipar dados importantes com TypeScript;
- não utilizar `any` sem justificativa;
- manter chamadas de API organizadas;
- não colocar secrets no frontend;
- não colocar API keys privadas no código React;
- não confiar no frontend para autorização;
- manter acessibilidade em mente;
- manter responsividade;
- respeitar a identidade visual do Redaê.

---

## 11. Mobile e Desktop

O Redaê possui duas experiências principais.

Mobile:

- captura de redação;
- foto pela câmera;
- escolha de foto da galeria;
- acompanhamento da evolução.

Desktop:

- escrita;
- análise;
- treinamento;
- acompanhamento.

Não criar duas aplicações independentes.

O frontend deve ser responsivo e adaptar a experiência ao dispositivo.

---

## 12. Imagens de redações

O produto permitirá:

1. tirar foto da redação;
2. escolher foto da galeria.

Não implementar outros formatos de upload sem requisito explícito.

Imagens devem ser tratadas com segurança.

Não armazenar imagens indefinidamente sem necessidade.

Não colocar imagens de usuários no GitHub.

---

## 13. Inteligência Artificial

Quando a IA for implementada:

- chamadas de IA devem ocorrer pelo backend;
- API keys nunca devem estar no frontend;
- não enviar dados desnecessários para serviços externos;
- tratar erros de serviços externos;
- validar respostas da IA;
- não confiar cegamente em respostas geradas;
- registrar decisões importantes relacionadas ao uso da IA;
- evitar custos desnecessários;
- não realizar chamadas repetidas sem necessidade.

A IA é uma ferramenta de apoio e não deve ser apresentada automaticamente como autoridade absoluta.

---

## 14. APIs

As APIs devem:

- utilizar HTTP corretamente;
- utilizar status codes apropriados;
- validar entradas;
- possuir contratos claros;
- possuir documentação quando aplicável;
- evitar exposição de dados desnecessários;
- retornar erros consistentes.

Não criar endpoints sem necessidade.

---

## 15. Testes

Alterações relevantes devem possuir testes apropriados.

Backend:

- testes unitários;
- testes de integração quando necessário.

Frontend:

- testes para comportamentos importantes quando apropriado.

Não escrever testes apenas para aumentar cobertura artificialmente.

Testar comportamento real.

---

## 16. Dependências

Antes de adicionar uma dependência:

1. verificar se ela é realmente necessária;
2. verificar se o projeto já possui uma solução equivalente;
3. considerar manutenção e segurança;
4. evitar bibliotecas pequenas para problemas triviais;
5. documentar decisões importantes.

Não adicionar uma biblioteca apenas porque ela é popular.

---

## 17. Docker

Docker deve reproduzir um ambiente consistente.

Não colocar secrets no:

- Dockerfile;
- docker-compose.yml;
- Docker image;
- GitHub Actions.

Utilizar variáveis de ambiente e mecanismos apropriados de configuração.

Evitar containers excessivamente privilegiados.

---

## 18. CI/CD

GitHub Actions deve verificar, quando aplicável:

- build;
- testes;
- qualidade;
- segurança.

Não colocar secrets diretamente nos arquivos YAML.

Utilizar GitHub Secrets.

Uma alteração não deve ser considerada concluída se quebrar o pipeline sem justificativa.

---

## 19. Logs

Logs devem ser úteis, mas nunca devem conter:

- passwords;
- JWTs;
- API keys;
- tokens;
- secrets;
- dados pessoais desnecessários;
- conteúdo privado de usuários sem necessidade.

Nunca imprimir objetos inteiros se eles puderem conter informações sensíveis.

---

## 20. Tratamento de erros

Erros devem ser tratados de forma controlada.

Não retornar:

- stack trace;
- credenciais;
- SQL interno;
- caminhos internos;
- detalhes de infraestrutura;

para o usuário final.

Logs internos podem conter informações técnicas apropriadas, mas sem secrets ou dados sensíveis.

---

## 21. Documentação

Quando uma alteração mudar:

- arquitetura;
- API;
- banco;
- infraestrutura;
- segurança;
- comportamento importante;

a documentação correspondente deve ser atualizada.

Não deixar documentação descrevendo uma arquitetura que não existe.

---

## 22. Uso de agentes de IA

Agentes devem:

1. ler `/agents/PROJECT_RULES.md`;
2. analisar o código existente;
3. entender o escopo;
4. planejar a alteração;
5. implementar somente o necessário;
6. executar testes;
7. verificar o diff;
8. informar o que foi alterado.

O agente não deve:

- apagar código sem necessidade;
- reescrever arquivos inteiros sem motivo;
- alterar arquitetura sem autorização;
- instalar tecnologias aleatórias;
- modificar configurações de segurança para contornar erros;
- criar funcionalidades não solicitadas.

Se houver ambiguidade relevante, o agente deve perguntar antes de tomar uma decisão que possa alterar a arquitetura ou o comportamento do produto.

---

## 23. Regra de escopo

Uma tarefa deve modificar somente aquilo que é necessário para concluí-la.

Exemplo:

Se a tarefa for corrigir um endpoint:

Não reorganizar todo o backend.

Se a tarefa for alterar uma tela:

Não refatorar todo o frontend.

Se a tarefa for criar uma entidade:

Não criar automaticamente todo o sistema relacionado.

Evitar "já que estou aqui" refactors.

---

## 24. Verificação antes de finalizar

Antes de finalizar qualquer tarefa, o agente deve verificar:

- código compila;
- testes relevantes passam;
- frontend faz build quando aplicável;
- backend faz build quando aplicável;
- nenhuma dependência desnecessária foi adicionada;
- nenhum secret foi adicionado;
- nenhum arquivo sensível foi criado;
- documentação necessária foi atualizada;
- Git diff está coerente;
- nenhuma alteração não relacionada foi incluída.

---

## 25. Regra final

O objetivo não é escrever o máximo de código.

O objetivo é escrever:

> código correto, simples, seguro, testável, compreensível e necessário.

Quando houver duas soluções válidas, preferir a que:

1. possui menor complexidade;
2. possui menor superfície de risco;
3. é mais fácil de testar;
4. é mais fácil de manter;
5. está mais alinhada ao projeto existente.

Nunca adicionar complexidade apenas para fazer o projeto parecer mais profissional.

O Redaê deve evoluir de forma incremental e consciente.
