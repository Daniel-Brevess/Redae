UPDATE payment_transaction
SET provider = 'STRIPE'
WHERE provider = 'MERCADO_PAGO';

ALTER TABLE payment_transaction
    DROP CONSTRAINT ck_payment_transaction_provider;

ALTER TABLE payment_transaction
    ADD CONSTRAINT ck_payment_transaction_provider CHECK (provider IN ('STRIPE'));
