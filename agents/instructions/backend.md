# Instruções de backend

- Preserve Java 21, Spring Boot e Maven.
- Não crie camadas, packages por feature, entidades ou endpoints sem requisito definido.
- Mantenha configuração externa por variáveis de ambiente e nunca versione credenciais.
- Não transforme dependências preparadas em funcionalidades: JWT, PostgreSQL, Resend e OpenAPI só devem ser implementados quando solicitados.
- Execute `mvn test` e `mvn package` após alterações relevantes no backend.
