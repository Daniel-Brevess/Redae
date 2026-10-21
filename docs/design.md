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
A área autenticada compartilha essa identidade, com aplicação própria para uso contínuo.
O header segue a composição da [AbacatePay](https://www.abacatepay.com/): cápsula
translúcida com blur, largura limitada e posição fixa, marca e links à esquerda,
Login e Cadastro à direita. No mobile, o botão de três linhas abre um painel
com navegação e Login; a seleção de um link ou Escape fecha o menu.
O footer ocupa 100% da largura, usa verde escuro e apenas os cantos superiores
arredondados e apresenta o Redazinho lendo no lugar do logo. A pose é extraída da
folha `1:10` do Figma (segunda pose da primeira linha), sem fundo, por image_gen,
preservando personagem, livro e pose, em `frontend/assets/redazinho-reading.png`.
A hero não exibe selo de edição do ENEM.
A headline da hero usa ExtraBold (800): “Sua redação pode estar te afastando da
faculdade que você tanto quer.”, com “afastando da faculdade” em verde. O CTA
da hero diz “Corrigir minha redação agora”.
O parágrafo da hero não inclui “por IA”; não há microtexto sob seu CTA nem balão
amarelo ao lado do mascote. Os avisos sobre avaliação por IA permanecem nas demais seções.
O fechamento também usa a referência da AbacatePay: texto conversacional para
quem chegou ao fim, CTA centralizado e cena ampla com Redazinho lendo, círculos
e transição de fundo até o footer. O footer tem colunas com links internos reais,
divisória, mascote, copyright e aviso dos limites da avaliação automática.
Não há links fictícios de suporte, redes sociais ou páginas legais.
Efeitos de hover são suaves e respeitam a preferência por movimento reduzido.
Na rolagem, os blocos da landing, incluindo a hero, aparecem com
fade e deslocamento de 32px, em 700ms. Header e footer ficam fora do surgimento;
o footer conserva seus efeitos de hover anteriores. Textos e
ações da hero entram juntos; colunas de conteúdo e perguntas entram em blocos,
enquanto as competências têm pequenos atrasos entre itens. A cena final usa parallax
vinculado ao scroll (metade da intensidade no celular), retornando à posição
natural ao chegar à base. `useLandingMotion` usa IntersectionObserver e
requestAnimationFrame, limpa listeners ao desmontar e desativa movimento em
`prefers-reduced-motion`. Sem suporte às APIs, o conteúdo permanece visível.
A faixa verde após a hero foi substituída por duas mãos fornecidas pelo usuário,
com fundos removidos por image_gen, preservando a composição das imagens. Os assets
são `frontend/assets/hand-reaching.png` e `frontend/assets/hand-holding-redazinho.png`.
As mãos começam deslocadas para fora das laterais, com baixa opacidade, e se
aproximam conforme a seção avança pelo viewport. Ao voltar o scroll, se afastam.
As imagens têm escala reduzida e cada mão fica restrita à sua metade da cena,
sem cruzar ou sobrepor a outra, inclusive no celular.
Com movimento reduzido ou sem APIs disponíveis, aparecem na posição final.
Os cards de acesso são compactos (até 330px), com fundo claro, borda verde,
tipografia da landing e botões da marca. Têm rolagem interna em telas baixas;
os fluxos de autenticação e validação permanecem os mesmos.
A seção de problema usa `ScrollStory`: 13 trechos definidos, revelados palavra
por palavra conforme o scroll. Um painel de 100svh fica sticky durante um percurso
de 594svh e se libera ao terminar a sequência, sem interceptar roda, toque ou
teclado. O scroll reverso volta os trechos. Verde e azul destacam perguntas e
próximos passos. O texto fica centralizado, sem contagem nem barra de progresso.
“pode” encerra o quinto trecho na mesma cor escura do sexto trecho, que começa
com “fazer você repetir”. A versão para leitores
de tela mantém o texto completo, sem anúncios contínuos. Com movimento reduzido
ou sem as APIs necessárias, a leitura é estática e sem o percurso longo.
A hero usa uma variação do Redazinho tentando alcançar o CTA, criada com image_gen
a partir do mascote original: preservar identidade, cores, mochila e traços, mudar
apenas a pose, sem fundo nem botão desenhado. No desktop estende o braço à esquerda
(`redazinho-reaching-cta.png`); no celular aponta para cima
(`redazinho-reaching-cta-mobile.png`). Os arquivos ficam em `frontend/assets/`.

Os botões públicos têm cantos de 12px, sem setas. Ações de correção e cadastro
usam verde da marca, texto escuro, contraste e sombra curta para destaque; Login
é um link de texto, sem fundo ou borda, que abre o formulário existente. O footer
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
A seção de competências acompanha a base clara da landing, com título central
em ExtraBold e destaque verde, descrição com ênfase pontual e cards amplos em
três colunas seguidas de duas no desktop. Os cards usam bordas verdes suaves,
marcadores lima e tipografia maior, com duas colunas no tablet e uma no celular.
O conteúdo C1–C5 e o aviso de nota estimada são preservados.
As seções de demonstração e dúvidas usam a mesma hierarquia tipográfica, fundo
claro e bordas verdes suaves. Os três passos ficam em cards com marcadores lima;
o telefone ilustrativo tem moldura verde escura e sombra suave. As perguntas
ficam centralizadas em acordeões com indicador de abrir/fechar, usando `details`
e `summary` nativos para preservar teclado e acessibilidade. Respostas, limites
da avaliação, descrição de créditos e CTA de cadastro são mantidos.

### Área autenticada

- Plus Jakarta Sans, Medium a Bold, com títulos em ExtraBold;
- fundo claro `#F8FAF6`;
- texto verde escuro e tons secundários esverdeados;
- verde para ação e evolução;
- azul para informação;
- lima para progresso;
- cards arredondados, bordas verdes suaves e sombras leves;
- botões retangulares com cantos de 12px, verde vivo e texto escuro;
- navegação translúcida, créditos em lima e indicação explícita da tela ativa.

O tema `frontend/src/internal.css` é aplicado à área de treino e à administração.
Cobre início, escolha de formato, editor, imagem/transcrição, confirmação,
processamento, resultado/competências, histórico, créditos, transações e perfil,
incluindo erros, loading, campos e estados desabilitados. O Redazinho aparece
como elemento decorativo no card de nova avaliação no desktop. No celular, a
navegação quebra em linhas e mantém as abas acessíveis por rolagem horizontal.
Os fluxos, dados, permissões e chamadas HTTP não foram alterados pelo tema.

| Token | Valor | Uso |
|---|---|---|
| `color-brand-primary` | `#22C55E` | ação principal |
| `color-brand-primary-hover` | `#40D875` | interação |
| `color-brand-accent` | `#3B82F6` | informação e foco |
| `color-progress` | `#D9F99D` | progresso e marcadores |
| `color-text` | `#0F2E22` | texto principal |
| `color-muted` | `#526358` | texto secundário |
| `color-surface` | `#F8FAF6` | superfície |
| `radius-card` | `26px` | cards |
| `radius-control` | `12px` | botões e campos |

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
