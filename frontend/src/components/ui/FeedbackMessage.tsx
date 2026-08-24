import type { ReactNode } from 'react'

type FeedbackMessageProps = { tone: 'error' | 'success' | 'info'; children: ReactNode }

export function FeedbackMessage({ tone, children }: FeedbackMessageProps) {
  return <p className={`feedback-message feedback-${tone}`} role={tone === 'error' ? 'alert' : 'status'}>{children}</p>
}
