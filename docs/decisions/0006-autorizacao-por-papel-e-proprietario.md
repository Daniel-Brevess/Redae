# ADR 0006 — Autorização por papel e proprietário do recurso

- **Status:** aceito
- **Data:** 2026-08-24

## Contexto

O sistema possui dois atores principais: estudante e administrador. As redações, avaliações e históricos pertencem a estudantes e não podem ser expostos apenas porque alguém conhece um identificador ou URL.

## Decisão

O backend aplicará autorização por papel e por proprietário do recurso:

- `STUDENT`: pode consultar e alterar o próprio perfil, redações, rascunhos e demais dados permitidos do próprio histórico;
- `ADMIN`: pode consultar estudantes, redações, avaliações, histórico de notas e falhas operacionais de OCR/IA;
- `ADMIN` não poderá corrigir, editar ou alterar redações, notas e avaliações por meio das capacidades do MVP;
- nenhum estudante poderá consultar ou alterar dados pertencentes a outro estudante;
- a autorização será verificada no backend em toda operação protegida, independentemente do que a interface exibir.

## Consequências

- reduz o risco de exposição ou alteração indevida de dados;
- mantém a separação entre uso pedagógico do estudante e operação administrativa;
- exige testes de autorização para acesso permitido, acesso negado e tentativa de troca de identificador;
- consultas administrativas precisarão ser somente leitura quando envolverem dados acadêmicos.

## Fora desta decisão

Esta ADR não define permissões futuras para professores, avaliadores humanos ou suporte avançado.
