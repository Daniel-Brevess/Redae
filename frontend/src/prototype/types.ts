export type PrototypeScreen = 'home' | 'history' | 'credits' | 'profile'
export type EvaluationStep = 'choice' | 'editor' | 'confirmation' | 'processing' | 'result'

export type CompetencyFeedback = {
  code: `C${1 | 2 | 3 | 4 | 5}`
  title: string
  score: number
  summary: string
  detail: string
}

export type EvaluationResult = {
  id: string
  theme: string
  text: string
  finalScore: number
  competencies: CompetencyFeedback[]
}
