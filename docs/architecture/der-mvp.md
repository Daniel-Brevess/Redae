# Diagrama entidade-relacionamento do MVP

O DER principal contém somente entidades persistentes do produto. A redação é uma entrada temporária e seus dados confirmados ficam dentro de `Avaliacao`.

```mermaid
erDiagram
    USUARIO ||--o{ AVALIACAO : possui
    AVALIACAO ||--o{ NOTA_COMPETENCIA : possui
    NOTA_COMPETENCIA ||--o{ FEEDBACK_ITEM : possui
    USUARIO ||--o{ COMPRA_CREDITO : realiza
    OFERTA_CREDITO o|--o{ COMPRA_CREDITO : aplica
    PRECO_CREDITO ||--o{ COMPRA_CREDITO : define
    USUARIO ||--o{ TRANSACAO_CREDITO : possui
    COMPRA_CREDITO ||--o{ TRANSACAO_CREDITO : gera
    AVALIACAO ||--o{ TRANSACAO_CREDITO : consome

    USUARIO {
        uuid id PK
        string nome
        string email UK
        string senha_hash
        string tipo_usuario
        timestamp created_at
        timestamp updated_at
    }

    AVALIACAO {
        uuid id PK
        uuid usuario_id FK
        text texto_confirmado
        string tema
        string origem
        string status
        int nota_final NULL
        string versao
        string modelo_ia
        timestamp gerada_em NULL
        timestamp created_at
        timestamp updated_at
    }

    NOTA_COMPETENCIA {
        uuid id PK
        uuid avaliacao_id FK
        string competencia_codigo
        int nivel
        int pontos
        text resumo
    }

    FEEDBACK_ITEM {
        uuid id PK
        uuid nota_competencia_id FK
        text trecho NULL
        text problema
        text explicacao
        text como_melhorar
        text limitacao NULL
    }

    COMPRA_CREDITO {
        uuid id PK
        uuid usuario_id FK
        uuid oferta_credito_id FK NULL
        uuid preco_credito_id FK
        string referencia_externa UK
        string status
        int quantidade_creditos
        int bonus_creditos
        int creditos_totais
        numeric valor
        string moeda
        timestamp paga_em NULL
        timestamp created_at
        timestamp updated_at
    }

    OFERTA_CREDITO {
        uuid id PK
        string nome
        int creditos_inclusos
        int bonus_creditos
        numeric preco
        string moeda
        boolean ativo
        timestamp vigencia_inicio NULL
        timestamp vigencia_fim NULL
        int limite_de_uso NULL
        timestamp created_at
        timestamp updated_at
    }

    PRECO_CREDITO {
        uuid id PK
        numeric valor_por_credito
        string moeda
        boolean ativo
        timestamp vigente_desde
        timestamp vigente_ate NULL
        uuid administrador_id FK
        timestamp created_at
        timestamp updated_at
    }

    TRANSACAO_CREDITO {
        uuid id PK
        uuid usuario_id FK
        uuid compra_credito_id FK NULL
        uuid avaliacao_id FK NULL
        string tipo
        int quantidade
        string referencia_externa NULL
        string motivo NULL
        uuid administrador_id FK NULL
        timestamp created_at
        timestamp updated_at
    }
```

## Regras do DER

- `Avaliacao.usuario_id` é obrigatório;
- `Avaliacao` armazena o texto confirmado, tema e origem;
- `NotaCompetencia` possui uma linha por C1–C5, com unicidade em `(avaliacao_id, competencia_codigo)`;
- `FeedbackItem` pode não ter trecho quando não houver evidência suficiente;
- `TransacaoCredito` usa referências opcionais conforme o tipo da transação;
- `Processamento` não aparece neste DER porque é efêmero.
