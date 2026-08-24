# Fluxo de redação por imagem

A imagem é um insumo temporário para transcrição. A avaliação nunca utiliza diretamente a imagem ou a transcrição não confirmada.

```mermaid
sequenceDiagram
    actor Estudante
    participant Frontend
    participant API as Backend API
    participant Storage as Armazenamento temporário
    participant Worker as Processador OCR
    participant IA as Gemini
    participant DB as PostgreSQL

    Estudante->>Frontend: seleciona e envia imagem
    Frontend->>API: solicita processamento da imagem
    API->>Storage: guarda imagem temporariamente
    API->>DB: cria trabalho OCR PENDENTE
    API-->>Frontend: status PENDENTE
    Worker->>DB: busca trabalho OCR
    Worker->>Storage: lê imagem temporária
    Worker->>IA: envia imagem para transcrição
    IA-->>Worker: texto transcrito
    Worker->>DB: salva transcrição e status CONCLUÍDO
    Frontend->>API: consulta status por polling
    API-->>Frontend: exibe transcrição editável
    Estudante->>Frontend: revisa e confirma texto
    Frontend->>API: envia texto confirmado
    API->>DB: salva redação confirmada e cria avaliação
    API->>Storage: solicita exclusão da imagem
    API-->>Frontend: avaliação PENDENTE
```

## Regras

- a imagem fica disponível somente durante OCR e conferência;
- a transcrição pode ser corrigida pelo estudante antes da confirmação;
- somente o texto confirmado é persistido como redação e enviado para avaliação;
- a imagem deve ser excluída após a confirmação, respeitando o prazo máximo de 10 minutos;
- se o OCR falhar, o trabalho fica `FALHOU` e nenhuma avaliação é criada;
- se o estudante abandonar a conferência sem confirmar, a imagem e a transcrição temporária são descartadas conforme a política de retenção.
