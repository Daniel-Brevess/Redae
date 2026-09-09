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

## Escopo inicial do frontend

- oferecer ações para câmera, galeria e arquivo do computador;
- aceitar somente imagens;
- mostrar uma prévia local da imagem selecionada;
- manter a imagem apenas em memória no navegador;
- não chamar a API de transcrição antes da implementação do backend;
- não simular uma transcrição ou uma avaliação.

## Implementação posterior do backend

O backend deverá receber a imagem por uma rota autenticada de upload, validar formato e tamanho, encaminhá-la ao cliente multimodal de IA e devolver o texto transcrito. A imagem não deverá ser persistida no banco ou em armazenamento permanente.

Depois da revisão, a avaliação deverá usar o endpoint existente, com uma origem própria para redação fotografada, caso essa distinção seja mantida no domínio.

## Decisões pendentes

- limite máximo de tamanho e formatos aceitos;
- contrato da rota de transcrição;
- estratégia multimodal de cada provedor;
- nome definitivo da origem da avaliação;
- tratamento de imagem ilegível, parcial ou com múltiplas páginas;
- momento de expiração da imagem temporária no backend.
