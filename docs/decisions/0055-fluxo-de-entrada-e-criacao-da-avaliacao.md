# ADR 0055 — Fluxo de entrada e criação da avaliação

- **Status:** aceito
- **Data:** 2026-08-24

## Texto digitado

Quando o estudante confirmar uma redação digitada:

1. o backend valida texto e tema;
2. cria `Avaliacao` com `origem = DIGITADA`;
3. cria o processamento de avaliação;
4. consome ou reserva o crédito conforme a regra de cobrança;
5. processa a avaliação de forma assíncrona.

## Imagem

Quando o estudante enviar uma imagem:

1. o backend cria um `Processamento` de `OCR` temporário;
2. a imagem fica no armazenamento temporário;
3. a IA gera a transcrição;
4. o estudante revisa e confirma o texto;
5. o backend remove a imagem conforme a política de retenção;
6. somente então cria `Avaliacao` com `origem = IMAGEM`;
7. cria o processamento de avaliação.

Não haverá `Avaliacao` persistida para texto não confirmado ou transcrição ainda não revisada.

## Consequências

- evita avaliação de texto que o estudante não confirmou;
- mantém a imagem fora do histórico persistente;
- permite que texto digitado tenha fluxo mais curto;
- exige definir o momento de reserva/consumo do crédito no fluxo de imagem.
