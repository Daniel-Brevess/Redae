# ADR 0081 — Migração futura para o OpenAI GPT-4o Mini

- **Status:** proposto
- **Data:** 2026-09-02

## Contexto

O Redaê utiliza atualmente o Google Gemini para gerar avaliações de redações.
Os testes realizados até agora mostraram variação entre as notas da IA e as
notas humanas de referência, além de limitações temporárias de uso no ambiente
gratuito do provedor.

Para continuar a validação com um modelo de baixo custo e reduzir a dependência
de um único provedor, foi decidido avaliar posteriormente o OpenAI GPT-4o Mini.

## Decisão proposta

O próximo provedor a ser integrado para testes será a **OpenAI**, utilizando o
modelo **GPT-4o Mini**.

O modelo deverá receber o mesmo texto, tema, prompt calibrado e contrato JSON
utilizados na comparação atual. A troca será feita de forma que o analisador de
avaliação continue dependendo da abstração `AIClient`, permitindo comparar os
provedores sem alterar o fluxo de negócio.

## Motivos da escolha

- custo compatível com a fase de validação do MVP;
- suporte a respostas estruturadas em JSON;
- possibilidade de uso pré-pago pela API;
- menor dependência dos limites gratuitos do Gemini;
- possibilidade de comparar a qualidade por competência, e não apenas pela nota
  total.

## Escopo da futura implementação

Quando aprovada e iniciada, a migração deverá:

1. criar um cliente OpenAI compatível com a interface `AIClient`;
2. configurar a chave da API somente por variável de ambiente;
3. configurar o modelo por propriedade externa, sem credenciais no repositório;
4. reutilizar o analisador, o prompt e o validador de resposta existentes;
5. preservar o registro do modelo utilizado em cada avaliação;
6. permitir a seleção do provedor por configuração, quando isso for necessário
   para os testes;
7. tratar erros, respostas inválidas e indisponibilidade do provedor;
8. atualizar a documentação e os testes técnicos correspondentes.

## Fora do escopo desta decisão

- remover imediatamente a integração com o Gemini;
- alterar o prompt antes da comparação entre provedores;
- colocar a chave da OpenAI no código, no frontend, no Dockerfile ou no Git;
- implementar cobrança, compra de créditos ou limites de usuário;
- afirmar que o modelo reproduzirá exatamente as notas humanas.

## Estratégia de validação

A comparação deverá utilizar as três redações de referência já reunidas, com
notas humanas por competência C1–C5. Para cada provedor, registrar:

- modelo e configuração utilizados;
- nota de cada competência e nota total;
- justificativas e evidências dos descontos;
- respostas inválidas ou falhas de integração;
- tempo e custo aproximados da solicitação.

A escolha do provedor não será baseada em uma única redação. Será considerada a
consistência entre redações de níveis diferentes, a proximidade das notas por
competência e a qualidade pedagógica dos feedbacks.

## Critérios para iniciar a migração

A implementação poderá começar quando houver aprovação explícita desta decisão
e uma chave da API configurada fora do repositório. Antes de qualquer commit,
deverão passar as verificações de formatação, testes e build previstas no CI.

## Referências

- [Documentação oficial do GPT-4o Mini](https://developers.openai.com/api/docs/models/gpt-4o-mini)
- [Preços oficiais da API](https://developers.openai.com/api/docs/pricing)
- [Faturamento pré-pago da API](https://help.openai.com/en/articles/8264644-what-is-prepaid-billi)
