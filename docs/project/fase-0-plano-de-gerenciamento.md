# Fase 0 — Plano de gerenciamento do projeto

> **Status:** rascunho para validação  
> **Última revisão:** 2026-08-18

Este documento é o ponto de partida da fase 0 do Redaê. Ele organiza as decisões de gestão necessárias para conduzir o semestre e separa o que já está definido do que ainda precisa ser confirmado.

## 1. Termo de abertura

### 1.1 Projeto

**Redaê — plataforma de treinamento e evolução para redação do ENEM.**

### 1.2 Justificativa

Estudantes praticam redação, mas frequentemente não conseguem identificar com clareza onde perdem pontos, transformar feedback em ações práticas ou acompanhar a própria evolução. O projeto pretende investigar e construir uma experiência que conecte escrita, análise, diagnóstico, treino e evolução.

### 1.3 Objetivo geral

Definir, construir e validar um MVP web que ajude estudantes a praticar redação do ENEM e compreender como melhorar.

### 1.4 Objetivos específicos

- documentar o problema, os usuários e os requisitos prioritários;
- definir arquitetura, domínio, dados, API e experiência antes da expansão do código;
- entregar um fluxo funcional de cadastro, acesso, diagnóstico, treino, envio e feedback;
- validar o MVP com estudantes e usar evidências para orientar o próximo ciclo;
- manter decisões, riscos, mudanças e resultados rastreáveis.

### 1.5 Resultado esperado

Ao final do ciclo, o projeto deverá ter documentação revisada, um MVP web executável e evidências iniciais de uso. O produto não será considerado concluído apenas pela existência de telas ou código.

## 2. Escopo inicial

### Incluído

- landing page pública;
- cadastro, login, logout e perfil básico;
- onboarding com objetivo e disponibilidade de estudo;
- diagnóstico inicial;
- temas e exercícios;
- escrita, salvamento de rascunho e envio de redação;
- entrada de redação por texto e por imagem;
- OCR/transcrição da imagem e conferência pelo estudante antes da análise;
- feedback estruturado e histórico de evolução;
- frontend web e backend Spring Boot como monólito modular;
- documentação de produto, arquitetura, dados, API, interface, testes e operação.

### Fora do escopo inicial

- microservices;
- aplicativo mobile nativo;
- marketplace de professores;
- gamificação complexa, ranking público e recompensas financeiras;
- integrações externas não essenciais;
- personalização avançada por IA sem histórico suficiente para avaliar sua qualidade.

O MVP incluirá as duas formas de entrada. A qualidade e o limite operacional do OCR deverão ser validados durante a implementação. Como limite inicial, uma redação poderá conter até 5 imagens JPG ou PNG, com até 8 MB por imagem e 32 MB no total.

## 3. Pessoas e responsabilidades

| Papel | Responsabilidade | Responsável atual |
|---|---|---|
| Sponsor/dono do produto | visão, prioridades e aprovação dos gates | Desenvolvedor |
| Gestão do projeto | plano, cronograma, riscos e mudanças | Desenvolvedor |
| Produto e pesquisa | problema, usuários, requisitos e validação | Desenvolvedor |
| Engenharia | arquitetura, implementação, testes e operação | Desenvolvedor |
| Design e acessibilidade | fluxos, interface, conteúdo e critérios visuais | Desenvolvedor |
| Usuários-piloto | testar o produto e fornecer feedback | Ainda não confirmados |

O projeto será desenvolvido individualmente. Neste momento não há usuários confirmados para o piloto; a primeira validação deverá combinar testes próprios, revisão dos fluxos e, caso seja possível, testes exploratórios com voluntários.

## 4. Premissas, restrições e dependências

### Premissas

- o projeto será conduzido como trabalho de semestre, com entrega em novembro;
- haverá dedicação diária ao projeto;
- a capacidade planejada é de aproximadamente 4 horas de desenvolvimento por dia;
- ferramentas de IA serão usadas para acelerar análise, documentação e implementação;
- o MVP será web e priorizará a responsividade, tendo um bom uso no desktop quanto no mobile;
- a stack atual permanece o ponto de partida;
- o feedback inicial pode ser estruturado sem depender de integrações externas;
- decisões relevantes serão registradas antes da implementação correspondente.

### Restrições

- prazo acadêmico do semestre;
- projeto individual, com disponibilidade planejada de 4 horas por dia;
- orçamento e serviços de produção ainda não confirmados;
- qualidade de correção automática ainda não validada;
- dados de estudantes exigem atenção a privacidade e LGPD.

### Dependências

- confirmação do prazo final exato da entrega;
- validação do escopo do MVP;
- decisão sobre correção manual, automática ou híbrida;
- decisão sobre banco de produção;
- acesso a estudantes para validação;
- definição de critérios de sucesso mensuráveis.

## 5. EAP resumida

1. **Gerenciamento**
   1.1 termo de abertura  
   1.2 cronograma e marcos  
   1.3 EAP e backlog  
   1.4 riscos, status report e mudanças
2. **Descoberta e requisitos**
   2.1 visão e proposta de valor  
   2.2 usuários, dores e jornadas  
   2.3 requisitos, histórias e critérios de aceitação  
   2.4 escopo final do MVP
