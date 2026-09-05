ALTER TABLE transacao_credito
    ADD COLUMN payment_transaction_id UUID REFERENCES payment_transaction(id);

ALTER TABLE transacao_credito
    DROP CONSTRAINT ck_transacao_compra;

ALTER TABLE transacao_credito
    ADD CONSTRAINT ck_transacao_compra CHECK (
        tipo <> 'COMPRA'
        OR (compra_credito_id IS NOT NULL OR payment_transaction_id IS NOT NULL)
    );

CREATE UNIQUE INDEX uk_transacao_payment_transaction_compra
    ON transacao_credito (payment_transaction_id)
    WHERE tipo = 'COMPRA' AND payment_transaction_id IS NOT NULL;
