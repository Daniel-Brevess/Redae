# Casos de uso dos fluxos principais

> **Status:** versão inicial  
> **Fase:** 1 — Descoberta e requisitos

## UC-01 — Criar conta e acessar avaliações

**Ator principal:** estudante

**Objetivo:** criar uma conta e acessar suas próprias redações e avaliações.

**Fluxo principal:**

1. O estudante informa seus dados.
2. O sistema valida os dados.
3. O sistema cria a conta.
4. O estudante entra na conta.
5. O sistema libera suas redações e avaliações.

**Exceções:** dados inválidos, email já utilizado ou credenciais incorretas devem gerar mensagens claras.

## UC-02 — Enviar redação digitada

**Ator principal:** estudante

**Objetivo:** enviar um texto para avaliação.

**Fluxo principal:**

1. O estudante abre o editor.
2. Escreve ou cola a redação.
3. O sistema valida a existência do texto.
4. O estudante envia a redação.
5. O sistema registra o texto e inicia a análise.

**Exceções:** texto vazio, falha de envio ou falha de análise devem impedir um resultado apresentado como válido.

## UC-03 — Enviar redação por imagem

**Ator principal:** estudante

**Objetivo:** enviar uma redação manuscrita ou impressa sem transcrevê-la manualmente.

**Fluxo principal:**

1. O estudante seleciona imagens JPG ou PNG.
2. O sistema valida quantidade e tamanho.
3. O sistema executa o OCR.
4. O sistema apresenta a transcrição.
5. O estudante revisa e corrige o texto.
6. O estudante confirma a transcrição.
7. O sistema inicia a análise do texto confirmado.

**Exceções:** formato inválido, limite excedido, imagem ilegível ou falha de OCR devem ser informados claramente.

## UC-04 — Avaliar redação

**Ator principal:** sistema de avaliação por IA
**Ator de apoio:** estudante

**Objetivo:** produzir uma estimativa de nota e feedback seguindo C1–C5.

**Fluxo principal:**

1. O sistema recebe o texto confirmado.
2. O sistema avalia C1, C2, C3, C4 e C5.
3. O sistema atribui notas por competência e nota geral.
4. O sistema identifica erros por trechos citados.
5. O sistema explica cada erro.
6. O sistema orienta como melhorar cada erro.
7. O sistema salva o resultado.
8. O estudante consulta a avaliação.

**Exceções:** baixa confiança, falta de evidência ou falha do serviço de IA devem ser exibidas como limitação, sem inventar nota ou trecho.

## UC-05 — Consultar avaliação

**Ator principal:** estudante

**Objetivo:** revisar uma nota e entender como melhorar.

**Fluxo principal:**

1. O estudante acessa suas avaliações.
2. Seleciona uma redação.
3. Visualiza nota geral e notas C1–C5.
4. Abre os erros por trechos citados.
5. Lê a explicação de cada erro.
6. Consulta a orientação de melhoria.

**Exceção:** o sistema não encontra o resultado ou o estudante não possui permissão; deve informar a situação sem expor dados de terceiros.

