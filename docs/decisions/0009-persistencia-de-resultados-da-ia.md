# ADR 0009 — Persistência somente dos resultados da IA

- **Status:** aceito
- **Data:** 2026-08-24

## Contexto

O Redaê precisa usar IA para transcrever imagens e avaliar redações, mas não deve transformar as interações com o provedor em armazenamento permanente de prompts, respostas ou imagens. O estudante precisa consultar posteriormente o texto confirmado, a nota e o feedback recebido.

## Decisão

No MVP, a IA será usada somente para duas finalidades:

1. transcrever a imagem de uma redação;
2. avaliar o texto confirmado e gerar nota e feedback.

O Redaê não persistirá a interação bruta com o provedor, incluindo imagem enviada para transcrição, prompt, resposta bruta da IA e contexto temporário usado na chamada. Também não haverá texto integral em logs operacionais.

O sistema persistirá apenas os resultados necessários ao produto:

- texto transcrito e confirmado pelo estudante;
- nota da avaliação;
- feedback estruturado por competência;
- versão da avaliação e data de geração;
- metadados técnicos mínimos para rastreabilidade, como operação, status, modelo, duração e consumo estimado.

A imagem utilizada na transcrição continuará temporária e será excluída conforme a regra já definida. A avaliação será feita somente sobre o texto confirmado.

## Consequências

### Positivas

- reduz a retenção de conteúdo sensível fora do domínio do produto;
- mantém no histórico somente o que o estudante precisa consultar;
- facilita explicar claramente ao usuário quais dados são armazenados;
- diminui o risco de vazamento por logs ou armazenamento de respostas brutas.

### Negativas e controles

- uma nova avaliação exigirá uma nova chamada à IA;
- prompts e respostas não estarão disponíveis para depuração posterior;
- a versão da avaliação e os metadados técnicos deverão ser suficientes para auditoria operacional;
- alterações de nota ou feedback deverão gerar uma nova versão, sem sobrescrever silenciosamente o resultado anterior.

## Fora desta decisão

Esta ADR não define se o usuário poderá solicitar reavaliação, quantas reavaliações serão permitidas ou por quanto tempo os resultados serão retidos.
