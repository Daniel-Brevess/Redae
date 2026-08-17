# Redaê

Base inicial da plataforma de treinamento personalizado de redação para o ENEM.

## Estrutura

- `frontend/`: aplicação React, TypeScript, Vite e Tailwind CSS.
- `backend/`: aplicação Spring Boot com Java 21 e Maven.
- `agents/`: instruções de trabalho para agentes e colaboradores.
- `docs/`: documentação curta e decisões do projeto.

## Desenvolvimento local

### Frontend

```bash
cd frontend
npm install
npm run dev
```

Build: `npm run build`

### Backend

```bash
cd backend
mvn spring-boot:run
```

No Windows PowerShell, use `mvn spring-boot:run`. Testes: `mvn test`.

O backend inicia sem credenciais externas e sem conexão de banco nesta etapa. A configuração de Oracle, JWT e Resend está documentada em `backend/src/main/resources/application-example.yml` e nas variáveis de ambiente.

## Infraestrutura futura

O plano inicial é Vercel para o frontend e Render para o backend. Azure poderá ser explorado posteriormente para aprendizado, infraestrutura e eventual migração.

## Status

Esta é somente a fundação técnica. Não há autenticação, domínio, persistência ou funcionalidades de negócio implementados.
