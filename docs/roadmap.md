# Roadmap do Redaê

## Propósito

Este roadmap organiza o desenvolvimento do Redaê como um projeto de produto e de engenharia. A primeira metade prioriza gerenciamento, análise, documentação e decisões técnicas; a segunda transforma essas decisões em software funcional.

O ponto de partida é uma landing page pública em React, TypeScript, Vite e Tailwind CSS, com backend Spring Boot mínimo e sem endpoints de negócio.

## Ordem de trabalho

## Checklist de progresso

O andamento do projeto é acompanhado por caixas de seleção. Não há pontuação: uma fase só deve ser marcada como concluída quando seu gate for atendido.

- [x] Fase 0 — Gerenciamento do projeto
- [x] Fase 1 — Descoberta e requisitos
- [x] Fase 2 — Arquitetura e estrutura da solução
- [x] Fase 3 — Modelagem de domínio e dados
- [x] Fase 4 — Contratos de API e integrações
- [x] Fase 5 — Experiência, interface e acessibilidade
- [ ] Fase 6 — Fundação implementável
- [ ] Fase 7 — Autenticação e área privada
- [ ] Fase 8 — Onboarding, diagnóstico e treino
- [ ] Fase 9 — Qualidade, segurança e operação
- [ ] Fase 10 — Piloto e lançamento

**Legenda:** `[ ]` pendente · `[x]` concluída

O projeto deve avançar por gates. Uma fase só é considerada concluída quando seus documentos foram revisados, suas decisões foram registradas e seus critérios de saída foram atendidos.

### Fase 0 — Gerenciamento do projeto

**Objetivo:** estabelecer como o projeto será conduzido, acompanhado e avaliado.

Documentos e entregas:

- Termo de abertura do projeto.
- Justificativa, objetivo geral e objetivos específicos.
- Escopo inicial e limites do projeto.
- Stakeholders e responsabilidades.
- Premissas, restrições e dependências.
- Cronograma macro do semestre.
- Estrutura analítica do projeto (EAP/WBS).
- Backlog inicial e método de priorização.
- Matriz de riscos com probabilidade, impacto e resposta.
- Critérios de sucesso e indicadores.
- Definição de cerimônias, status report e controle de mudanças.

Gate: o projeto possui escopo, responsáveis, cronograma, riscos e forma de acompanhamento aprovados.

### Fase 1 — Descoberta e requisitos

**Objetivo:** documentar o problema que o Redaê resolve e o comportamento esperado do produto.

Documentos e entregas:

- Visão do produto e proposta de valor.
- Personas ou perfis de usuários.
- Jornada atual e jornada desejada do estudante.
- Mapa de necessidades e dores.
- Requisitos funcionais.
- Requisitos não funcionais: segurança, acessibilidade, desempenho, disponibilidade e privacidade.
- Histórias de usuário com critérios de aceitação.
- Casos de uso dos fluxos principais.
- Priorização MoSCoW ou equivalente.
- Escopo do MVP, fora do escopo e hipóteses a validar.

Fluxos mínimos documentados:

- Acessar a landing page.
- Cadastrar-se.
- Fazer login e logout.
- Completar onboarding.
- Fazer diagnóstico.
- Escolher treino.
- Escrever, confirmar e enviar uma redação.
- Consultar feedback e histórico.

Gate: cada requisito prioritário possui objetivo, ator, regra, critério de aceitação e prioridade.

### Fase 2 — Arquitetura e estrutura da solução

**Objetivo:** definir a estrutura do sistema antes de implementar funcionalidades.

Documentos e decisões:

- Contexto do sistema e limites entre frontend, backend e serviços externos.
- Diagrama de contexto e diagrama de containers.
- Organização interna do monólito modular.
- Responsabilidades dos módulos e regras de dependência.
- Decisão de stack e justificativa das tecnologias.
- Ambientes de desenvolvimento, homologação e produção.
- Estratégia de configuração e variáveis de ambiente.
- Estratégia de autenticação, autorização e gerenciamento de sessão.
- Estratégia de erros, logs, observabilidade e auditoria.
- Estratégia de testes e qualidade.
- ADRs para decisões arquiteturais relevantes.

Diretriz atual: manter Spring Boot como monólito modular no MVP, conforme o ADR 0001, evitando microservices sem necessidade comprovada.

