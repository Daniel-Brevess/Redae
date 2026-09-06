export type PrototypeScreen = 'home' | 'history' | 'credits' | 'transactions' | 'profile'
export type EvaluationStep = 'choice' | 'editor' | 'confirmation' | 'processing' | 'result'

export type CompetencyFeedback = {
  code: `C${1 | 2 | 3 | 4 | 5}`
  title: string
  score: number
  summary: string
  detail: string
  feedbackItems: CompetencyFeedbackItem[]
}

export type CompetencyFeedbackItem = {
  excerpt: string | null
  problem: string
  explanation: string
  howToImprove: string
  example: string
  limitation: string | null
}

export type EvaluationResult = {
  id: string
  theme: string
  text: string
  type: 'DIAGNOSTICO' | 'COMPLETA'
  finalScore: number
  competencies: CompetencyFeedback[]
}
