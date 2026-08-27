import { request, type ApiEnvelope } from './http'

export type CreatedEvaluation = {
  id: string
  theme: string
  origin: 'DIGITADA'
  status: 'PENDENTE' | 'PROCESSANDO' | 'CONCLUIDA' | 'FALHOU'
  createdAt: string
}

export type EvaluationFeedback = {
  excerpt: string | null
  problem: string
  explanation: string
  howToImprove: string
  example: string
  limitation: string | null
}

export type EvaluationCompetency = {
  code: `C${1 | 2 | 3 | 4 | 5}`
  level: number
  points: number
  summary: string
  feedbackItems: EvaluationFeedback[]
}

export type Evaluation = CreatedEvaluation & {
  finalScore: number | null
  failureReason: string | null
  competencies: EvaluationCompetency[]
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

export function getEvaluation(evaluationId: string, accessToken?: string) {
  return request<ApiEnvelope<Evaluation>>(`/evaluations/${evaluationId}`, {}, accessToken)
}

export function listEvaluations(accessToken?: string) {
  return request<ApiEnvelope<Evaluation[]>>('/evaluations', {}, accessToken)
}
