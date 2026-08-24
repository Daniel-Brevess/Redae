# Glossário do domínio do MVP

## Avaliação

Registro persistente principal do produto. Contém o texto confirmado, tema, origem, nota e feedback da análise.

## Redação

Texto produzido ou enviado pelo estudante. No MVP é um objeto temporário de entrada; o conteúdo confirmado é armazenado dentro de `Avaliacao`.

## Competência

Uma das cinco dimensões oficiais da redação do ENEM: C1, C2, C3, C4 e C5.

## Nota por competência

Resultado de uma competência dentro de uma avaliação, com nível de 0 a 5 e pontuação de 0 a 200.

## FeedbackItem

Orientação específica associada a uma competência, podendo conter trecho, problema, explicação, melhoria e limitação.

## Crédito

Unidade de uso do produto. No MVP, 1 crédito permite iniciar 1 avaliação.

## Compra de crédito

Registro do pedido financeiro para adquirir créditos, com estado de pagamento, preço e snapshot da condição aplicada.

## Oferta de crédito

Pacote promocional administrável, com créditos, bônus, preço e período de vigência.

## Transação de crédito

Movimento auditável que adiciona, consome, estorna ou ajusta créditos.

## Processamento

Registro técnico efêmero de OCR ou avaliação assíncrona. Não faz parte do histórico funcional.

## Texto confirmado

Versão que o estudante revisou e autorizou para avaliação. É imutável dentro de uma avaliação concluída.
