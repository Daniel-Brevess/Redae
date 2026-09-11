# Área administrativa

## Objetivo

Criar uma área administrativa do Redaê para acompanhar usuários, créditos,
cupons e pacotes comerciais. A área será acessível somente por usuários com o
papel `ADMIN`.

## Acesso e segurança

- A tela administrativa não deve aparecer para usuários comuns.
- O backend deve validar o papel `ADMIN` em todas as rotas administrativas.
- Ocultar o link no frontend não substitui a autorização no backend.
- Usuários sem permissão devem receber `403`.
- Consultas e alterações administrativas devem respeitar o usuário autenticado
  e registrar o administrador responsável quando houver alteração.
- Nenhum dado sensível, segredo ou credencial deve ser exibido sem necessidade.

## Funcionalidades previstas

### 1. Painel administrativo

O administrador poderá acessar uma tela própria com indicadores resumidos:

- quantidade total de usuários cadastrados;
- quantidade de créditos por usuário;
- cupom mais utilizado;
- pacote mais comprado.

Os indicadores devem ser obtidos do backend e não calculados exclusivamente no
frontend.

### 2. Usuários

O administrador poderá:

- visualizar a lista de usuários cadastrados;
- consultar dados básicos do usuário;
- consultar a quantidade de créditos disponível para cada usuário.

O escopo inicial não inclui edição, exclusão ou alteração de dados dos usuários.

### 3. Cupons

O administrador poderá criar cupons para uso nas compras de créditos.

Cada cupom deverá possuir, conforme definição posterior do contrato:

- código;
- tipo e valor do benefício;
- validade;
- limite de usos, quando aplicável;
- status ativo ou inativo.

A aplicação do cupom deverá ser validada pelo backend. O frontend não poderá
definir o desconto final nem modificar as regras do cupom.

### 4. Pacotes de créditos

O administrador poderá criar pacotes de créditos para disponibilização aos
usuários.

Cada pacote deverá permitir configurar, conforme definição posterior:

- nome;
- quantidade de créditos;
- bônus, quando aplicável;
- preço;
- moeda;
- período de disponibilidade;
- status ativo ou inativo.

O preço e a quantidade final de créditos devem ser determinados pelo backend.

### 5. Métricas comerciais

O administrador poderá consultar:

- qual cupom foi mais utilizado;
- qual pacote foi mais comprado.

As métricas devem considerar apenas transações válidas, conforme as regras de
pagamento definidas para o produto. Compras pendentes, canceladas ou falhas não
devem ser contabilizadas como concluídas.

## Organização prevista

As funcionalidades administrativas devem permanecer no monólito modular atual,
reutilizando os módulos existentes e mantendo as responsabilidades separadas:

- `controller`: endpoints administrativos;
- `service`: regras de negócio e autorização de operações;
- `repository`: consultas de usuários, créditos, cupons e pacotes;
- `dto`: contratos de entrada e saída;
- `entity`: cupons, pacotes e demais entidades persistentes necessárias.

O frontend deverá possuir uma rota ou área administrativa protegida, com
componentes próprios para indicadores, usuários, cupons e pacotes.

## Pendências para implementação

- definir os campos finais e as regras de desconto dos cupons;
- definir se o código do cupom ignora maiúsculas e minúsculas;
- definir limites de uso por usuário e limite global;
- definir se pacotes podem ser editados após a primeira venda;
- definir regras para desativação de cupons e pacotes;
- definir filtros, paginação e ordenação da lista de usuários;
- definir quais dados básicos dos usuários serão exibidos;
- definir o período das métricas comerciais;
- definir se as métricas serão totais ou filtráveis por período;
- documentar os contratos HTTP no OpenAPI;
- criar testes de autorização, validação, persistência e métricas;
- validar as migrations em banco limpo.

## Critérios de conclusão da etapa

- somente administradores conseguem acessar a área administrativa;
- usuários comuns não visualizam nem acessam os recursos administrativos;
- o total de usuários é exibido corretamente;
- a lista de usuários apresenta os créditos correspondentes;
- cupons podem ser criados com validação no backend;
- pacotes podem ser criados com validação no backend;
- cupom mais usado e pacote mais comprado são calculados com dados válidos;
- alterações administrativas possuem auditoria quando aplicável;
- testes e verificações de formatação passam no CI;
- documentação e contratos permanecem sincronizados com o código.

## Plano de implementação por tarefas

As tarefas devem ser executadas na ordem e validadas individualmente.

### 1. Base de autorização

- [x] Confirmar o papel `ADMIN` persistido e carregado pelo JWT.
- [x] Criar regra reutilizável para proteger endpoints administrativos.
- [x] Garantir `403` para usuários `STUDENT`.
- [x] Criar testes de autorização para `ADMIN` e `STUDENT`.

**Resultado:** somente administradores podem acessar recursos administrativos.

### 2. Rota e layout administrativo