Gate: qualquer pessoa da equipe consegue explicar os principais componentes, seus limites e o caminho de uma requisição.

### Fase 3 — Modelagem de domínio e dados

**Objetivo:** transformar os requisitos em um modelo de domínio consistente e implementável.

Documentos e entregas:

- Glossário de termos do domínio.
- Entidades, atributos e responsabilidades.
- Diagrama entidade-relacionamento.
- Modelo lógico e regras de integridade.
- Dicionário de dados.
- Cardinalidades e relacionamentos.
- Identificação de dados pessoais e sensíveis.
- Regras de retenção, exclusão e anonimização.
- Estratégia de migrações e versionamento do banco.
- Dados de exemplo para desenvolvimento e testes.

Modelo persistente definido:

- Usuário.
- Avaliação.
- Nota por competência.
- Feedback por competência.
- Oferta de crédito.
- Preço de crédito.
- Compra de crédito.
- Transação de crédito.

Redação é entrada temporária; processamento é estrutura técnica efêmera. Diagnóstico, exercícios, rascunhos, plano de estudo e registro de progresso ficam fora do MVP atual.

Gate: as entidades suportam os fluxos do MVP sem duplicação desnecessária, e cada campo possui tipo, regra e origem definidos.

### Fase 4 — Contratos de API e integrações

**Objetivo:** definir como frontend, backend e serviços externos irão se comunicar.

Documentos e entregas:

- Especificação OpenAPI.
- Padrão de URLs, métodos e códigos HTTP.
- Formato de requests e responses.
- Paginação, filtros e ordenação.
- Modelo padrão de erros.
- Regras de autenticação por endpoint.
- Versionamento da API.
- Exemplos de chamadas e respostas.
- Estratégia de idempotência e concorrência quando necessário.
- Contratos para email, recuperação de senha e notificações.
 
Endpoints iniciais a especificar:

- Cadastro.
- Login.
- Logout.
- Usuário atual.
- Atualização de perfil.
- Diagnóstico.
- Temas e exercícios.
- Rascunhos e redações.
- Feedback e progresso.

Gate: frontend e backend conseguem trabalhar de forma independente usando o mesmo contrato.

### Fase 5 — Experiência, interface e acessibilidade

**Objetivo:** documentar a experiência antes de expandir a implementação visual.

Documentos e entregas:

- Fluxos de navegação.
- Contratos das telas principais, sem wireframes formais nesta etapa.
- Design direction e tokens visuais.
- Componentes reutilizáveis e estados.
- Estados de loading, erro, vazio e sucesso.
- Regras de responsividade.
- Critérios de acessibilidade e navegação por teclado.
- Textos de interface e mensagens de validação.
- Critérios de usabilidade para teste com estudantes.

Gate: cada tela prioritária possui objetivo, fluxo, estados, textos principais e critérios de aceitação visual. Um protótipo focado será criado depois da fundação para validação antes do desenvolvimento completo.

### Fase 6 — Fundação implementável

**Objetivo:** preparar o repositório para o desenvolvimento organizado.

Entregas:

- Estrutura de módulos do frontend e backend.
- Configuração de ambientes.
- Banco de desenvolvimento e migrações iniciais.
- Pipeline de CI com typecheck, testes e build.
- Padrões de lint e formatação.
- Tratamento base de erros.
- Componentes de layout, formulário e feedback.
- Convenções de commits, branches e pull requests.
- Templates de issue, tarefa, decisão e retrospectiva.

Gate: uma nova pessoa consegue instalar, executar, testar e entender o projeto seguindo a documentação.

### Fase 7 — Autenticação e área privada

**Objetivo:** implementar a primeira capacidade completa de ponta a ponta.

Entregas:

- Cadastro com nome, email, senha e confirmação.
- Login com email e senha.
- Hash seguro de senhas.
- JWT, expiração e logout.
- Validações no frontend e backend.
- Rotas privadas.
- Perfil básico do usuário.
- Confirmação de email e recuperação de senha, usando Resend quando o contrato estiver definido.

Gate: o usuário consegue criar conta, autenticar-se, sair e acessar somente seus próprios dados.

### Fase 8 — Onboarding, diagnóstico e treino

**Objetivo:** entregar o núcleo funcional do MVP.

