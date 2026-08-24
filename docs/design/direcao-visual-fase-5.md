# Direção visual — Fase 5

## Personalidade

O Redaê terá uma experiência editorial contemporânea, minimalista e acolhedora. A interface deve transmitir clareza, confiança e evolução gradual, sem parecer uma plataforma escolar rígida ou um painel técnico.

## Referência atual

A landing page existente orienta a direção visual com:

- tipografia grande e expressiva;
- bastante espaço em branco;
- cards arredondados e navegação em formato pill;
- transparência e blur usados com moderação;
- verde como cor de ação e evolução;
- azul como cor de destaque e informação;
- lima como sinal de progresso e conquista;
- textos curtos, diretos e motivadores;
- layout responsivo com redução progressiva de colunas.

## Princípios de interface

1. Clareza antes de ornamentação.
2. Uma ação principal evidente por tela.
3. Progresso visível sem criar pressão.
4. Feedback simples, humano e específico.
5. A estética deve apoiar a concentração na escrita e na leitura do resultado.

## Base visual inicial

- Fonte: Manrope, conforme a landing page atual.
- Fundo: neutro muito claro.
- Texto: grafite escuro e cinzas azulados.
- Ação primária: verde.
- Destaque informativo: azul.
- Progresso positivo: lima.
- Bordas: suaves, com raio generoso em cards e controles.
- Sombras: leves e amplas, evitando excesso de profundidade.

Tokens iniciais:

| Token | Valor | Uso |
|---|---|---|
| `color-brand-primary` | `#16A36A` | ação principal e evolução |
| `color-brand-primary-hover` | `#128855` | interação da ação principal |
| `color-brand-accent` | `#4F7CFF` | destaque e informação |
| `color-progress` | `#B5F25C` | progresso e conquista |
| `color-text` | `#15171A` | texto principal |
| `color-muted` | `#64748B` | texto secundário |
| `color-surface` | `#F8FAFC` | fundo neutro |
| `radius-card` | `30px` | cards principais |
| `radius-control` | `999px` | botões e controles pill |

## Estrutura inicial da área autenticada

- tela inicial privada com CTA principal para nova avaliação;
- saldo de créditos visível no topo, junto ao acesso ao perfil;
- card de nova avaliação com escolha entre texto e imagem;
- resultado com resumo da nota antes do detalhamento por C1–C5.

## Componentes e interação

- navegação superior simples para início, histórico, créditos e perfil;
- verde para ação primária, azul para ação secundária ou informativa;
- feedbacks de C1–C5 em cards expansíveis;
- todo componente interativo possui estados normal, hover, foco, desabilitado, loading e erro.

## Mobile-first

A experiência será projetada primeiro para o celular, principal contexto de uso do estudante: escrever, fazer Pix, fotografar a redação e acompanhar o processamento. Em telas menores, a navegação será compacta e os cards serão empilhados em uma coluna.

O processamento será comunicado por mensagens claras, sem porcentagem artificial. O histórico vazio explicará o próximo passo e oferecerá o CTA para fazer a primeira avaliação.

## Formulários e captura de imagem

- nova avaliação será organizada em etapas curtas, com uma ação principal por tela;
- campos terão labels visíveis e mensagens de erro próximas ao campo;
- o foco irá para o primeiro erro após uma tentativa inválida;
- captura oferecerá câmera e galeria;
- imagens poderão ser revisadas, removidas e reorganizadas antes da transcrição;
- foco, contraste e semântica serão preservados em todas as etapas.

## Conteúdo e recuperação

As mensagens serão diretas, acolhedoras e objetivas, sem termos técnicos ou tom infantilizado. Validações serão específicas e próximas ao campo. Falhas de upload ou transcrição permitirão retry sem perder imagens já válidas. A transcrição poderá ser revisada e editada antes da confirmação.