- [x] Criar rota administrativa no frontend.
- [x] Exibir o link somente para `ADMIN`.
- [x] Bloquear acesso direto de usuários comuns.
- [x] Criar estados de carregamento, erro e acesso negado.

**Resultado:** o administrador possui uma área própria e protegida.

### 3. Contagem de usuários

- [x] Criar endpoint administrativo para contar usuários.
- [x] Criar service e consulta no repository.
- [x] Exibir o total no painel.
- [x] Testar com usuários fictícios.

**Resultado:** o painel mostra a quantidade de usuários cadastrados.

### 4. Lista de usuários

- [x] Definir os dados básicos exibidos.
- [x] Criar DTO e endpoint paginado.
- [x] Implementar ordenação e estado vazio.
- [x] Exibir a lista no frontend.
- [x] Não retornar senhas, tokens ou dados desnecessários.

**Resultado:** o administrador consulta usuários com segurança e paginação.

### 5. Créditos por usuário

- [x] Reutilizar o cálculo oficial do ledger.
- [x] Disponibilizar o saldo no DTO ou endpoint administrativo.
- [x] Exibir os créditos na lista.
- [x] Testar compras, consumos e estornos.

**Resultado:** cada usuário aparece com seu saldo correto.

### 6. Créditos manuais para testers

Antes do deploy, o administrador poderá conceder créditos manualmente para
contas de testers pela área administrativa.

- [ ] Criar busca administrativa de usuários por nome ou e-mail.
- [ ] Exibir os resultados em cards com dados básicos e saldo atual.
- [ ] Adicionar ação protegida para informar a quantidade de créditos.
- [ ] Validar que a quantidade seja inteira e positiva.
- [ ] Reutilizar o ledger, registrando a concessão como transação auditável.
- [ ] Registrar o administrador responsável e a data da operação.
- [ ] Atualizar o saldo exibido após a concessão.
- [ ] Garantir `403` para usuários sem o papel `ADMIN`.
- [ ] Testar busca, concessão, saldo atualizado, valores inválidos e autorização.

**Resultado:** o administrador consegue preparar contas de testers sem alterar
diretamente o banco de dados, e cada concessão permanece rastreável.

---

## Funcionalidades pós-deploy

As tarefas abaixo ficam adiadas para depois do primeiro deploy e da validação
com testers.

### 7. Modelo de cupons

- [ ] Definir código, benefício, validade e limites.
- [ ] Criar entity, enum, repository e migration.
- [ ] Validar unicidade, datas e status.
- [ ] Criar testes de persistência e validação.

**Resultado:** cupons podem ser armazenados de forma consistente.

### 8. Criação de cupons

- [ ] Criar DTO, service e endpoint `POST` administrativo.
- [ ] Garantir que somente `ADMIN` possa criar.
- [ ] Criar formulário no frontend.
- [ ] Exibir mensagens de sucesso e erro.

**Resultado:** o administrador cria cupons pela área administrativa.

### 9. Modelo de pacotes

- [ ] Definir nome, créditos, bônus, preço, moeda e disponibilidade.
- [ ] Reutilizar ou adaptar as entidades de oferta existentes.
- [ ] Criar ou ajustar migration e repository.
- [ ] Validar preço, quantidade e status.

**Resultado:** pacotes podem ser persistidos e validados.

### 10. Criação de pacotes

- [ ] Criar DTO, service e endpoint administrativo.
- [ ] Criar formulário no frontend.
- [ ] Permitir ativação ou desativação conforme regra definida.
- [ ] Garantir que o preço final venha do backend.

**Resultado:** o administrador cria pacotes para venda.

### 11. Métricas comerciais

- [ ] Definir período padrão e filtros.
- [ ] Consultar cupons usados em pagamentos válidos.
- [ ] Consultar pacotes comprados em pagamentos válidos.
- [ ] Criar endpoint e cards no painel.
- [ ] Testar empates, ausência de dados e pagamentos pendentes.

**Resultado:** o painel mostra o cupom mais usado e o pacote mais comprado.

### 12. Testes e fechamento

- [ ] Atualizar `docs/api.md` e `docs/api.openapi.yaml`.
- [ ] Atualizar arquitetura e decisões quando necessário.
- [ ] Criar testes frontend da área administrativa.
- [ ] Executar testes, lint, typecheck, build e formatação.
- [ ] Validar migrations em banco limpo.
- [ ] Revisar autorização, auditoria e dados expostos.

**Resultado:** a área administrativa fica pronta para revisão manual.

## Ordem resumida

1. Autorização.
2. Rota e layout.
3. Contagem de usuários.
4. Lista de usuários.
5. Créditos.
6. Créditos manuais para testers.

### Após o deploy

7. Modelo de cupons.
8. Criação de cupons.
9. Modelo de pacotes.
10. Criação de pacotes.
11. Métricas comerciais.
12. Testes e fechamento.
