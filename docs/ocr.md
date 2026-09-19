# OCR e transcrição de redações por imagem

## Objetivo deste documento

Este documento registra a decisão de substituir a transcrição baseada em modelo de visão por
OCR local na primeira versão do fluxo de redação por imagem. Ele também documenta os testes
realizados, os problemas encontrados, as alterações implementadas e as limitações conhecidas.

Nenhum dado pessoal presente na imagem de teste é reproduzido aqui. A imagem utilizada permanece
fora do Git e está em `tmp/transcription-tests/` apenas para testes locais.

## Contexto

O fluxo do Redaê permite que o estudante envie uma foto da redação, revise o texto transcrito,
edite o conteúdo e confirme o texto antes de iniciar a avaliação. A edição manual já existia no
frontend e continua sendo obrigatória como etapa de revisão.

O primeiro teste foi realizado com uma folha manuscrita do ENEM armazenada em:

```text
tmp/transcription-tests/15270478730_REDACAO_ENEM2024.JPG
```

O texto de referência usado para comparação está em:

```text
tmp/transcription-tests/redacao-01.txt
```

Esse texto de referência não é uma fixture versionada e não deve ser adicionado ao Git.

## Testes com modelo de visão

### Prompt inicial

O prompt inicial era equivalente a:

```text
Transcreva somente a redação manuscrita presente na imagem. Retorne apenas a transcrição.
```

O resultado teve aproximadamente 1.901 caracteres, mas não foi fiel. O modelo inventou palavras,
alterou frases, confundiu nomes e reconstruiu o conteúdo com base em interpretação semântica.

### Prompt detalhado

Foi testado um prompt mais restritivo, solicitando leitura linha a linha, preservação de erros,
ignorando cabeçalho e usando `[ilegível]` para trechos incertos. O resultado continuou ruim,
com similaridade aproximada de 15,36% em comparação por conjunto de palavras.

### Prompt mínimo

Também foi testado exatamente:

```text
Transcreva
```

Esse resultado foi pior, com similaridade aproximada de 12,98%. Além de errar o manuscrito, o
modelo adicionou uma explicação sobre informações pessoais, embora o pedido fosse somente uma
transcrição.

### Temperatura zero

Foi adicionada temperatura `0` à chamada do cliente OpenAI para reduzir variações entre chamadas.
O resultado melhorou apenas marginalmente, chegando a aproximadamente 18,35% de similaridade,
mas ainda não era utilizável como transcrição fiel.

### Conclusão dos testes com IA

O problema não era apenas o texto do prompt. O modelo estava interpretando a caligrafia e
reconstruindo frases prováveis, em vez de realizar leitura literal. A imagem inteira também
continha cabeçalho, campos impressos e linhas de formulário que competiam com o manuscrito.

Por esse motivo, a transcrição baseada somente em modelo de visão não foi considerada adequada
para a primeira versão do produto.

## Decisão tomada

A primeira versão pública do fluxo usará Tesseract OCR em português no backend, com a seguinte
regra de produto:

- a transcrição é experimental;
- pode conter erros, especialmente em manuscritos cursivos;
- o estudante deve revisar e editar o texto;
- a avaliação só pode ser confirmada depois da revisão;
- a transcrição automática não é nota oficial nem texto considerado fiel;
- a imagem original é temporária e não deve ser persistida indefinidamente.

Essa decisão é provisória. Depois de coletar resultados reais e consentidos, o OCR poderá ser
substituído por uma solução especializada em manuscritos ou por um fluxo híbrido de OCR, visão e
revisão humana.

## Implementação atual

### Backend

Foi criado o serviço:

```text
backend/src/main/java/br/com/redae/evaluation/service/TesseractOcrService.java
```

Responsabilidades:

- receber os bytes da imagem;
- criar um diretório temporário;
- recortar somente a área aproximada da redação;
- salvar temporariamente o recorte como PNG;
- executar o comando Tesseract com o idioma `por`;
- usar o modo de segmentação `--psm 6`;
- impor timeout de 30 segundos;
- ler o arquivo temporário de saída;
- excluir entrada, saída e diretório temporário no bloco `finally`.

