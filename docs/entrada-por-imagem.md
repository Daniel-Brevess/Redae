# Entrada de redação por imagem

## Objetivo

Permitir que o estudante tire uma foto da redação, escolha uma imagem da galeria ou selecione um arquivo no computador. A imagem será transcrita por IA, revisada pelo estudante e só então enviada para o fluxo existente de avaliação.

## Fluxo definido

```text
Escolha da origem
  → envio temporário da imagem ao backend
  → transcrição pela IA
  → revisão e edição do texto pelo estudante
  → confirmação
  → avaliação existente
```

## Escopo implementado

### Backend e integracao

- `POST /api/v1/essay-transcriptions` recebe o campo multipart `image`.
- A rota exige autenticacao e aceita JPG ou PNG de ate 8 MB.
- O backend envia a imagem ao provedor multimodal e retorna o texto transcrito.
- A imagem nao e persistida; a confirmacao usa `POST /api/v1/evaluations` com origem `IMAGEM`.

- oferecer ações para câmera, galeria e arquivo do computador;
- aceitar somente imagens;
- mostrar uma prévia local da imagem selecionada;
- manter a imagem apenas em memória no navegador;
- enviar a imagem para `POST /api/v1/essay-transcriptions`;
- revisar e editar a transcrição localmente;
- confirmar o texto usando `POST /api/v1/evaluations` com origem `IMAGEM`;
- não persistir a imagem nem o texto antes da confirmação.

## Implementação do backend

Implementado com `POST /api/v1/essay-transcriptions`. A rota autenticada
aceita o campo multipart `image`, somente JPG ou PNG de até 8 MB, e retorna
`{ data: { text }, meta, traceId }`. A imagem não é persistida.

O modelo da transcrição pode ser configurado separadamente com
`AI_TRANSCRIPTION_MODEL` e, por padrão, é `gpt-4o`. O modelo da avaliação
continua sendo definido por `AI_MODEL`.

O backend deverá receber a imagem por uma rota autenticada de upload, validar formato e tamanho, encaminhá-la ao cliente multimodal de IA e devolver o texto transcrito. A imagem não deverá ser persistida no banco ou em armazenamento permanente.

Depois da revisão, a avaliação usa o endpoint existente com a origem `IMAGEM`.

## Decisões pendentes

- limite máximo de tamanho e formatos aceitos;
- contrato da rota de transcrição;
- estratégia multimodal de cada provedor;
- nome definitivo da origem da avaliação;
- tratamento de imagem ilegível, parcial ou com múltiplas páginas;
- momento de expiração da imagem temporária no backend.
