# Histórias de usuário e critérios de aceitação

> **Status:** rascunho para validação  
> **Fase:** 1 — Descoberta e requisitos

## HU-01 — Criar uma conta

**Como** estudante que quer melhorar sua nota no ENEM,  
**quero** criar uma conta,  
**para** acessar minhas redações e avaliações.

**Prioridade:** Must  
**Relacionada:** RF-10

### Critérios de aceitação

- dado um formulário de cadastro, quando informo dados válidos, então minha conta é criada;
- quando informo dados inválidos ou incompletos, então recebo uma mensagem clara;
- quando tento cadastrar um email já utilizado, então sou informado sem criar uma conta duplicada.

## HU-02 — Enviar redação digitada

**Como** estudante,  
**quero** escrever ou colar minha redação,  
**para** enviá-la para avaliação.

**Prioridade:** Must  
**Relacionada:** RF-01

### Critérios de aceitação

- dado o editor aberto, quando insiro texto, então consigo visualizá-lo;
- quando tento enviar sem texto, então o sistema impede o envio e explica o problema;
- quando envio um texto válido, então ele fica disponível para análise.

## HU-03 — Enviar redação por imagem

**Como** estudante que escreveu no papel,  
**quero** enviar imagens da minha redação,  
**para** não precisar transcrever todo o texto manualmente.

**Prioridade:** Must  
**Relacionada:** RF-02

### Critérios de aceitação

- quando envio imagens JPG ou PNG dentro dos limites, então elas são aceitas;
- quando envio formato não suportado, então recebo uma mensagem clara;
- quando ultrapasso a quantidade ou o tamanho permitido, então o envio é bloqueado com a regra informada.

## HU-04 — Conferir transcrição

**Como** estudante que enviou uma imagem,  
**quero** revisar e corrigir a transcrição,  
**para** garantir que a IA avalie o texto correto.

**Prioridade:** Must  
**Relacionada:** RF-03 e RF-04

### Critérios de aceitação

- quando o OCR termina, então consigo visualizar o texto transcrito;
- quando encontro um erro, então consigo editar a transcrição;
- quando confirmo a transcrição, então o texto confirmado é o enviado para avaliação;
- quando o OCR falha, então sou informado e não recebo uma avaliação falsa.

## HU-05 — Receber uma nota

**Como** estudante,  
**quero** receber uma nota na escala do ENEM,  
**para** entender meu resultado geral.

**Prioridade:** Must  
**Relacionada:** RF-06

### Critérios de aceitação

- quando a avaliação termina, então vejo uma nota geral;
- então também vejo as notas das competências C1, C2, C3, C4 e C5;
- a escala da nota é apresentada de forma clara;
- a nota é identificada como estimativa automática, não como nota oficial;
- quando a avaliação falha, então nenhuma nota é apresentada como válida.

## HU-06 — Identificar os erros

**Como** estudante que recebeu uma nota,  
**quero** ver exatamente onde estão meus erros,  
**para** entender o que prejudicou minha redação.

**Prioridade:** Must  
**Relacionada:** RF-07

### Critérios de aceitação

- cada erro é apresentado separadamente;
- cada erro possui um trecho citado da redação;
- cada erro é associado à competência ou critério correspondente;
- se não houver evidência suficiente, o sistema informa a limitação em vez de inventar um trecho.

## HU-07 — Entender o motivo do erro

**Como** estudante,  
**quero** entender por que cada trecho é um problema,  
**para** aprender com a correção.

**Prioridade:** Must  
**Relacionada:** RF-08

### Critérios de aceitação

- cada erro possui uma explicação vinculada ao trecho citado;
- a explicação usa linguagem compreensível;
- a explicação relaciona o problema à competência avaliada;
- uma justificativa genérica não é considerada suficiente.

## HU-08 — Saber como melhorar

**Como** estudante,  
**quero** receber uma orientação prática para cada erro,  
**para** saber o que mudar na próxima redação.

**Prioridade:** Must  
**Relacionada:** RF-09

### Critérios de aceitação

- cada erro relevante possui uma orientação correspondente;
- a orientação diz o que devo observar ou modificar;
- a orientação não se limita a recomendar estudar mais;
- quando a IA não consegue sugerir uma melhoria confiável, isso é informado.

## HU-09 — Consultar uma avaliação anterior

**Como** estudante,  
**quero** consultar uma redação e sua avaliação depois do envio,  
**para** revisar o feedback quando precisar.

**Prioridade:** Must  
**Relacionada:** RF-11

### Critérios de aceitação

- quando uma análise é concluída, então redação, nota e feedback são salvos;
- quando retorno à área de avaliações, então consigo abrir o resultado;
- o resultado exibido corresponde à redação correta;
- não consigo consultar avaliações de outra conta.

## HU-10 — Ser informado sobre a avaliação automática

**Como** estudante,  
**quero** saber que a avaliação foi gerada por IA,  
**para** interpretar o resultado com responsabilidade.

**Prioridade:** Must  
**Relacionada:** RNF-08

### Critérios de aceitação

- a interface informa que a avaliação é automática;
- o sistema não promete equivalência com uma correção oficial;
- limitações ou baixa confiança são mostradas quando aplicável.

