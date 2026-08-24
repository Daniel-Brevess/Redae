# Requisitos funcionais do MVP

> **Status:** rascunho para validação  
> **Fase:** 1 — Descoberta e requisitos

## Objetivo funcional

O Redaê deve permitir que um estudante envie uma redação do ENEM, receba uma nota, identifique exatamente onde errou, entenda o motivo do erro e saiba como melhorar.

## 1. Requisitos essenciais

### RF-01 — Receber redação digitada

O sistema deve permitir que o estudante escreva ou cole o texto de uma redação.

**Critérios de aceitação:**

- o estudante consegue inserir texto no editor;
- o sistema impede o envio quando não há conteúdo;
- o texto enviado é preservado sem alteração indevida.

### RF-02 — Receber redação por imagem

O sistema deve permitir que o estudante envie imagens da redação manuscrita ou impressa.

**Critérios de aceitação:**

- o sistema aceita JPG e PNG;
- o sistema aceita até 5 imagens por redação;
- cada imagem pode ter até 8 MB;
- o conjunto pode ter até 32 MB;
- formatos ou tamanhos inválidos geram mensagem compreensível.

### RF-03 — Transcrever imagens

O sistema deve transformar as imagens enviadas em um texto para análise.

**Critérios de aceitação:**

- o estudante consegue visualizar a transcrição;
- uma falha de transcrição é informada claramente;
- o sistema não envia a redação para análise sem uma transcrição disponível.

### RF-04 — Permitir conferência da transcrição

O sistema deve permitir que o estudante revise e edite o texto transcrito antes da análise.

**Critérios de aceitação:**

- o texto transcrito pode ser editado;
- o estudante consegue confirmar a versão revisada;
- a análise utiliza o texto confirmado pelo estudante.

### RF-05 — Enviar redação para análise

O sistema deve permitir que o estudante envie o texto confirmado para análise.

**Critérios de aceitação:**

- o envio só ocorre após o texto estar disponível;
- o sistema informa que a análise está em andamento;
- o estudante recebe uma resposta de sucesso ou erro compreensível.

### RF-06 — Apresentar nota

O sistema deve apresentar uma nota para a redação analisada usando a estrutura de avaliação do ENEM, organizada pelas competências C1, C2, C3, C4 e C5.

**Critérios de aceitação:**

- a nota fica associada à redação correta;
- a nota geral e as notas por competência utilizam a escala do ENEM;
- a escala da nota é informada ao estudante;
- a nota é identificada como uma estimativa produzida pelo sistema, não como uma nota oficial;
- uma falha de análise não gera uma nota falsa.

### RF-07 — Identificar onde está o erro

O sistema deve apontar os erros encontrados na redação por meio de trechos citados e associá-los à competência ou critério correspondente.

**Critérios de aceitação:**

- cada erro aparece separado dos demais;
- cada erro contém um trecho citado da redação;
- cada erro é associado a uma competência ou critério;
- quando não for possível citar um trecho adequado, o sistema informa essa limitação.

### RF-08 — Explicar o erro

O sistema deve explicar por que cada erro prejudica a redação ou a nota.

**Critérios de aceitação:**

- a explicação utiliza linguagem compreensível para um estudante do ensino médio;
- a explicação está associada ao erro correspondente;
- explicações genéricas sem relação com a redação não são consideradas suficientes.

### RF-09 — Orientar como melhorar

O sistema deve apresentar uma orientação prática para corrigir cada erro identificado.

**Critérios de aceitação:**

- cada erro relevante possui uma orientação correspondente;
- a orientação descreve o que o estudante deve mudar ou observar;
- a orientação não se limita a recomendar “estudar mais”.

## 2. Requisitos de suporte mínimo

### RF-10 — Criar conta e acessar resultados

O sistema deve permitir que o estudante crie uma conta e acesse suas próprias redações e análises.

**Critérios de aceitação:**

- o estudante consegue criar uma conta com dados válidos;
- o estudante consegue entrar e sair da conta;
- um estudante não consegue acessar redações de outra conta.

### RF-11 — Confirmar redação e salvar análise

O estudante escreve ou cola a redação no editor e decide se deseja confirmar o envio. Ao confirmar, o sistema deve persistir o texto enviado, a nota e o feedback correspondente. Não haverá salvamento manual de rascunho como etapa do MVP.

**Critérios de aceitação:**

- o resultado pode ser consultado novamente;
- o texto analisado, a nota e o feedback permanecem associados;
- antes da confirmação, o texto pode permanecer somente no estado da sessão do editor;
- uma falha informada não faz o estudante acreditar que o resultado foi salvo.

## 3. Fora desta primeira especificação

O feedback e a avaliação serão gerados automaticamente por IA. Os resultados devem ser apresentados como estimativas e suas limitações devem ser informadas ao estudante.

Os itens abaixo não serão transformados em requisitos até que o objetivo central esteja validado:

- ranking;
- comunidade;
- gamificação;
- plano de estudo completo;
- recomendações avançadas;
- correção oficial ou garantia de nota do ENEM;
- suporte a vestibulares diferentes do ENEM.

## 4. Decisões ainda necessárias

1. Definir os critérios detalhados de cada competência C1–C5.
2. Definir o formato do prompt e da resposta estruturada da IA.
3. Definir como lidar com análises que apresentarem baixa confiança ou falha.
4. Definir exemplos de avaliações esperadas para testar a qualidade do feedback.