Entregas:

- Onboarding com objetivo e disponibilidade de estudo.
- Diagnóstico inicial.
- Catálogo de temas e exercícios.
- Editor de redação.
- Confirmação e envio de redações; rascunhos manuais ficam fora do MVP.
- Envio de redação.
- Histórico de atividades.
- Feedback estruturado por competências do ENEM.
- Progresso básico e próxima recomendação.

Gate: um estudante consegue entrar no produto, receber um próximo passo, realizar um treino e consultar seu feedback.

### Fase 9 — Qualidade, segurança e operação

**Objetivo:** preparar o MVP para usuários reais.

Entregas:

- Testes unitários, integração e ponta a ponta dos fluxos críticos.
- Testes de contrato da API.
- Testes de acessibilidade.
- Revisão de segurança e proteção contra abuso.
- Logs estruturados e monitoramento.
- Backup e restauração do banco.
- Política de privacidade, termos de uso e revisão de LGPD.
- Teste de carga básico.
- Plano de incidentes e rollback.
- Deploy em homologação.

Gate: os riscos críticos possuem mitigação, os fluxos principais têm cobertura de testes e o sistema pode ser observado em execução.

### Fase 10 — Piloto e lançamento

**Objetivo:** validar o produto com usuários e decidir a evolução pós-MVP.

Entregas:

- Beta fechado com estudantes.
- Roteiro de teste e formulário de feedback.
- Registro de problemas e hipóteses aprendidas.
- Análise das métricas de ativação e retenção.
- Correções de alta prioridade.
- Deploy de produção no frontend e backend.
- Plano de suporte e comunicação.
- Relatório de encerramento do MVP.
- Roadmap pós-MVP revisado com base em evidências.

Gate: existe evidência de uso real, os problemas críticos foram tratados e a próxima etapa foi priorizada.

## Pacote de documentação do semestre

Ao final do ciclo acadêmico, o projeto deve possuir pelo menos:

- Termo de abertura.
- Visão do produto.
- Escopo e fora do escopo.
- EAP/WBS e cronograma.
- Matriz de stakeholders.
- Matriz de riscos.
- Requisitos e histórias de usuário.
- Casos de uso e critérios de aceitação.
- Diagramas de arquitetura.
- ADRs.
- Glossário de domínio.
- Diagrama de dados e dicionário de dados.
- OpenAPI da primeira versão.
- Documentação visual e contratos das telas.
- Plano de testes.
- Plano de implantação e operação.
- Registro de decisões, mudanças e retrospectivas.
- Relatório de validação do MVP.

## Fora do primeiro MVP

- Microservices.
- Aplicativo mobile nativo.
- Marketplace de professores.
- Gamificação complexa, ranking público e recompensas financeiras.
- Integrações externas não essenciais.
- Personalização avançada por IA antes de existir histórico suficiente para avaliar qualidade.

## Decisões que precisam ser validadas

- A correção será manual, automática ou híbrida?
- Qual banco será usado no primeiro ambiente de produção?
- O cadastro exigirá confirmação de email antes do primeiro acesso?
- Quais competências e critérios formam o feedback inicial?
- O produto será gratuito no beta? Haverá limite de redações?
- Quais métricas indicarão que o MVP está pronto para lançamento?

## Métricas iniciais de sucesso

- Percentual de entregas documentais aprovadas por fase.
- Requisitos prioritários com critérios de aceitação definidos.
- Riscos críticos com plano de resposta.
- Conversão da landing page para cadastro.
- Percentual de cadastros que concluem o onboarding.
- Percentual de usuários que enviam a primeira redação.
- Retenção semanal.
- Frequência de prática por usuário.
- Percepção de utilidade do feedback.

## Próximo ciclo recomendado

1. Criar o termo de abertura e a EAP do semestre.
2. Fechar escopo, personas, requisitos e critérios de aceitação do MVP.
3. Registrar as primeiras ADRs de arquitetura e stack.
4. Criar o glossário, modelo de domínio, DER e dicionário de dados.
5. Especificar a API de autenticação em OpenAPI.
6. Revisar os estados e contratos visuais dos cards atuais de login e cadastro.

Cada fase deve terminar com uma demonstração ou revisão documental, registro das decisões tomadas e atualização deste roadmap.
