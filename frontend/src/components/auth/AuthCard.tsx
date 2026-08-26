import { useEffect, useRef, useState, type FormEvent } from 'react'
import { useAuth } from '../../auth/AuthContext'
import { FormField } from '../forms/FormField'
import { FeedbackMessage } from '../ui/FeedbackMessage'

export type AuthCardType = 'login' | 'signup'
type AuthCardProps = {
  type: AuthCardType
  onClose: () => void
  onEnterPrototype: () => void
  onAuthenticated: (type: AuthCardType) => void
}

export function AuthCard({ type, onClose, onEnterPrototype, onAuthenticated }: AuthCardProps) {
  const [submitted, setSubmitted] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)
  const cardRef = useRef<HTMLElement>(null)
  const isSignup = type === 'signup'
  const { signIn, signUp } = useAuth()

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

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()
    setError(null)
    setSubmitted(false)
    setLoading(true)
    const data = new FormData(event.currentTarget)
    try {
      const email = String(data.get('email'))
      const password = String(data.get('password'))
      if (isSignup) {
        await signUp({
          name: String(data.get('name')),
          email,
          password,
          passwordConfirmation: String(data.get('passwordConfirmation')),
        })
      } else {
        await signIn(email, password)
      }
      setSubmitted(true)
      onAuthenticated(type)
      onClose()
    } catch (requestError) {
      setError(
        requestError instanceof Error
          ? requestError.message
          : 'Não foi possível concluir o acesso.',
      )
    } finally {
      setLoading(false)
    }
  }

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
        {error && <FeedbackMessage tone="error">{error}</FeedbackMessage>}
        {submitted && (
          <FeedbackMessage tone="success">Acesso realizado com sucesso.</FeedbackMessage>
        )}
        {submitted && (
          <button className="auth-prototype-link" type="button" onClick={onEnterPrototype}>
            Abrir área de treino <span aria-hidden="true">→</span>
          </button>
        )}
        <form className="auth-form" onSubmit={handleSubmit}>
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
          <button className="auth-submit" type="submit" disabled={loading}>
            {loading ? 'Aguarde...' : isSignup ? 'Cadastrar' : 'Entrar'}
          </button>
        </form>
      </section>
    </div>
  )
}
