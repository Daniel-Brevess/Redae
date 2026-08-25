+CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE usuario (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(160) NOT NULL,
    email VARCHAR(320) NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_usuario_email UNIQUE (email),
    CONSTRAINT ck_usuario_tipo CHECK (tipo_usuario IN ('STUDENT', 'ADMIN'))
);

CREATE TABLE avaliacao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID NOT NULL REFERENCES usuario(id),
    texto_confirmado TEXT NOT NULL,
    tema VARCHAR(500) NOT NULL,
    origem VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    nota_final INTEGER,
    versao VARCHAR(80) NOT NULL,
    modelo_ia VARCHAR(160) NOT NULL,
    gerada_em TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_avaliacao_origem CHECK (origem IN ('DIGITADA', 'IMAGEM')),
    CONSTRAINT ck_avaliacao_status CHECK (status IN ('PENDENTE', 'PROCESSANDO', 'CONCLUIDA', 'FALHOU')),
    CONSTRAINT ck_avaliacao_nota CHECK (nota_final IS NULL OR nota_final BETWEEN 0 AND 1000)
);

CREATE TABLE nota_competencia (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    avaliacao_id UUID NOT NULL REFERENCES avaliacao(id) ON DELETE CASCADE,
    competencia_codigo VARCHAR(2) NOT NULL,
    nivel INTEGER NOT NULL,
    pontos INTEGER NOT NULL,
    resumo TEXT NOT NULL,
    CONSTRAINT uk_nota_competencia UNIQUE (avaliacao_id, competencia_codigo),
    CONSTRAINT ck_nota_competencia_codigo CHECK (competencia_codigo IN ('C1', 'C2', 'C3', 'C4', 'C5')),
    CONSTRAINT ck_nota_competencia_nivel CHECK (nivel BETWEEN 0 AND 5),
    CONSTRAINT ck_nota_competencia_pontos CHECK (pontos BETWEEN 0 AND 200)
);

CREATE TABLE feedback_item (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nota_competencia_id UUID NOT NULL REFERENCES nota_competencia(id) ON DELETE CASCADE,
    trecho TEXT,
    problema TEXT NOT NULL,
    explicacao TEXT NOT NULL,
    como_melhorar TEXT NOT NULL,
    limitacao TEXT
);

CREATE TABLE oferta_credito (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(160) NOT NULL,
    creditos_inclusos INTEGER NOT NULL,
    bonus_creditos INTEGER NOT NULL DEFAULT 0,
    preco NUMERIC(12, 2) NOT NULL,
    moeda VARCHAR(3) NOT NULL DEFAULT 'BRL',
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    vigencia_inicio TIMESTAMPTZ,
    vigencia_fim TIMESTAMPTZ,
    limite_de_uso INTEGER,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_oferta_creditos CHECK (creditos_inclusos > 0),
    CONSTRAINT ck_oferta_bonus CHECK (bonus_creditos >= 0),
    CONSTRAINT ck_oferta_preco CHECK (preco >= 0),
    CONSTRAINT ck_oferta_moeda CHECK (moeda = 'BRL'),
    CONSTRAINT ck_oferta_limite CHECK (limite_de_uso IS NULL OR limite_de_uso > 0),
    CONSTRAINT ck_oferta_vigencia CHECK (vigencia_fim IS NULL OR vigencia_inicio IS NULL OR vigencia_fim > vigencia_inicio)
);

CREATE TABLE preco_credito (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    valor_por_credito NUMERIC(12, 2) NOT NULL,
    moeda VARCHAR(3) NOT NULL DEFAULT 'BRL',
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    vigente_desde TIMESTAMPTZ NOT NULL,
    vigente_ate TIMESTAMPTZ,
    administrador_id UUID NOT NULL REFERENCES usuario(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_preco_valor CHECK (valor_por_credito >= 0),
    CONSTRAINT ck_preco_moeda CHECK (moeda = 'BRL'),
    CONSTRAINT ck_preco_vigencia CHECK (vigente_ate IS NULL OR vigente_ate > vigente_desde)
);

CREATE TABLE compra_credito (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID NOT NULL REFERENCES usuario(id),
    oferta_credito_id UUID REFERENCES oferta_credito(id),
    preco_credito_id UUID NOT NULL REFERENCES preco_credito(id),
    referencia_externa VARCHAR(255),
    status VARCHAR(20) NOT NULL,
    quantidade_creditos INTEGER NOT NULL,
    bonus_creditos INTEGER NOT NULL DEFAULT 0,
    creditos_totais INTEGER NOT NULL,
    valor NUMERIC(12, 2) NOT NULL,
    moeda VARCHAR(3) NOT NULL DEFAULT 'BRL',
    paga_em TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_compra_referencia UNIQUE (referencia_externa),
    CONSTRAINT ck_compra_status CHECK (status IN ('CRIADA', 'PENDENTE', 'PAGA', 'CANCELADA', 'FALHOU', 'ESTORNADA')),
    CONSTRAINT ck_compra_quantidade CHECK (quantidade_creditos > 0),
    CONSTRAINT ck_compra_bonus CHECK (bonus_creditos >= 0),
    CONSTRAINT ck_compra_total CHECK (creditos_totais = quantidade_creditos + bonus_creditos),
    CONSTRAINT ck_compra_valor CHECK (valor >= 0),
    CONSTRAINT ck_compra_moeda CHECK (moeda = 'BRL')
);

CREATE TABLE transacao_credito (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID NOT NULL REFERENCES usuario(id),
    compra_credito_id UUID REFERENCES compra_credito(id),
    avaliacao_id UUID REFERENCES avaliacao(id),
    tipo VARCHAR(20) NOT NULL,
    quantidade INTEGER NOT NULL,
    referencia_externa VARCHAR(255),
    motivo TEXT,
    administrador_id UUID REFERENCES usuario(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_transacao_tipo CHECK (tipo IN ('COMPRA', 'CONCESSAO', 'CONSUMO', 'ESTORNO', 'AJUSTE')),
    CONSTRAINT ck_transacao_quantidade CHECK (quantidade > 0),
    CONSTRAINT ck_transacao_ajuste CHECK (tipo <> 'AJUSTE' OR (motivo IS NOT NULL AND administrador_id IS NOT NULL)),
    CONSTRAINT ck_transacao_compra CHECK (tipo NOT IN ('COMPRA', 'ESTORNO') OR compra_credito_id IS NOT NULL),
    CONSTRAINT ck_transacao_consumo CHECK (tipo <> 'CONSUMO' OR avaliacao_id IS NOT NULL)
);

CREATE UNIQUE INDEX uk_transacao_referencia_externa ON transacao_credito (referencia_externa) WHERE referencia_externa IS NOT NULL;
CREATE INDEX ix_avaliacao_usuario_created_at ON avaliacao (usuario_id, created_at DESC);
CREATE INDEX ix_avaliacao_status ON avaliacao (status);
CREATE INDEX ix_nota_competencia_avaliacao ON nota_competencia (avaliacao_id);
CREATE INDEX ix_feedback_nota_competencia ON feedback_item (nota_competencia_id);
CREATE INDEX ix_compra_usuario_created_at ON compra_credito (usuario_id, created_at DESC);
CREATE INDEX ix_transacao_usuario_created_at ON transacao_credito (usuario_id, created_at DESC);

