import { useEffect, useRef, useState } from 'react'
import { FormField } from '../forms/FormField'
import { FeedbackMessage } from '../ui/FeedbackMessage'

export type AuthCardType = 'login' | 'signup'
type AuthCardProps = { type: AuthCardType; onClose: () => void }

export function AuthCard({ type, onClose }: AuthCardProps) {
  const [submitted, setSubmitted] = useState(false)
  const cardRef = useRef<HTMLElement>(null)
  const isSignup = type === 'signup'

  useEffect(() => {
    const handleEscape = (event: KeyboardEvent) => {
      if (event.key === 'Escape') onClose()
    }
    const handleOutsideClick = (event: MouseEvent) => {
      if (
        cardRef.current &&
        !cardRef.current.contains(event.target as Node) &&
        !(event.target as Element).closest('.auth-trigger')
      )
        onClose()
    }
    document.addEventListener('keydown', handleEscape)
    document.addEventListener('mousedown', handleOutsideClick)
    return () => {
      document.removeEventListener('keydown', handleEscape)
      document.removeEventListener('mousedown', handleOutsideClick)
    }
  }, [onClose])

  return (
    <div className="auth-card-wrap">
      <section
        className="auth-card"
        ref={cardRef}
        role="dialog"
        aria-modal="false"
        aria-labelledby={`${type}-title`}
      >
        <button className="auth-close" type="button" aria-label="Fechar" onClick={onClose}>
          ×
        </button>
        <p className="eyebrow">{isSignup ? 'Comece sua evolução' : 'Bem-vindo de volta'}</p>
        <h2 id={`${type}-title`}>{isSignup ? 'Crie sua conta.' : 'Entre na sua conta.'}</h2>
        {submitted && (
          <FeedbackMessage tone="success">Formulário pronto para integração.</FeedbackMessage>
        )}
        <form
          className="auth-form"
          onSubmit={(event) => {
            event.preventDefault()
            setSubmitted(true)
          }}
        >
          {isSignup && (
            <FormField
              id="signup-name"
              label="Nome"
              name="name"
              type="text"
              autoComplete="name"
              required
            />
          )}
          <FormField
            id={`${type}-email`}
            label="Email"
            name="email"
            type="email"
            autoComplete="email"
            required
          />
          <FormField
            id={`${type}-password`}
            label="Senha"
            name="password"
            type="password"
            autoComplete={isSignup ? 'new-password' : 'current-password'}
            minLength={isSignup ? 8 : undefined}
            required
          />
          {isSignup && (
            <FormField
              id="signup-password-confirmation"
              label="Confirmar senha"
              name="passwordConfirmation"
              type="password"
              autoComplete="new-password"
              minLength={8}
              required
            />
          )}
          <button className="auth-submit" type="submit">
            {isSignup ? 'Cadastrar' : 'Entrar'}
          </button>
        </form>
      </section>
    </div>
  )
}
