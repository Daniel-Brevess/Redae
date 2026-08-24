# Fluxo de redação digitada

No MVP, o estudante não salva manualmente um rascunho. O texto permanece no editor enquanto a sessão estiver ativa e só passa a ser persistido quando o estudante confirma o envio.

```mermaid
sequenceDiagram
    actor Estudante
    participant Frontend
    participant API as Backend API
    participant DB as PostgreSQL
    participant Worker as Processador assíncrono
    participant IA as Gemini

    Estudante->>Frontend: abre exercício e escreve texto
    Frontend->>API: carrega tema e instruções
    API-->>Frontend: tema e instruções
    Estudante->>Frontend: confirma envio
    Frontend->>API: envia texto confirmado
    API->>DB: salva redação e cria avaliação PENDENTE
    API-->>Frontend: status PENDENTE
    Worker->>DB: busca avaliação pendente
    Worker->>IA: envia texto confirmado e tema
    IA-->>Worker: nota e feedback
    Worker->>DB: salva avaliação e feedback
    Frontend->>API: consulta status por polling
    API-->>Frontend: resultado CONCLUÍDO
    Frontend-->>Estudante: exibe nota e feedback
```

Se o estudante abandonar o editor antes da confirmação, o texto não será recuperável pelo sistema no MVP. Essa decisão não impede que um recurso de rascunho automático seja avaliado posteriormente.
