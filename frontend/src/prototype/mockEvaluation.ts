import type { EvaluationResult } from './types'

const feedback: EvaluationResult['competencies'] = [
  {
    code: 'C1',
    title: 'Domínio da norma-padrão',
    score: 160,
    summary: 'Seu texto mantém boa clareza e estrutura sintática.',
    detail: 'Revise períodos longos e observe a pontuação nas frases com mais de uma ideia.',
  },
  {
    code: 'C2',
    title: 'Compreensão do tema',
    score: 180,
    summary: 'Você desenvolveu o tema sem fugir da proposta.',
    detail: 'Continue conectando cada argumento ao recorte central do tema para ganhar precisão.',
  },
  {
    code: 'C3',
    title: 'Seleção de argumentos',
    score: 160,
    summary: 'Os argumentos são relevantes e sustentam seu ponto de vista.',
    detail: 'Inclua dados, repertórios ou exemplos mais específicos para fortalecer a autoria.',
  },
  {
    code: 'C4',
    title: 'Coesão e coerência',
    score: 180,
    summary: 'As ideias avançam com uma sequência fácil de acompanhar.',
    detail: 'Varie os conectivos entre parágrafos para deixar as relações ainda mais explícitas.',
  },
  {
    code: 'C5',
    title: 'Proposta de intervenção',
    score: 160,
    summary: 'Sua proposta apresenta ação e objetivo bem definidos.',
    detail: 'Detalhe melhor o modo de execução para tornar a intervenção mais concreta.',
  },
]

export function createMockEvaluation(theme: string, text: string): EvaluationResult {
  return {
    id: 'evaluation-prototype-001',
    theme,
    text,
    finalScore: feedback.reduce((total, item) => total + item.score, 0),
    competencies: feedback,
  }
}
