ALTER TABLE transacao_credito
    DROP CONSTRAINT ck_transacao_compra;

ALTER TABLE transacao_credito
    ADD CONSTRAINT ck_transacao_compra CHECK (
        tipo <> 'COMPRA'
        OR (compra_credito_id IS NOT NULL OR payment_transaction_id IS NOT NULL)
    );

ALTER TABLE transacao_credito
    ADD CONSTRAINT ck_transacao_estorno CHECK (
        tipo <> 'ESTORNO'
        OR (compra_credito_id IS NOT NULL
            OR payment_transaction_id IS NOT NULL
            OR avaliacao_id IS NOT NULL)
    );
