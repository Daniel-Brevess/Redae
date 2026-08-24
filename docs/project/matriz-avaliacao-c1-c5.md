# Matriz de avaliação da redação — C1 a C5

> **Status:** base de critérios validada; detalhes de níveis e casos especiais continuam em consolidação  
> **Fase:** 3 — Modelagem de domínio e dados

Esta matriz define como o Redaê deverá organizar a avaliação automática. Os critérios das competências serão baseados nos critérios oficiais do ENEM, conforme a [Cartilha da Redação do Enem](https://www.gov.br/inep/pt-br/centrais-de-conteudo/acervo-linha-editorial/publicacoes-institucionais/avaliacoes-e-exames-da-educacao-basica/redacao-do-enem-2025-cartilha-do-a-participante) e as [Matrizes de Referência do Enem](https://www.gov.br/inep/pt-br/centrais-de-conteudo/acervo-linha-editorial/publicacoes-institucionais/avaliacoes-e-exames-da-educacao-basica/matrizes-de-referencia-enem). O formato de evidências, explicações e orientações é uma adaptação do Redaê para fins pedagógicos.

## 1. Escala

Cada competência receberá um nível de 0 a 5, correspondente a uma faixa de 0 a 200 pontos. A nota geral será a soma das cinco competências, na escala de 0 a 1000 pontos.

A nota exibida pelo Redaê deverá ser identificada como uma estimativa automática, não como uma nota oficial do Enem.

## 2. Competências

### C1 — Modalidade escrita formal da língua portuguesa

Avalia o domínio da escrita formal, incluindo aspectos gramaticais, escolha vocabular, registro, ortografia, acentuação, pontuação, concordância, estrutura sintática e organização das palavras.

**A IA deve observar:**

- desvios de ortografia e acentuação;
- pontuação;
- concordância e regência quando aplicável;
- escolha vocabular e registro formal;
- estruturação dos períodos;
- clareza sintática.

**Feedback obrigatório:** trecho citado, identificação do desvio, explicação simples e sugestão de correção.

### C2 — Compreensão da proposta e desenvolvimento do tema

Avalia a compreensão do tema, o desenvolvimento dentro dos limites do texto dissertativo-argumentativo e o uso pertinente de conhecimentos relacionados ao tema.

**A IA deve observar:**

- atendimento ao recorte temático;
- presença de ponto de vista relacionado ao tema;
- desenvolvimento do texto dissertativo-argumentativo;
- uso pertinente de repertório sociocultural;
- possíveis sinais de tangenciamento, fuga ao tema ou cópia.

**Feedback obrigatório:** trecho ou parte da redação que demonstra o atendimento ou problema, explicação do impacto e orientação de como desenvolver melhor o tema.

### C3 — Seleção, organização e interpretação de informações e argumentos

Avalia a seleção, relação, organização e interpretação de informações, fatos, opiniões e argumentos em defesa de um ponto de vista.

**A IA deve observar:**

- clareza do ponto de vista;
- relação entre argumentos e tese;
- pertinência das informações;
- organização do projeto de texto;
- coerência entre as partes;
- desenvolvimento suficiente dos argumentos.

**Feedback obrigatório:** trecho citado ou conjunto curto de trechos relacionados, explicação da falha argumentativa e orientação de como fortalecer a defesa do ponto de vista.

### C4 — Mecanismos linguísticos para a argumentação

Avalia o uso dos mecanismos linguísticos necessários para construir a argumentação e articular as partes do texto.

**A IA deve observar:**

- uso de conectivos;
- relação entre frases e parágrafos;
- continuidade e progressão das ideias;
- repetição ou ausência de elementos de ligação;
- contradições ou rupturas causadas pela articulação linguística.

**Feedback obrigatório:** trecho citado, identificação do problema de articulação, explicação do efeito causado e alternativa de melhoria.

### C5 — Proposta de intervenção

Avalia a elaboração de proposta de intervenção para o problema abordado, respeitando os direitos humanos.

**A IA deve observar:**

- existência de uma proposta relacionada ao problema;
- ação proposta;
- agente responsável;
- meio ou modo de execução;
- finalidade ou efeito esperado;
- detalhamento quando presente;
- respeito aos direitos humanos.

**Feedback obrigatório:** trecho da proposta, elementos presentes ou ausentes, explicação da insuficiência e orientação de como torná-la mais completa.

## 3. Contrato mínimo da avaliação automática

Para cada competência, a IA deverá retornar:

```json
{
  "competencia": "C1",
  "nivel": 0,
  "pontuacao": 0,
  "resumo": "explicação clara e curta da avaliação",
  "evidencias": [
    {
      "trecho": "trecho literal da redação",
      "problema": "o que foi identificado",
      "explicacao": "por que isso afeta a competência",
      "como_melhorar": "orientação prática"
    }
  ],
  "limitacoes": [],
  "confianca": "alta"
}
```

O campo `trecho` deve conter somente texto encontrado na redação analisada. Quando não houver evidência suficiente, a IA deverá deixar `evidencias` vazio, explicar a limitação e evitar inventar um problema.

## 4. Regras de qualidade do feedback

- nenhuma nota deve ser apresentada sem justificativa;
- toda afirmação sobre um erro deve estar ligada a uma evidência;
- a explicação deve ser compreensível para um estudante do ensino médio;
- a orientação deve dizer o que mudar, não apenas apontar o erro;
- a IA deve diferenciar erro objetivo, interpretação e limitação;
- a análise deve considerar o tema e a proposta associados à redação;
- a avaliação deve preservar o texto original e a versão revisada pelo estudante;
- o sistema deve alertar que a avaliação é automática e estimada.

## 5. Casos que exigem tratamento especial

Antes de gerar uma nota normal, o sistema deverá verificar se há indícios de:

- fuga ao tema;
- não atendimento ao tipo textual exigido;
- texto insuficiente para avaliação;
- cópia relevante dos textos motivadores;
- conteúdo que impeça uma avaliação confiável.

O comportamento exato nesses casos deverá seguir a cartilha oficial e ser detalhado nos requisitos de negócio da análise.

## 6. Testes de validação da matriz

Cada competência deverá ser testada com redações que contenham:

- desempenho forte;
- erro claro e localizável;
- problema que exige interpretação;
- ausência de evidência suficiente;
- situação que pode gerar falsa acusação de erro.

A avaliação da IA só será considerada aceitável quando a justificativa, o trecho citado e a orientação forem coerentes entre si.
