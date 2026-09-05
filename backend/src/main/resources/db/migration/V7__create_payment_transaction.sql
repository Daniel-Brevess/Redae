CREATE TABLE payment_transaction (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID NOT NULL REFERENCES usuario(id),
    referencia_externa VARCHAR(255) UNIQUE,
    status VARCHAR(20) NOT NULL,
    provider VARCHAR(30) NOT NULL,
    quantidade_creditos INTEGER NOT NULL,
    bonus_creditos INTEGER NOT NULL DEFAULT 0,
    creditos_totais INTEGER NOT NULL,
    valor NUMERIC(12, 2) NOT NULL,
    moeda VARCHAR(3) NOT NULL DEFAULT 'BRL',
    paga_em TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_payment_transaction_status CHECK (
        status IN ('CRIADA', 'PENDENTE', 'PAGA', 'CANCELADA', 'FALHOU', 'ESTORNADA')
    ),
    CONSTRAINT ck_payment_transaction_provider CHECK (provider IN ('MERCADO_PAGO')),
    CONSTRAINT ck_payment_transaction_quantity CHECK (quantidade_creditos > 0),
    CONSTRAINT ck_payment_transaction_bonus CHECK (bonus_creditos >= 0),
    CONSTRAINT ck_payment_transaction_total CHECK (
        creditos_totais = quantidade_creditos + bonus_creditos
    ),
    CONSTRAINT ck_payment_transaction_amount CHECK (valor >= 0),
    CONSTRAINT ck_payment_transaction_currency CHECK (moeda = 'BRL')
);

CREATE INDEX ix_payment_transaction_user_created_at
    ON payment_transaction (usuario_id, created_at DESC);

CREATE INDEX ix_payment_transaction_status
    ON payment_transaction (status);