3. **Arquitetura e dados**
   3.1 contexto e containers  
   3.2 módulos e decisões técnicas  
   3.3 domínio, DER e dicionário de dados  
   3.4 contratos de API
4. **Experiência**
   4.1 fluxos  
   4.2 wireframes  
   4.3 componentes, estados e acessibilidade
5. **Implementação**
   5.1 fundação e CI  
   5.2 autenticação e área privada  
   5.3 onboarding, diagnóstico e treino  
   5.4 feedback e progresso
6. **Qualidade e validação**
   6.1 testes e segurança  
   6.2 operação e deploy  
   6.3 piloto, métricas e relatório final

## 6. Backlog inicial e priorização

O backlog será priorizado por **valor para validar o produto**, **dependência** e **risco**, usando MoSCoW como linguagem simples de alinhamento.

| Prioridade | Item | Fase de execução |
|---|---|---|
| Must | fechar problema, usuário e escopo do MVP | 1 |
| Must | definir requisitos dos fluxos principais | 1 |
| Must | arquitetura, dados e contrato de autenticação | 2–4 |
| Must | cadastro, login, área privada e perfil | 6–7 |
| Must | onboarding, diagnóstico, treino, envio e feedback | 8 |
| Should | histórico e progresso básico | 8 |
| Should | piloto com estudantes e métricas | 10 |
| Must | entrada por imagem, OCR, transcrição e conferência | 8 |
| Won't agora | comunidade, ranking e gamificação complexa | pós-MVP |

## 7. Cronograma macro

O cronograma abaixo usa um ciclo intensivo de sete dias para a primeira versão integrada. Novembro permanece como prazo de encerramento acadêmico para correções, testes, validação e apresentação.

| Marco | Janela proposta | Saída |
|---|---:|---|
| Fase 0 — gerenciamento | dia 1 | gate de gestão aprovado |
| Fase 1 — descoberta | dia 2 | requisitos prioritários aprovados |
| Fases 2–4 — arquitetura, dados e API | dia 3 | contratos e decisões técnicas aprovados |
| Fase 5 — experiência | dia 4 | fluxos e wireframes aprovados |
| Fases 6–8 — fundação e núcleo do MVP | dias 5–6 | fluxo funcional ponta a ponta |
| Fases 9–10 — qualidade e piloto | dia 7 em diante | relatório do piloto e próximo roadmap |

O dia exato da entrega em novembro ainda será confirmado. A primeira semana produzirá a versão integrada; o período restante será reservado para reduzir riscos e comprovar o funcionamento com usuários.

## 8. Riscos iniciais

| Risco | Prob. | Impacto | Resposta inicial | Dono |
|---|---|---|---|---|
| escopo crescer além do prazo | alta | alto | congelar MVP e controlar mudanças | Desenvolvedor |
| velocidade da IA gerar decisões não validadas | alta | alto | revisar saídas, testar fluxos e registrar decisões | Desenvolvedor |
| falta de usuários para validação | alta | alto | usar testes próprios e buscar voluntários sem bloquear a primeira versão | Desenvolvedor |
| correção automática não ser confiável | alta | alto | definir limites, revisar amostras e explicitar incertezas | Desenvolvedor |
| decisões técnicas serem adiadas | média | médio | gates documentais antes da implementação | Desenvolvedor |
| tratamento inadequado de dados pessoais | média | alto | mapear dados e privacidade antes do piloto | Desenvolvedor |

## 9. Acompanhamento e mudanças

### Cerimônias propostas

- **planejamento semanal:** escolher o próximo resultado verificável;
- **check-in curto:** atualizar feito, próximo passo e bloqueios;
- **revisão de gate:** aprovar documentos e decisões de cada fase;
- **retrospectiva quinzenal:** registrar o que melhorar no processo.

### Status report

Cada atualização deverá registrar: período, objetivo, concluído, próximo passo, bloqueios, riscos alterados e decisões necessárias.

### Controle de mudanças

Qualquer alteração relevante de escopo, prazo, custo, arquitetura ou critério de sucesso deve registrar: proposta, motivo, impacto, decisão, responsável e data. Mudanças que comprometam o gate devem ser aprovadas pelo dono do produto.

## 10. Critérios de sucesso da fase 0

O gate será considerado pronto quando houver:

- escopo incluído e fora do escopo aprovados;
- responsáveis e disponibilidade confirmados;
- cronograma com datas e marcos do semestre;
- EAP e backlog inicial priorizados;
- matriz de riscos com donos e respostas;
- cadência de acompanhamento definida;
- registro das decisões pendentes e do próximo passo da fase 1.

## 11. Decisões para nossa próxima conversa

1. Confirmar o dia exato da entrega em novembro.
2. Buscar voluntários para validação, sem tratar isso como dependência do desenvolvimento inicial.
3. Revisar o limite inicial de imagens após os primeiros testes de OCR.
4. Criar o roteiro de validação interna e os critérios mínimos de aceite.

O roteiro foi criado em [`roteiro-validacao-mvp.md`](roteiro-validacao-mvp.md).
