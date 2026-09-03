# ADR 0082: Organização modular atual do backend

- **Status:** Aceita
- **Data:** 2026-09-03

## Contexto

O backend começou com pacotes de domínio e pacotes reservados para funcionalidades futuras. Com a evolução do MVP, essa estrutura passou a misturar módulos efetivamente usados com diretórios vazios e deixou os clientes de IA junto do serviço de avaliação.

## Decisão

Manter o backend como monólito modular organizado por contexto, com camadas internas:

- `user`: identidade, usuários, perfis e papéis;
- `auth`: autenticação, sessões e autorização;
- `evaluation`: ciclo de vida da redação, processamento, notas e feedback;
- `ai`: contrato `AIClient` e adaptadores dos provedores de IA;
- `shared`: componentes transversais.

O antigo módulo `identity` passa a se chamar `user`. Os diretórios reservados e ainda vazios (`essays`, `history`, `processing` e `support`) são removidos. O processamento permanece dentro de `evaluation`, e os clientes de provedores ficam em `ai/client`.

## Consequências

- A avaliação depende do contrato de `ai`, e não diretamente da implementação de um provedor.
- A estrutura fica alinhada aos contextos que já possuem código executável.
- Esta mudança é estrutural: endpoints, regras de negócio, persistência, migrations e contratos públicos permanecem inalterados.
- ADRs anteriores continuam descrevendo decisões históricas e não são reescritos.
