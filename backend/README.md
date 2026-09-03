# Backend

Aplicação Spring Boot do Redaê, organizada como monólito modular.

## Estrutura

Cada módulo de domínio possui camadas próprias:

```text
src/main/java/br/com/redae/
├── user/         # identidade, estudantes, administradores e perfis
├── auth/         # autenticação, sessões e autorização
├── evaluation/   # redações, processamento, notas e feedback
├── ai/           # abstração e adaptadores dos provedores de IA
└── shared/       # componentes transversais
```

As camadas são `controller`, `service`, `repository`, `dto`, `entity` e `config`,
quando aplicáveis. Os módulos já contêm autenticação, perfil, avaliações,
persistência e integração com o cliente de IA.

## Banco local

O ambiente local usa PostgreSQL via Docker Compose. Os valores de configuração
devem ser fornecidos pelo ambiente local ou por um gerenciador de secrets. O
repositório não contém arquivos de exemplo, senhas ou valores padrão.

As variáveis necessárias para o Compose são `POSTGRES_DB`, `POSTGRES_USER` e
`POSTGRES_PASSWORD`. O backend também recebe `DB_URL`, `DB_USERNAME` e
`DB_PASSWORD` quando executado fora do Compose.

Para usar a avaliação com OpenAI no Docker, defina `OPENAI_API_KEY` no
ambiente antes de iniciar o Compose. O provedor padrão é `openai`.
No PowerShell:

```powershell
$env:OPENAI_API_KEY = "sua-chave-da-openai"
docker compose up --build
```

No Bash:

```bash
export OPENAI_API_KEY="sua-chave-da-openai"
docker compose up --build
```

Para usar o Gemini, defina `AI_PROVIDER=gemini`, `GOOGLE_API_KEY` e, se
necessário, `AI_MODEL`. A chave é enviada somente para o container do backend
e não deve ser colocada no código ou no frontend.

Depois de configurar os valores no ambiente, execute:

```bash
docker compose up --build
```

O Flyway executa automaticamente as migrations pendentes em
`src/main/resources/db/migration`. Migrations já aplicadas não devem ser
editadas; correções devem ser adicionadas em um novo arquivo versionado.

## Comandos

```bash
mvn test
mvn package
mvn spotless:check
mvn verify
mvn org.owasp:dependency-check-maven:check -DfailBuildOnCVSS=7
```
