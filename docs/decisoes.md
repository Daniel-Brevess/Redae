# Decisões do projeto

Este arquivo é a referência rápida das decisões vigentes. As decisões antigas foram consolidadas por tema para reduzir ruído e manter o histórico compreensível. Quando uma decisão foi substituída, vale o estado atual descrito abaixo.

## Arquitetura e stack

- Monólito modular no MVP.
- Camadas organizadas dentro de módulos de contexto.
- Módulos atuais: `user`, `auth`, `evaluation`, `ai` e `shared`.
- Processamento pertence a `evaluation`; clientes de IA pertencem a `ai`.
- Frontend em React, TypeScript, Vite e Tailwind CSS.
- PostgreSQL com Flyway como fonte persistente.
- Docker Compose para ambiente local.

## Produto e avaliação

- A redação é persistida quando confirmada.
- O texto completo permanece disponível no histórico autorizado.
- A avaliação é o agregado principal e possui notas C1–C5 e feedbacks.
- A nota final é calculada pelo backend a partir das competências.
- Diagnóstico e avaliação completa são tipos distintos.
- O primeiro diagnóstico é controlado pelo backend; a avaliação completa depende da regra de créditos.
- Uma avaliação concluída é imutável.
- Resposta inválida da IA não gera resultado concluído.

## Créditos e pagamentos

- O saldo é derivado de um ledger de transações.
- Consumo ocorre após a confirmação da avaliação.
- Compras e consumo são idempotentes.
- O backend é a fonte dos preços e ofertas.
- Pagamentos são confirmados por webhook autenticado.
- Ajustes administrativos exigem motivo e auditoria.

## Segurança e dados

- JWT é usado para autenticação; refresh fica em cookie protegido.
- A autorização considera proprietário e papel administrativo.
- Secrets ficam fora do repositório.
- Logs não contêm conteúdo sensível.
- UUIDs e timestamps UTC são usados nos dados persistentes.
- Migrations aplicadas não são alteradas; correções usam nova versão.

## IA e calibração

- A aplicação usa `AIClient` para desacoplar o provedor.
- O Gemini é o provedor atual de teste.
- O prompt deve avaliar C1–C5 de forma independente, exigir evidências literais e explicar descontos relevantes.
- A calibração usa redações de referência com notas oficiais e compara coerência entre competências, não apenas proximidade da nota total.
- A futura troca de provedor será feita por novo adaptador, mantendo o contrato.

## Histórico consolidado

As decisões numeradas anteriores cobriram: monólito e stack (0001–0018), domínio e fluxo de avaliação (0019–0044), créditos e pagamentos (0045–0073), API e operação (0074–0079), calibração e provedor futuro (0080–0081) e arquitetura modular atual (0082). O conteúdo normativo vigente desses grupos está refletido neste documento e nos documentos `produto.md`, `arquitetura.md` e `api.md`.
