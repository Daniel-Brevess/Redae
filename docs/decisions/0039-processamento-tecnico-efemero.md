# ADR 0039 — Processamento técnico efêmero

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

`Processamento` será usado somente durante a execução de OCR ou avaliação. Ele não fará parte do histórico funcional do estudante.

Quando o trabalho terminar com sucesso:

1. o resultado é persistido em uma transação;
2. a redação/avaliação recebe o estado final;
3. o registro técnico de `Processamento` é removido.

Quando houver falha:

- o registro permanece pelo período necessário para retentativa controlada;
- o limite inicial será de 3 tentativas ou 24 horas, o que ocorrer primeiro;
- após o limite de tentativas ou expiração, o registro é removido;
- metadados mínimos podem ser enviados aos logs operacionais, sem conteúdo da redação ou resposta da IA.

As entidades `Redacao` e `Avaliacao` não dependerão de uma linha de `Processamento` depois que o resultado for concluído.

## Consequências

- o banco mantém apenas o estado funcional que o estudante consulta;
- reduz retenção de dados técnicos e volume de registros;
- exige limpeza confiável de processamentos expirados;
- falhas não poderão ser investigadas pelo banco após a limpeza, apenas pelos metadados permitidos nos logs.

Não haverá FK permanente de `Avaliacao` para `Processamento`.
