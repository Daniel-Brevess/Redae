# ADR 0005 — Autenticação por JWT em cookies protegidos

- **Status:** aceito
- **Data:** 2026-08-24

## Contexto

O Redaê terá uma aplicação web com área pública e área privada. O mecanismo de autenticação precisa funcionar com o backend Spring Boot, proteger as credenciais no navegador e permitir expiração e logout controlados.

## Decisão

O backend emitirá dois tokens:

- um access token JWT de curta duração para autenticar as requisições;
- um refresh token de duração maior, controlado pelo backend para renovar o access token.

Ambos serão enviados em cookies com as flags `HttpOnly` e `Secure`. A política de `SameSite`, domínio e proteção contra CSRF será definida junto da configuração dos ambientes e da implementação das rotas.

O access token terá validade curta. O refresh token terá validade configurável, será associado à sessão do usuário e poderá ser revogado. No logout, o backend revogará o refresh token e o frontend limpará o estado local de autenticação.

## Consequências

### Positivas

- tokens não ficam acessíveis diretamente ao JavaScript da aplicação;
- expiração curta reduz o impacto de um access token comprometido;
- refresh token revogável permite logout e encerramento de sessões;
- a estratégia é compatível com a área privada planejada.

### Negativas e controles

- cookies exigem configuração correta de CSRF, `SameSite`, domínio e HTTPS;
- refresh tokens precisam ser armazenados ou controlados com segurança no backend;
- rotação, reutilização e revogação de refresh tokens serão detalhadas na implementação;
- autorização por papel e isolamento dos dados serão definidos separadamente.

## Fora desta decisão

Esta ADR não define os tempos exatos de expiração, o modelo final de persistência das sessões nem as permissões detalhadas de estudante e administrador.
