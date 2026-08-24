# ADR 0017 — Módulos do monólito do MVP

- **Status:** aceito
- **Data:** 2026-08-24

## Decisão

O monólito Spring Boot será dividido nos módulos:

- `identity`: estudantes, administradores, perfis e papéis;
- `auth`: login, senhas, tokens, sessões e autorização;
- `essays`: redações, rascunhos, envio e ciclo de vida;
- `processing`: upload temporário, OCR, trabalhos assíncronos e estados de processamento;
- `evaluation`: chamadas de avaliação, notas e feedback por competência;
- `history`: histórico de atividades, avaliações e progresso;
- `support`: reclamações, dúvidas e parabenizações.

Cada módulo terá suas próprias camadas técnicas e será responsável por seus dados e regras. O acesso entre módulos ocorrerá por services ou interfaces públicas. Repositories, entities e detalhes internos não serão acessados diretamente por outros módulos.

## Consequências

- deixa explícitos os limites funcionais do MVP;
- facilita testes por módulo e evolução futura;
- exige evitar dependências circulares;
- permite separar um módulo no futuro somente se houver necessidade comprovada.
