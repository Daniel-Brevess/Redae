# Backend

Aplicação Spring Boot do Redaê, organizada como monólito modular.

## Estrutura

Cada módulo de domínio possui camadas próprias:

```text
src/main/java/br/com/redae/
├── identity/     # estudantes, administradores e perfis
├── auth/         # autenticação, sessões e autorização
├── essays/       # redações e ciclo de vida
├── processing/   # processamento técnico temporário
├── evaluation/   # notas e feedback
├── history/      # histórico e progresso
└── support/      # suporte ao estudante
```

As camadas são `controller`, `service`, `repository`, `dto`, `entity` e `config`.
Os pacotes estão preparados nesta fase; endpoints e regras de negócio serão
implementados nas próximas fatias verticais.

## Banco local

O ambiente local usa PostgreSQL via Docker Compose. Os valores de configuração
devem ser fornecidos pelo ambiente local ou por um gerenciador de secrets. O
repositório não contém arquivos de exemplo, senhas ou valores padrão.

As variáveis necessárias para o Compose são `POSTGRES_DB`, `POSTGRES_USER` e
`POSTGRES_PASSWORD`. O backend também recebe `DB_URL`, `DB_USERNAME` e
`DB_PASSWORD` quando executado fora do Compose.

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
