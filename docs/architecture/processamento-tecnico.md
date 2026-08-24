# Modelo técnico de processamento efêmero

`Processamento` é uma estrutura temporária para controlar OCR e avaliação. Ela pode existir durante a execução e a janela de retentativa, mas não faz parte do histórico funcional.

```mermaid
erDiagram
    USUARIO ||--o{ PROCESSAMENTO : inicia
    PROCESSAMENTO }o--o| AVALIACAO : processa

    PROCESSAMENTO {
        uuid id PK
        uuid usuario_id FK
        uuid avaliacao_id FK NULL
        string tipo
        string status
        int tentativas
        string erro_codigo NULL
        string referencia_arquivo NULL
        timestamp expira_em
        timestamp iniciado_em NULL
        timestamp concluido_em NULL
        timestamp created_at
        timestamp updated_at
    }
```

Para OCR, `avaliacao_id` pode ser nulo porque a avaliação só nasce após a confirmação da transcrição. Após sucesso, o resultado é persistido e o processamento é removido.
