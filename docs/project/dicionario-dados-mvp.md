# Dicionário de dados inicial do MVP

Este dicionário consolida as entidades persistentes confirmadas na fase 3. Tipos são apresentados em termos do PostgreSQL; UUIDs e timestamps seguem as convenções das ADRs 0030 e 0041.

## Usuario

| Campo | Tipo | Obrigatório | Regra/origem |
| --- | --- | --- | --- |
| `id` | UUID | sim | gerado pelo sistema |
| `nome` | VARCHAR | sim | informado no cadastro |
| `email` | VARCHAR | sim | normalizado para minúsculas e único |
| `senha_hash` | VARCHAR | sim | produzido pelo backend; nunca senha em claro |
| `tipo_usuario` | VARCHAR | sim | `STUDENT` ou `ADMIN` |
| `created_at` | TIMESTAMPTZ | sim | gerado pelo sistema em UTC |
| `updated_at` | TIMESTAMPTZ | sim | atualizado pelo sistema em UTC |

## Avaliacao

| Campo | Tipo | Obrigatório | Regra/origem |
| --- | --- | --- | --- |
| `id` | UUID | sim | gerado ao confirmar o envio |
| `usuario_id` | UUID | sim | FK para `Usuario` |
| `texto_confirmado` | TEXT | sim | texto confirmado pelo estudante |
| `tema` | VARCHAR(500) | sim | informado livremente pelo estudante |
| `origem` | VARCHAR | sim | `DIGITADA` ou `IMAGEM` |
| `status` | VARCHAR | sim | ciclo da avaliação |
| `nota_final` | INTEGER | não | nula até conclusão; 0–1000 |
| `versao` | VARCHAR | sim | versão do contrato/rubrica |
| `modelo_ia` | VARCHAR | sim | modelo usado na geração |
| `gerada_em` | TIMESTAMPTZ | não | preenchida na conclusão |
| `created_at` | TIMESTAMPTZ | sim | UTC |
| `updated_at` | TIMESTAMPTZ | sim | UTC |

## NotaCompetencia

| Campo | Tipo | Obrigatório | Regra/origem |
| --- | --- | --- | --- |
| `id` | UUID | sim | gerado pelo sistema |
| `avaliacao_id` | UUID | sim | FK para `Avaliacao` |
| `competencia_codigo` | VARCHAR(2) | sim | `C1` a `C5`; único por avaliação |
| `nivel` | INTEGER | sim | 0–5, retornado pela IA e validado |
| `pontos` | INTEGER | sim | calculado pelo backend: 0–200 |
| `resumo` | TEXT | sim | resposta validada da IA |

## FeedbackItem

| Campo | Tipo | Obrigatório | Regra/origem |
| --- | --- | --- | --- |
| `id` | UUID | sim | gerado pelo sistema |
| `nota_competencia_id` | UUID | sim | FK para `NotaCompetencia` |
| `trecho` | TEXT | não | deve existir no texto quando preenchido |
| `problema` | TEXT | sim | resposta validada da IA |
| `explicacao` | TEXT | sim | resposta validada da IA |
| `como_melhorar` | TEXT | sim | resposta validada da IA |
| `limitacao` | TEXT | não | limitação da evidência ou análise |

## CompraCredito

| Campo | Tipo | Obrigatório | Regra/origem |
| --- | --- | --- | --- |
| `id` | UUID | sim | gerado pelo sistema |
| `usuario_id` | UUID | sim | FK para `Usuario` |
| `oferta_credito_id` | UUID | não | FK quando compra usa oferta |
| `preco_credito_id` | UUID | sim | FK para preço vigente usado |
| `referencia_externa` | VARCHAR | não | identificador AbacatePay; único quando preenchido |
| `status` | VARCHAR | sim | ciclo do pagamento |
| `quantidade_creditos` | INTEGER | sim | quantidade comprada |
| `bonus_creditos` | INTEGER | sim | bônus aplicado |
| `creditos_totais` | INTEGER | sim | quantidade + bônus |
| `valor` | NUMERIC(12,2) | sim | calculado pelo backend |
| `moeda` | VARCHAR(3) | sim | `BRL` |
| `paga_em` | TIMESTAMPTZ | não | preenchida por confirmação |
| `created_at` | TIMESTAMPTZ | sim | UTC |
| `updated_at` | TIMESTAMPTZ | sim | UTC |

## OfertaCredito

| Campo | Tipo | Obrigatório | Regra/origem |
| --- | --- | --- | --- |
| `id` | UUID | sim | gerado pelo sistema |
| `nome` | VARCHAR | sim | definido pelo administrador |
| `creditos_inclusos` | INTEGER | sim | positivo |
| `bonus_creditos` | INTEGER | sim | zero ou positivo |
| `preco` | NUMERIC(12,2) | sim | em BRL |
| `moeda` | VARCHAR(3) | sim | `BRL` |
| `ativo` | BOOLEAN | sim | controlado pelo administrador |
| `vigencia_inicio` | TIMESTAMPTZ | não | início da oferta |
| `vigencia_fim` | TIMESTAMPTZ | não | fim da oferta |
| `limite_de_uso` | INTEGER | não | limite opcional |
| `created_at` | TIMESTAMPTZ | sim | UTC |
| `updated_at` | TIMESTAMPTZ | sim | UTC |

## PrecoCredito

| Campo | Tipo | Obrigatório | Regra/origem |
| --- | --- | --- | --- |
| `id` | UUID | sim | gerado pelo sistema |
| `valor_por_credito` | NUMERIC(12,2) | sim | administrado pelo `ADMIN` |
| `moeda` | VARCHAR(3) | sim | `BRL` |
| `ativo` | BOOLEAN | sim | uma versão vigente por vez |
| `vigente_desde` | TIMESTAMPTZ | sim | início da validade |
| `vigente_ate` | TIMESTAMPTZ | não | fim da validade |
| `administrador_id` | UUID | sim | FK para `Usuario` admin |
| `created_at` | TIMESTAMPTZ | sim | UTC |
| `updated_at` | TIMESTAMPTZ | sim | UTC |

## TransacaoCredito

| Campo | Tipo | Obrigatório | Regra/origem |
| --- | --- | --- | --- |
| `id` | UUID | sim | gerado pelo sistema |
| `usuario_id` | UUID | sim | FK para `Usuario` |
| `compra_credito_id` | UUID | não | obrigatório para compra/estorno |
| `avaliacao_id` | UUID | não | obrigatório para consumo |
| `tipo` | VARCHAR | sim | `COMPRA`, `CONCESSAO`, `CONSUMO`, `ESTORNO` ou `AJUSTE` |
| `quantidade` | INTEGER | sim | positivo; efeito depende do tipo |
| `referencia_externa` | VARCHAR | não | evento externo ou referência operacional |
| `motivo` | TEXT | condicional | obrigatório em ajuste |
| `administrador_id` | UUID | condicional | obrigatório em ajuste |
| `created_at` | TIMESTAMPTZ | sim | UTC |
| `updated_at` | TIMESTAMPTZ | sim | UTC |
