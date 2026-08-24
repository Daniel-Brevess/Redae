# Fluxo de avaliação por IA

A avaliação começa somente depois que o estudante confirma o texto. O backend valida a resposta do Gemini antes de persistir qualquer nota ou feedback.

```mermaid
sequenceDiagram
    actor Estudante
    participant Frontend
    participant API as Backend API
    participant DB as PostgreSQL
    participant Worker as Processador de avaliação
    participant IA as Gemini

    Estudante->>Frontend: confirma texto da redação
    Frontend->>API: envia texto confirmado e tema
    API->>DB: salva redação e avaliação PENDENTE
    API-->>Frontend: status PENDENTE
    Worker->>DB: busca avaliação pendente
    Worker->>IA: envia texto confirmado e tema
    IA-->>Worker: resposta estruturada
    Worker->>Worker: valida esquema, nota e competências

    alt resposta válida
        Worker->>DB: salva nota e feedback
        Worker->>DB: atualiza avaliação para CONCLUÍDO
    else resposta inválida ou falha técnica
        Worker->>DB: registra falha sem salvar resultado falso
        Worker->>DB: atualiza avaliação para FALHOU
    end

    Frontend->>API: consulta status por polling
    API-->>Frontend: status e resultado disponível
    Frontend-->>Estudante: exibe nota e feedback
```

## Regras

- a IA recebe somente o texto confirmado e o tema necessário;
- a resposta precisa obedecer ao contrato estruturado definido pelo backend;
- nota fora da escala ou competência ausente invalida a resposta;
- resposta inválida não gera nota visível nem avaliação concluída;
- retentativas só ocorrem para falhas técnicas, respeitando o limite de uma avaliação válida por redação;
- o resultado persistido contém a nota, os feedbacks, a versão da avaliação e a data de geração.
