# Design

## Direção visual

O Redaê deve parecer editorial, contemporâneo, minimalista e acolhedor. A interface prioriza concentração na escrita, clareza do feedback e percepção de evolução, sem parecer um painel técnico ou uma plataforma escolar rígida.

Princípios:

1. Clareza antes de ornamentação.
2. Uma ação principal evidente por tela.
3. Progresso visível sem criar pressão.
4. Feedback simples, humano e específico.
5. Mobile-first.

## Base visual

### Página pública de vendas

A landing page `/` segue a VSL em Projetos → Redaê → Marketing no Notion e
a referência de marca no [Figma](https://www.figma.com/design/1dbfDwjKu2vQVexc5uEtsr/Sem-t%C3%ADtulo?node-id=0-1).
Usa Plus Jakarta Sans nos pesos Medium (500) a Bold (700), verde escuro `#0F2E22`, verde `#22C55E`, lima
`#D9F99D`, azul `#3B82F6`, amarelo `#FACC15` e fundo `#F8FAF6`.
Esses estilos ficam restritos à página pública; a área autenticada mantém a base abaixo.
O header ocupa toda a largura da tela, sem arredondamento, fixo no topo durante a rolagem
(`position: fixed`), com espaço reservado antes do conteúdo, Login e Cadastro. No mobile, um botão de três linhas
abre e fecha os links de navegação; a seleção de um link ou Escape fecha o menu.
O footer ocupa 100% da largura, usa verde escuro e apenas os cantos superiores
arredondados e apresenta o Redazinho lendo no lugar do logo. A pose é extraída da
folha `1:10` do Figma (segunda pose da primeira linha), sem fundo, por image_gen,
preservando personagem, livro e pose, em `frontend/assets/redazinho-reading.png`.
A hero não exibe selo de edição do ENEM.
A headline da hero usa ExtraBold (800), com “PERDENDO PONTOS” em ExtraBold (800) itálico,
em maiúsculas e sem quebra interna; “na redação e nem sabe.” começa abaixo.
O parágrafo da hero não inclui “por IA”; não há microtexto sob seu CTA nem balão
amarelo ao lado do mascote. Os avisos sobre avaliação por IA permanecem nas demais seções.
Os botões públicos têm cantos de 12px, sem setas. Ações de correção e cadastro
usam verde da marca, texto escuro, contraste e sombra curta para destaque; Login
é um link de texto, sem fundo ou borda, que abre o formulário existente. O header tem apenas a borda inferior, e o footer
preenche toda a largura até o fim da página, sem margem externa inferior.

O logo usa a imagem fornecida pelo usuário, correspondente ao Figma (`1:7`), com
fundo removido em `frontend/assets/redae-logo-transparent.png`, compartilhada
entre o header público e a área autenticada. O recorte por image_gen remove apenas
o branco, preservando letras, cores, acentos e proporções. O Redazinho da landing é a borracha
amarela com mochila verde (`1:11`), com fundo removido, em
`frontend/assets/redazinho-vsl.png`. O recorte foi produzido com a ferramenta
image_gen: remover somente fundo branco e sombra, preservar personagem, pose,
cores, proporções e detalhes, gerar PNG com transparência.

A página contém hero, problema, cinco competências, demonstração em três passos,
perguntas frequentes e CTA final. CTAs abrem o cadastro existente. O telefone
apresenta um exemplo ilustrativo, explicitamente identificado, sem representar
uma avaliação real. Não há vídeo VSL, depoimentos, garantias de nota ou preços
fixos novos. A nota por IA é apresentada como estimativa.

### Área autenticada

- Manrope;
- fundo neutro claro;
- texto grafite e cinzas azulados;
- verde para ação e evolução;
- azul para informação;
- lima para progresso;
- cards arredondados e sombras leves.

| Token | Valor | Uso |
|---|---|---|
| `color-brand-primary` | `#16A36A` | ação principal |
| `color-brand-primary-hover` | `#128855` | interação |
| `color-brand-accent` | `#4F7CFF` | informação |
| `color-progress` | `#B5F25C` | progresso |
| `color-text` | `#15171A` | texto principal |
| `color-muted` | `#64748B` | texto secundário |
| `color-surface` | `#F8FAFC` | superfície |
| `radius-card` | `30px` | cards |
| `radius-control` | `999px` | pills |

## Fluxo principal de telas

1. Área inicial autenticada: próxima ação e saldo.
2. Nova avaliação: escolha entre texto e imagem.
3. Editor: tema e redação.
4. Captura: câmera ou galeria.
5. Revisão: imagens ou transcrição.
6. Processamento: estado claro, sem porcentagem artificial.
7. Resultado: nota resumida e competências expansíveis.
8. Histórico: avaliações anteriores e progresso.

Todos os estados devem contemplar sucesso, loading, erro e vazio. Erros ficam próximos da ação que falhou e permitem retry quando possível.

## Acessibilidade e conteúdo

- labels visíveis;
- foco visível e ordem de teclado coerente;
- foco no primeiro erro após submissão inválida;
- contraste adequado;
- semântica compatível com leitores de tela;
- mensagens diretas, acolhedoras e objetivas;
- ações não dependem apenas de cor.
