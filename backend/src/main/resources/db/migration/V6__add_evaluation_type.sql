ALTER TABLE avaliacao ADD COLUMN tipo VARCHAR(20);

WITH ordered_evaluations AS (
    SELECT
        id,
        ROW_NUMBER() OVER (PARTITION BY usuario_id ORDER BY created_at, id) AS position
    FROM avaliacao
)
UPDATE avaliacao
SET tipo = CASE
    WHEN ordered_evaluations.position = 1 THEN 'DIAGNOSTICO'
    ELSE 'COMPLETA'
END
FROM ordered_evaluations
WHERE avaliacao.id = ordered_evaluations.id;

ALTER TABLE avaliacao ALTER COLUMN tipo SET NOT NULL;
ALTER TABLE avaliacao ADD CONSTRAINT ck_avaliacao_tipo CHECK (tipo IN ('DIAGNOSTICO', 'COMPLETA'));
CREATE UNIQUE INDEX uk_avaliacao_usuario_diagnostico
    ON avaliacao (usuario_id)
    WHERE tipo = 'DIAGNOSTICO';
