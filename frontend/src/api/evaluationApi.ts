import { request, type ApiEnvelope } from './http'

export type CreatedEvaluation = {
  id: string
  theme: string
  origin: 'DIGITADA'
  status: 'PENDENTE' | 'PROCESSANDO' | 'CONCLUIDA' | 'FALHOU'
  createdAt: string
}

export function createTypedEvaluation(theme: string, text: string, accessToken?: string) {
  return request<ApiEnvelope<CreatedEvaluation>>(
    '/evaluations',
    {
      method: 'POST',
      body: JSON.stringify({ origin: 'DIGITADA', theme, text }),
    },
    accessToken,
  )
}
