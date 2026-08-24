# Fluxo de dados entre componentes

| Origem | Dados enviados | Destino | Regra |
| --- | --- | --- | --- |
| Frontend | credenciais, texto, imagem, confirmações e consultas de status | Backend | comunicação por API HTTPS |
| Backend | estados, erros seguros, nota e feedback | Frontend | nunca inclui secrets ou detalhes internos |
| Backend | usuários, redações confirmadas, avaliações, histórico e suporte | PostgreSQL | persistência oficial do produto |
| Backend | imagem temporária | Armazenamento temporário | exclusão após confirmação ou expiração |
| Backend | imagem | Gemini | somente durante transcrição; sem dados pessoais desnecessários |
| Backend | texto confirmado e tema | Gemini | somente durante avaliação |
| Gemini | transcrição | Backend | estudante revisa antes da persistência definitiva |
| Gemini | nota e feedback estruturado | Backend | backend valida o contrato antes de salvar |

## Regras de privacidade

- prompts, respostas brutas e imagens não são persistidos como interação da IA;
- logs contêm somente metadados técnicos mínimos;
- dados pessoais não são enviados ao Gemini;
- a avaliação usa somente o texto confirmado pelo estudante;
- o frontend nunca acessa diretamente PostgreSQL, armazenamento ou Gemini.
