# Containers do sistema

O Redaê será implantado como frontend separado e backend monolítico modular. O processamento assíncrono permanece dentro do backend no MVP.

```mermaid
flowchart LR
    browser[Navegador]
    frontend[Frontend React\nTypeScript / Vite / Tailwind\nVercel]

    subgraph backend[Backend Spring Boot - Render]
        api[API HTTP\nControllers e DTOs]
        modules[Módulos de negócio\nidentity / auth / essays / evaluation / history / support]
        processing[Processador assíncrono\nOCR e avaliação]
        aiport[Interface de IA]
        repos[Repositories]
    end

    postgres[(PostgreSQL)]
    storage[(Armazenamento temporário)]
    gemini[Gemini API]

    browser --> frontend
    frontend -->|HTTPS| api
    api --> modules
    modules --> repos
    repos --> postgres
    modules --> processing
    processing --> storage
    processing --> aiport
    aiport --> gemini
    processing --> modules
```

## Responsabilidades

- **Frontend:** experiência, validações de interface, editor, polling e apresentação dos estados.
- **API HTTP:** autenticação da requisição, validação de entrada, mapeamento de respostas e erros públicos.
- **Módulos de negócio:** casos de uso e regras do domínio de cada contexto.
- **Processador assíncrono:** execução, retentativa, limite de concorrência e atualização de estados de OCR/avaliação.
- **Interface de IA:** abstração do provedor e dos modelos utilizados.
- **Repositories:** persistência acessada somente pelo próprio módulo responsável.
- **PostgreSQL:** fonte oficial dos dados persistentes.
- **Armazenamento temporário:** imagens durante o OCR e conferência, com exclusão automática.
