CREATE TABLE sessao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    refresh_token_hash VARCHAR(255) NOT NULL UNIQUE,
    expires_at TIMESTAMPTZ NOT NULL,
    status VARCHAR(20) NOT NULL,
    revoked_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_used_at TIMESTAMPTZ,
    CONSTRAINT ck_sessao_status CHECK (status IN ('ACTIVE', 'REVOKED'))
);

CREATE INDEX ix_sessao_usuario ON sessao (usuario_id);
CREATE INDEX ix_sessao_expires_at ON sessao (expires_at);
