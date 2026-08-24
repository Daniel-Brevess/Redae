# Fluxos e telas prioritárias — Fase 5

## Diretriz geral

A experiência será mobile-first. Login e cadastro permanecem como cards da landing page existente. A área autenticada usará navegação compacta, saldo de créditos visível e uma ação principal por tela.

## Fluxo principal

1. Estudante entra na área privada.
2. Seleciona **Nova avaliação**.
3. Escolhe texto ou imagem no mesmo card.
4. Informa o tema.
5. Para texto, escreve livremente e confirma.
6. Para imagem, tira foto ou escolhe da galeria, revisa as imagens e aguarda a transcrição.
7. Revisa e pode editar a transcrição.
8. Confirma o texto transcrito.
9. Acompanha o processamento por uma mensagem clara, sem porcentagem artificial.
10. Consulta primeiro o resumo e depois as notas C1–C5 e os feedbacks expansíveis.

## Telas e objetivos

| Tela | Objetivo | Ação principal |
|---|---|---|
| Área inicial | orientar o próximo passo e mostrar saldo | Nova avaliação |
| Nova avaliação | escolher texto ou imagem | Continuar |
| Editor | escrever e informar tema | Confirmar redação |
| Captura | fotografar ou selecionar imagens | Enviar para transcrição |
| Revisão | revisar imagens ou transcrição | Confirmar transcrição |
| Processamento | comunicar que a avaliação está em andamento | Voltar ao histórico |
| Resultado | mostrar nota e orientar leitura do feedback | Ver competências |
| Histórico vazio | explicar o primeiro passo | Fazer minha primeira avaliação |

## Estados obrigatórios

Cada tela deve documentar estados de sucesso, loading, erro e vazio. Erros ficam próximos da ação que falhou, permitem retry quando possível e não apagam dados já válidos.

## Linguagem

Mensagens devem ser diretas, acolhedoras e objetivas, sem termos técnicos ou tom infantilizado. Exemplos: “Informe o tema da redação.”, “Escolha ao menos uma imagem.” e “Estamos avaliando sua redação.”

## Acessibilidade

- labels visíveis em campos;
- foco no primeiro erro após submissão inválida;
- foco sempre visível;
- ordem de teclado coerente;
- contraste adequado;
- semântica compatível com leitores de tela;
- ações de câmera, galeria, revisão e confirmação acessíveis sem depender apenas de cor.

## Fora do protótipo inicial

Compra de créditos, área administrativa e fluxos secundários ficam para uma etapa posterior. O protótipo deve validar o fluxo principal do estudante.
