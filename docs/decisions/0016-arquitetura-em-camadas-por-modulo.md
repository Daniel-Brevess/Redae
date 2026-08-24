# ADR 0016 — Arquitetura em camadas por módulo

- **Status:** aceito
- **Data:** 2026-08-24

## Contexto

O backend será um monólito modular em Spring Boot. A equipe prefere uma organização familiar baseada em camadas técnicas, como controller, service, repository, DTO, entity e config.

## Decisão

Cada módulo do monólito será organizado internamente por camadas:

- `controller`: endpoints HTTP e entrada de requisições;
- `service`: casos de uso, orquestração e regras de aplicação;
- `repository`: persistência e consultas do próprio módulo;
- `dto`: objetos de entrada e saída da API;
- `entity`: entidades persistidas e seus mapeamentos;
- `config`: configurações específicas do módulo, quando necessárias.

A estrutura será orientada primeiro pelo módulo e depois pela camada. Exemplos: `identity/service`, `essays/controller` e `evaluation/repository`.

Um módulo poderá consumir services, casos de uso ou interfaces públicas de outro módulo. Não poderá acessar diretamente entities, repositories ou detalhes internos de outro módulo. Integrações externas, como IA, ficarão atrás de interfaces próprias do módulo responsável.

## Consequências

- reduz a curva de aprendizado e combina com o modelo de desenvolvimento em Spring Boot;
- mantém os módulos separados mesmo usando camadas técnicas;
- exige disciplina para evitar services ou entities compartilhados sem contrato;
- permite evoluir partes específicas sem transformar o MVP em microservices.

## Fora desta decisão

Esta ADR não define a lista final de classes ou pacotes de cada módulo. Isso será detalhado durante a fundação implementável.
