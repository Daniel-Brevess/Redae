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
