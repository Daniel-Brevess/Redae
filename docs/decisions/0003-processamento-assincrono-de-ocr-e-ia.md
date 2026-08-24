# ADR 0003 — Processamento assíncrono de OCR e IA

- **Status:** aceito
- **Data:** 2026-08-24

## Contexto

O processamento de uma redação por imagem pode envolver upload, transcrição multimodal, revisão do estudante e avaliação por IA. Manter toda essa operação dentro de uma única requisição aumentaria o risco de timeout, dificultaria retentativas e reduziria a capacidade de absorver picos de uso.

## Decisão

OCR e avaliação por IA serão processados de forma assíncrona pelo backend. O estado do processamento será persistido e poderá assumir os valores:

- `PENDENTE`: trabalho criado e aguardando execução;
- `PROCESSANDO`: trabalho em execução;
- `CONCLUÍDO`: etapa finalizada com sucesso;
- `FALHOU`: etapa encerrada com erro e disponível para tratamento ou retentativa.

O fluxo de imagem será dividido em etapas:

1. o backend recebe a imagem e cria o trabalho de OCR;
2. o provedor de IA gera uma transcrição editável;
3. o estudante revisa e confirma o texto;
4. o backend cria o trabalho de avaliação;
5. a IA gera o feedback estruturado;
6. o estudante consulta o resultado no histórico.

A avaliação nunca utilizará diretamente o texto bruto extraído da imagem: somente o texto confirmado pelo estudante será avaliado.

## Consequências

### Positivas

- evita manter requisições abertas durante operações demoradas;
- permite retentativas controladas e rastreamento por etapa;
- facilita limitar concorrência e custo do provedor de IA;
- prepara o sistema para aumento de volume sem exigir microservices no MVP.

### Negativas e controles

- a interface precisa comunicar estados de processamento;
- será necessário persistir trabalhos e erros técnicos;
- o MVP precisará definir uma estratégia simples de atualização da tela;
- trabalhos duplicados deverão ser evitados por uma chave de idempotência ou regra equivalente.

## Fora desta decisão

Esta ADR não define o mecanismo de fila, o provedor de mensageria, notificações push ou a estratégia final de polling. Esses pontos serão detalhados na arquitetura de infraestrutura e na especificação da API.