O serviço de domínio `EssayTranscriptionService` continua responsável por validar:

- arquivo obrigatório;
- tamanho máximo de 8 MB;
- tipos JPG e PNG;
- resposta vazia do OCR.

### Container

O `backend/Dockerfile` instala:

- `tesseract-ocr`;
- `tesseract-ocr-por`.

O binário é instalado apenas na imagem do backend. O frontend não acessa o Tesseract diretamente.

### Recorte de segurança

Antes do OCR, o backend recorta aproximadamente a região entre 24% e 88% da altura da imagem e
entre 4,5% e 98% da largura. O objetivo é excluir cabeçalho, nome, CPF, assinatura, instruções,
códigos e outros campos impressos.

O recorte é proporcional às dimensões da imagem e não deve ser interpretado como detecção perfeita
da área manuscrita. Fotografias com enquadramento muito diferente podem exigir uma estratégia de
detecção mais robusta.

### Frontend

Foi adicionado um aviso na etapa de revisão da transcrição:

```text
A transcrição automática é experimental e pode conter erros. Revise e corrija o texto antes de confirmar a avaliação.
```

O campo de edição existente foi preservado. O estudante continua podendo corrigir o texto antes
de confirmar a avaliação.

## Resultado do teste com Tesseract

O primeiro teste com a folha inteira retornou HTTP 200, mas também capturou textos impressos do
formulário e dados do cabeçalho. Isso foi considerado um problema de privacidade e de escopo.

Após o recorte, o resultado deixou de incluir o cabeçalho e os dados pessoais observados no teste.
Entretanto, a leitura da caligrafia continuou muito ruim: o Tesseract reconheceu grande parte do
manuscrito como ruído, sequências sem sentido e caracteres isolados.

Conclusão do teste:

```text
OCR instalado e funcional: sim
Upload e resposta HTTP: sim
Recorte do cabeçalho: parcialmente validado
Transcrição manuscrita fiel: não
```

O OCR atual deve ser tratado como mecanismo experimental de apoio, não como transcrição confiável
para manuscrito cursivo.

## Validações executadas

Backend:

```text
mvn --batch-mode verify
```

Resultado:

- 37 testes executados;
- 0 falhas;
- 0 erros;
- Spotless aprovado.

Docker:

- imagem do backend reconstruída;
- Tesseract 5.5.0 confirmado dentro do container;
- idioma português instalado;
- endpoint de transcrição respondeu HTTP 200;
- `git diff --check` sem erros de whitespace.

## Limitações conhecidas

1. Tesseract tradicional não é especializado em caligrafia cursiva.
2. O recorte usa proporções fixas e pode falhar em fotos fora do padrão.
3. A qualidade depende de foco, iluminação, inclinação, contraste e resolução.
4. O OCR pode retornar texto muito ruim sem necessariamente gerar erro HTTP.
5. HTTP 200 significa que houve uma saída textual; não significa que a transcrição seja fiel.
6. A revisão do estudante é indispensável antes da avaliação.
7. Ainda não existe uma métrica automática de confiança para bloquear ou sinalizar transcrições ruins.
8. Ainda não foi implementada comparação automática entre OCR e uma segunda fonte de leitura.

## Próximos passos recomendados

1. Adicionar pré-processamento de imagem: correção de rotação, contraste, remoção de linhas e
   binarização.
2. Detectar linhas manuscritas e executar OCR por blocos menores.
3. Testar um OCR especializado em manuscrito.
4. Criar um conjunto de imagens fictícias ou consentidas com transcrições revisadas.
5. Medir precisão por caracteres, palavras e linhas, sem depender apenas de similaridade global.
6. Exibir uma indicação de baixa confiança quando o resultado tiver muitos caracteres incomuns,
   pouco texto reconhecido ou baixa consistência.
7. Considerar fluxo híbrido somente depois de medir custo, latência, privacidade e precisão.

## Estado da decisão

Decisão vigente: publicar o recurso com aviso explícito e revisão obrigatória, usando Tesseract
OCR local como primeira implementação.

Status: implementado tecnicamente, validado no ambiente Docker e ainda não considerado confiável
para manuscritos sem correção manual do estudante.
