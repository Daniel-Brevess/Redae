import { useEffect, useState, type FormEvent, type ReactNode } from 'react'
import {
  createTypedEvaluation,
  getEvaluation,
  listEvaluations,
  type Evaluation,
} from '../../api/evaluationApi'
import type { EvaluationResult, EvaluationStep, PrototypeScreen } from '../../prototype/types'
import { PrototypeShell } from './PrototypeShell'
import { EMAIL_VERIFICATION_ENABLED, type User } from '../../api/authApi'
import { useAuth, useOptionalAuth } from '../../auth/AuthContext'

type PrototypeExperienceProps = { onExit: () => void; user?: User | null }

const EVALUATION_POLL_INTERVAL_MS = 2000
const EVALUATION_PROCESSING_TIMEOUT_MS = 120000
type ProcessingStatus = 'PENDENTE' | 'PROCESSANDO'

export function PrototypeExperience({ onExit, user = null }: PrototypeExperienceProps) {
  const [screen, setScreen] = useState<PrototypeScreen>('home')
  const [step, setStep] = useState<EvaluationStep | null>(null)
  const [theme, setTheme] = useState('Os desafios da educação digital no Brasil')
  const [text, setText] = useState('')
  const [result, setResult] = useState<EvaluationResult | null>(null)
  const [submitError, setSubmitError] = useState<string | null>(null)
  const [submitting, setSubmitting] = useState(false)
  const [evaluationId, setEvaluationId] = useState<string | null>(null)
  const [processingStatus, setProcessingStatus] = useState<ProcessingStatus>('PROCESSANDO')
  const [history, setHistory] = useState<Evaluation[]>([])
  const [historyLoading, setHistoryLoading] = useState(false)
  const [openingEvaluationId, setOpeningEvaluationId] = useState<string | null>(null)
  const auth = useOptionalAuth()

  const startEvaluation = () => {
    setScreen('home')
    setStep('choice')
    setResult(null)
    setSubmitError(null)
  }

  const openEditor = () => {
    setText('')
    setSubmitError(null)
    setStep('editor')
  }

  const confirmText = () => {
    if (theme.trim() && text.trim().length >= 80) setStep('confirmation')
  }

  const submitEvaluation = async () => {
    if (submitting || step === 'processing') return

    setSubmitting(true)
    setSubmitError(null)
    try {
      const response = await createTypedEvaluation(
        theme.trim(),
        text.trim(),
        auth?.accessToken ?? undefined,
      )
      setEvaluationId(response.data.id)
      setProcessingStatus(response.data.status === 'PENDENTE' ? 'PENDENTE' : 'PROCESSANDO')
      setStep('processing')
    } catch (error) {
      setSubmitError(error instanceof Error ? error.message : 'Não foi possível enviar a redação.')
    } finally {
      setSubmitting(false)
    }
  }

  const retryEvaluation = async () => {
    setSubmitError(null)
    await submitEvaluation()
  }

  useEffect(() => {
    if (!evaluationId || !auth?.accessToken || step !== 'processing') return
    let active = true
    let timeoutId: number | null = null
    const deadline = Date.now() + EVALUATION_PROCESSING_TIMEOUT_MS
    const poll = async () => {
      try {
        const response = await getEvaluation(evaluationId, auth.accessToken ?? undefined)
        if (!active) return
        if (response.data.status === 'CONCLUIDA') {
          setResult(toEvaluationResult(response.data))
          setStep('result')
        } else if (response.data.status === 'FALHOU') {
          setSubmitError(
            response.data.failureReason ??
              'Não foi possível concluir a avaliação. Tente novamente.',
          )
          setStep('confirmation')
        } else {
          setProcessingStatus(response.data.status)
          if (Date.now() >= deadline) {
            setSubmitError(
              'A avaliação está demorando mais que o esperado. Tente novamente em instantes.',
            )
            setStep('confirmation')
            return
          }
          timeoutId = window.setTimeout(() => void poll(), EVALUATION_POLL_INTERVAL_MS)
        }
      } catch {
        if (active) {
          setSubmitError('Não foi possível consultar o andamento da avaliação.')
          setStep('confirmation')
        }
      }
    }
    void poll()
    return () => {
      active = false
      if (timeoutId !== null) window.clearTimeout(timeoutId)
    }
  }, [auth?.accessToken, evaluationId, step])

  useEffect(() => {
    if (screen !== 'history' || !auth?.accessToken) return
    let active = true
    const loadingTimer = window.setTimeout(() => {
      if (active) setHistoryLoading(true)
    }, 0)
    listEvaluations(auth.accessToken)
      .then((response) => {
        if (active) setHistory(response.data)
      })
      .catch(() => {
        if (active) setSubmitError('Não foi possível carregar o histórico.')
      })
      .finally(() => {
        if (active) setHistoryLoading(false)
      })
    return () => {
      active = false
      window.clearTimeout(loadingTimer)
    }
  }, [auth?.accessToken, screen])

  const goHome = () => {
    setStep(null)
    setScreen('home')
  }

  const openHistoryEvaluation = async (evaluation: Evaluation) => {
    if (!auth?.accessToken || openingEvaluationId) return
    setOpeningEvaluationId(evaluation.id)
    setSubmitError(null)
    try {
      const response = await getEvaluation(evaluation.id, auth.accessToken)
      setEvaluationId(response.data.id)
      setProcessingStatus(response.data.status === 'PENDENTE' ? 'PENDENTE' : 'PROCESSANDO')
      if (response.data.status === 'CONCLUIDA') {
        setResult(toEvaluationResult(response.data))
        setStep('result')
      } else {
        setStep('processing')
      }
      setScreen('home')
    } catch (error) {
      setSubmitError(error instanceof Error ? error.message : 'Não foi possível abrir a avaliação.')
    } finally {
      setOpeningEvaluationId(null)
    }
  }

  return (
    <PrototypeShell
      activeScreen={screen}
      onNavigate={(nextScreen) => {
        setStep(null)
        setScreen(nextScreen)
      }}
      onExit={onExit}
      user={user}
    >
      {EMAIL_VERIFICATION_ENABLED && user && user.emailVerified === false && auth?.accessToken && (
        <EmailVerificationBanner />
      )}
      {screen === 'home' && (
        <HomeScreen
          user={user}
          step={step}
          theme={theme}
          text={text}
          result={result}
          onStart={startEvaluation}
          onThemeChange={setTheme}
          onTextChange={setText}
          onEditor={openEditor}
          onConfirmText={confirmText}
          onSubmit={submitEvaluation}
          submitError={submitError}
          submitting={submitting}
          processingStatus={processingStatus}
          onRetry={retryEvaluation}
          onHome={goHome}
        />
      )}
      {screen === 'history' && (
        <HistoryScreen
          evaluations={history}
          loading={historyLoading}
          openingEvaluationId={openingEvaluationId}
          onStart={startEvaluation}
          onOpen={openHistoryEvaluation}
        />
      )}
      {screen === 'credits' && <CreditsScreen />}
      {screen === 'profile' && <ProfileScreen user={user} />}
    </PrototypeShell>
  )
}

function EmailVerificationBanner() {
  const { resendEmailVerification, verifyEmail } = useAuth()
  const [code, setCode] = useState('')
  const [message, setMessage] = useState<string | null>(null)
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  async function resend() {
    setLoading(true)
    setError(null)
    try {
      await resendEmailVerification()
      setMessage('Novo código enviado. Confira seu e-mail.')
    } catch (requestError) {
      setError(
        requestError instanceof Error ? requestError.message : 'Não foi possível enviar o código.',
      )
    } finally {
      setLoading(false)
    }
  }

  async function confirm(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()
    setLoading(true)
    setError(null)
    try {
      await verifyEmail(code)
      setMessage('E-mail confirmado com sucesso.')
    } catch (requestError) {
      setError(
        requestError instanceof Error ? requestError.message : 'Código inválido ou expirado.',
      )
    } finally {
      setLoading(false)
    }
  }

  return (
    <aside className="email-verification-banner" aria-label="Confirmação de e-mail">
      <div>
        <strong>Confirme seu e-mail</strong>
        <p>Enviamos um código para o seu e-mail. Confirme para manter sua conta protegida.</p>
      </div>
      <form onSubmit={confirm}>
        <input
          aria-label="Código de confirmação"
          inputMode="numeric"
          maxLength={6}
          pattern="[0-9]{6}"
          placeholder="000000"
          value={code}
          onChange={(event) => setCode(event.target.value.replace(/\D/g, ''))}
          required
        />
        <button className="primary-button" type="submit" disabled={loading || code.length !== 6}>
          Confirmar
        </button>
        <button className="text-button" type="button" onClick={resend} disabled={loading}>
          Reenviar código
        </button>
      </form>
      {message && <p className="email-verification-message">{message}</p>}
      {error && (
        <p className="field-hint field-hint-error" role="alert">
          {error}
        </p>
      )}
    </aside>
  )
}

type HomeScreenProps = {
  user: User | null
  step: EvaluationStep | null
  theme: string
  text: string
  result: EvaluationResult | null
  onStart: () => void
  onThemeChange: (theme: string) => void
  onTextChange: (text: string) => void
  onEditor: () => void
  onConfirmText: () => void
  onSubmit: () => Promise<void>
  submitError: string | null
  submitting: boolean
  processingStatus: ProcessingStatus
  onRetry: () => Promise<void>
  onHome: () => void
}

function HomeScreen({
  user,
  step,
  theme,
  text,
  result,
  onStart,
  onThemeChange,
  onTextChange,
  onEditor,
  onConfirmText,
  onSubmit,
  submitError,
  submitting,
  processingStatus,
  onRetry,
  onHome,
}: HomeScreenProps) {
  if (step === 'choice') return <ChoiceStep onEditor={onEditor} onBack={onHome} />
  if (step === 'editor') {
    return (
      <EditorStep
        theme={theme}
        text={text}
        onThemeChange={onThemeChange}
        onTextChange={onTextChange}
        onConfirm={onConfirmText}
        onBack={onHome}
      />
    )
  }
  if (step === 'confirmation') {
    return (
      <ConfirmationStep
        theme={theme}
        text={text}
        onSubmit={onSubmit}
        onBack={onEditor}
        error={submitError}
        submitting={submitting}
        onRetry={onRetry}
      />
    )
  }
  if (step === 'processing') {
    return <ProcessingStep status={processingStatus} onBack={onHome} />
  }
  if (step === 'result' && result) return <ResultStep result={result} onHome={onHome} />

  return <DashboardScreen user={user} result={result} onStart={onStart} />
}

function DashboardScreen({
  user,
  result,
  onStart,
}: {
  user: User | null
  result: EvaluationResult | null
  onStart: () => void
}) {
  return (
    <div className="prototype-content">
      <section className="prototype-welcome">
        <div>
          <p className="prototype-eyebrow">terça-feira, 25 de agosto</p>
          <h1>Olá, {user?.name ?? 'estudante'}.</h1>
          <p className="prototype-lede">Vamos transformar uma ideia em um próximo passo?</p>
        </div>
        <div className="credit-pill" aria-label="Saldo de créditos em breve">
          <span aria-hidden="true">✦</span>
          <strong>Créditos em breve</strong>
        </div>
      </section>
      <section className="prototype-grid prototype-grid-main" aria-label="Resumo da sua jornada">
        <article className="new-evaluation-card">
          <div>
            <span className="prototype-icon prototype-icon-green" aria-hidden="true">
              ＋
            </span>
            <p className="prototype-eyebrow">Seu próximo passo</p>
            <h2>Faça uma nova avaliação.</h2>
            <p>Escreva uma redação e receba um diagnóstico claro para continuar evoluindo.</p>
          </div>
          <button className="primary-button" type="button" onClick={onStart}>
            Começar agora <span aria-hidden="true">→</span>
          </button>
        </article>
        <aside className="progress-card">
          <p className="prototype-eyebrow">Sua evolução</p>
          <div className="progress-card-score">
            <strong>{result ? result.finalScore : '—'}</strong>
            <span>{result ? 'última nota' : 'ainda sem avaliações'}</span>
          </div>
          <div className="progress-bar" aria-label="Progresso das avaliações">
            <span style={{ width: result ? '72%' : '0%' }} />
          </div>
          <p>
            {result
              ? 'Você já deu o primeiro passo. Continue praticando.'
              : 'Sua primeira avaliação começa aqui.'}
          </p>
        </aside>
      </section>
      <section className="prototype-section-heading">
        <div>
          <p className="prototype-eyebrow">Últimas atividades</p>
          <h2>Seu histórico</h2>
        </div>
        {result && <span className="status-badge">Avaliação concluída</span>}
      </section>
      {result ? (
        <article className="history-card">
          <div className="history-score">
            <strong>{result.finalScore}</strong>
            <span>/ 1000</span>
          </div>
          <div>
            <strong>{result.theme}</strong>
            <p>
              Agora há pouco ·{' '}
              {result.type === 'DIAGNOSTICO' ? 'Diagnóstico' : 'Avaliação completa'} · Texto
              digitado
            </p>
          </div>
          <button className="text-button" type="button" onClick={onStart}>
            Ver detalhes <span aria-hidden="true">→</span>
          </button>
        </article>
      ) : (
        <div className="prototype-empty">
          <span aria-hidden="true">✎</span>
          <p>Seu histórico aparecerá aqui depois da primeira avaliação.</p>
        </div>
      )}
    </div>
  )
}

function ChoiceStep({ onEditor, onBack }: { onEditor: () => void; onBack: () => void }) {
  return (
    <FlowFrame
      eyebrow="Nova avaliação"
      title="Como você quer começar?"
      description="Escolha o formato que combina com o seu momento."
      onBack={onBack}
    >
      <div className="choice-grid">
        <button className="choice-card choice-card-selected" type="button" onClick={onEditor}>
          <span className="prototype-icon prototype-icon-green" aria-hidden="true">
            Aa
          </span>
          <strong>Escrever agora</strong>
          <span>Digite sua redação diretamente no Redaê.</span>
          <small>Mais rápido</small>
        </button>
        <button className="choice-card choice-card-disabled" type="button" disabled>
          <span className="prototype-icon prototype-icon-blue" aria-hidden="true">
            ▧
          </span>
          <strong>Enviar uma imagem</strong>
          <span>Fotografe ou escolha uma redação da galeria.</span>
          <small>Em breve no protótipo</small>
        </button>
      </div>
      <BackButton onClick={onBack} />
    </FlowFrame>
  )
}

type EditorStepProps = {
  theme: string
  text: string
  onThemeChange: (value: string) => void
  onTextChange: (value: string) => void
  onConfirm: () => void
  onBack: () => void
}

function EditorStep({
  theme,
  text,
  onThemeChange,
  onTextChange,
  onConfirm,
  onBack,
}: EditorStepProps) {
  const isValid = theme.trim().length > 0 && text.trim().length >= 80
  return (
    <FlowFrame
      eyebrow="Nova avaliação · 1 de 2"
      title="Coloque sua ideia no papel."
      description="Não precisa ser perfeito. Este é um espaço para praticar."
      onBack={onBack}
    >
      <form
        className="prototype-form"
        onSubmit={(event) => {
          event.preventDefault()
          if (isValid) onConfirm()
        }}
      >
        <label htmlFor="evaluation-theme">Tema da redação</label>
        <input
          id="evaluation-theme"
          value={theme}
          onChange={(event) => onThemeChange(event.target.value)}
          placeholder="Ex.: Desafios da educação no Brasil"
        />
        <div className="field-heading">
          <label htmlFor="evaluation-text">Sua redação</label>
          <span>{text.length} caracteres</span>
        </div>
        <textarea
          id="evaluation-text"
          value={text}
          onChange={(event) => onTextChange(event.target.value)}
          placeholder="Comece a escrever aqui..."
          rows={12}
        />
        {text.length > 0 && text.trim().length < 80 && (
          <p className="field-hint field-hint-error" role="alert">
            Escreva pelo menos 80 caracteres para continuar.
          </p>
        )}
        <div className="form-actions">
          <BackButton onClick={onBack} />
          <button className="primary-button" type="submit" disabled={!isValid}>
            Revisar redação <span aria-hidden="true">→</span>
          </button>
        </div>
      </form>
    </FlowFrame>
  )
}

function ConfirmationStep({
  theme,
  text,
  onSubmit,
  onBack,
  error,
  submitting,
  onRetry,
}: {
  theme: string
  text: string
  onSubmit: () => Promise<void>
  onBack: () => void
  error: string | null
  submitting: boolean
  onRetry: () => Promise<void>
}) {
  return (
    <FlowFrame
      eyebrow="Nova avaliação · 2 de 2"
      title="Tudo pronto para revisar?"
      description="Confira o tema e o início do texto antes de enviar para avaliação."
      onBack={onBack}
    >
      <div className="confirmation-card">
        <span>Tema</span>
        <strong>{theme}</strong>
        <span>Texto</span>
        <p>{text}</p>
      </div>
      <div className="form-actions">
        <BackButton onClick={onBack} />
        <button
          className="primary-button"
          type="button"
          aria-label={error ? 'Tentar novamente' : 'Confirmar e avaliar'}
          onClick={error ? onRetry : onSubmit}
          disabled={submitting}
        >
          Confirmar e avaliar <span aria-hidden="true">→</span>
        </button>
      </div>
      {error && (
        <p className="field-hint field-hint-error" role="alert">
          {error}
        </p>
      )}
    </FlowFrame>
  )
}

function ProcessingStep({ status, onBack }: { status: ProcessingStatus; onBack: () => void }) {
  const processingMessage =
    status === 'PENDENTE' ? '\u0041guardando in\u00edcio da an\u00e1lise' : 'Analisando seu texto'
  return (
    <FlowFrame
      eyebrow="Avaliação em andamento"
      title="Estamos lendo sua redação."
      description="Leva só um instante. Em breve você verá os pontos fortes e os próximos caminhos para melhorar."
      onBack={onBack}
    >
      <div className="processing-card" role="status" aria-live="polite">
        <span className="processing-spinner" aria-hidden="true" />
        <strong>{processingMessage}</strong>
        <p>Organizando seu diagnóstico por competência.</p>
      </div>
    </FlowFrame>
  )
}

function ResultStep({ result, onHome }: { result: EvaluationResult; onHome: () => void }) {
  const [expanded, setExpanded] = useState<string | null>(null)
  return (
    <div className="prototype-content result-content">
      <button className="back-link" type="button" onClick={onHome}>
        ← Voltar para o início
      </button>
      <section className="result-hero">
        <div>
          <p className="prototype-eyebrow">
            {result.type === 'DIAGNOSTICO'
              ? 'Diagnóstico concluído'
              : 'Avaliação completa concluída'}
          </p>
          <h1>Você já sabe por onde continuar.</h1>
          <p className="prototype-lede">
            {result.type === 'DIAGNOSTICO'
              ? 'Seu diagnóstico chegou. Veja os principais pontos e continue evoluindo.'
              : 'Sua avaliação completa chegou. Veja os detalhes de cada competência.'}
          </p>
        </div>
        <div className="result-score">
          <strong>{result.finalScore}</strong>
          <span>de 1000 pontos</span>
        </div>
      </section>
      <section className="result-summary">
        <div>
          <p className="prototype-eyebrow">Tema avaliado</p>
          <h2>{result.theme}</h2>
        </div>
        <span className="status-badge status-badge-green">
          {result.type === 'DIAGNOSTICO'
            ? 'Diagnóstico · Texto digitado'
            : 'Completa · Texto digitado'}
        </span>
      </section>
      <section className="essay-result">
        <p className="prototype-eyebrow">Sua redação</p>
        <p className="essay-result-text">{result.text}</p>
      </section>
      <section className="competencies-section">
        <div className="prototype-section-heading">
          <div>
            <p className="prototype-eyebrow">
              {result.type === 'DIAGNOSTICO' ? 'Seu diagnóstico' : 'Sua avaliação completa'}
            </p>
            <h2>Olhe para cada competência.</h2>
          </div>
          <span className="competency-total">5 competências</span>
        </div>
        <div className="competency-list">
          {result.competencies.map((item) => (
            <article
              className={`competency-card${expanded === item.code ? ' is-expanded' : ''}`}
              key={item.code}
            >
              <button
                type="button"
                onClick={() => setExpanded(expanded === item.code ? null : item.code)}
                aria-expanded={expanded === item.code}
              >
                <span className="competency-code">{item.code}</span>
                <span className="competency-copy">
                  <strong>{item.title}</strong>
                  <small>{item.summary}</small>
                </span>
                <span className="competency-score">
                  {item.score}
                  <small>/ 200</small>
                </span>
                <span className="accordion-icon" aria-hidden="true">
                  {expanded === item.code ? '−' : '+'}
                </span>
              </button>
              {expanded === item.code && (
                <div className="competency-detail">
                  <p>{item.detail || 'Nenhum apontamento relevante foi identificado.'}</p>
                  {result.type === 'DIAGNOSTICO' && (
                    <p className="field-hint">
                      Para receber todos os detalhes, faça uma avaliação completa.
                    </p>
                  )}
                  <a href="#next-step" onClick={(event) => event.preventDefault()}>
                    Ver como praticar essa competência <span aria-hidden="true">→</span>
                  </a>
                </div>
              )}
            </article>
          ))}
        </div>
      </section>
    </div>
  )
}

// Kept temporarily while the prototype history layout is migrated to the API-backed version.
// eslint-disable-next-line @typescript-eslint/no-unused-vars
function LegacyHistoryScreen({
  result,
  onStart,
}: {
  result: EvaluationResult | null
  onStart: () => void
}) {
  return (
    <div className="prototype-content">
      <section className="simple-screen-heading">
        <p className="prototype-eyebrow">Seu caminho</p>
        <h1>Histórico de avaliações.</h1>
        <p className="prototype-lede">
          Acompanhe o que mudou a cada texto e encontre seu próximo foco de treino.
        </p>
      </section>
      {result ? (
        <article className="history-card">
          <div className="history-score">
            <strong>{result.finalScore}</strong>
            <span>/ 1000</span>
          </div>
          <div>
            <strong>{result.theme}</strong>
            <p>Hoje · Texto digitado</p>
          </div>
          <button className="text-button" type="button" onClick={onStart}>
            Abrir avaliação <span aria-hidden="true">→</span>
          </button>
        </article>
      ) : (
        <div className="prototype-empty prototype-empty-large">
          <span aria-hidden="true">◌</span>
          <h2>Seu histórico começa com uma prática.</h2>
          <p>Faça sua primeira avaliação para acompanhar sua evolução por aqui.</p>
          <button className="primary-button" type="button" onClick={onStart}>
            Fazer minha primeira avaliação <span aria-hidden="true">→</span>
          </button>
        </div>
      )}
    </div>
  )
}

function HistoryScreen({
  evaluations,
  loading,
  openingEvaluationId,
  onStart,
  onOpen,
}: {
  evaluations: Evaluation[]
  loading: boolean
  openingEvaluationId: string | null
  onStart: () => void
  onOpen: (evaluation: Evaluation) => void | Promise<void>
}) {
  if (loading) {
    return (
      <div className="prototype-content">
        <div className="prototype-empty prototype-empty-large" role="status">
          <p>Carregando historico...</p>
        </div>
      </div>
    )
  }
  return (
    <div className="prototype-content">
      <section className="simple-screen-heading">
        <p className="prototype-eyebrow">Seu caminho</p>
        <h1>Historico de avaliacoes.</h1>
        <p className="prototype-lede">Acompanhe suas praticas e seus resultados.</p>
      </section>
      {evaluations.length > 0 ? (
        <div className="history-list">
          {evaluations.map((evaluation) => (
            <article className="history-card" key={evaluation.id}>
              <div className="history-score">
                <strong>{evaluation.finalScore ?? '-'}</strong>
                <span>{evaluation.finalScore === null ? 'em andamento' : '/ 1000'}</span>
              </div>
              <div>
                <strong>{evaluation.theme}</strong>
                <p>
                  {new Date(evaluation.createdAt).toLocaleDateString('pt-BR')} ·{' '}
                  {evaluation.type === 'DIAGNOSTICO' ? 'diagnóstico' : 'avaliação completa'} ·{' '}
                  {evaluation.status.toLowerCase()}
                </p>
              </div>
              <button
                className="text-button"
                type="button"
                onClick={() => void onOpen(evaluation)}
                disabled={openingEvaluationId !== null}
                aria-busy={openingEvaluationId === evaluation.id}
              >
                {openingEvaluationId === evaluation.id ? 'Abrindo avaliacao...' : 'Abrir avaliacao'}{' '}
                <span aria-hidden="true">-&gt;</span>
              </button>
            </article>
          ))}
        </div>
      ) : (
        <div className="prototype-empty prototype-empty-large">
          <p>Seu historico comeca com uma pratica.</p>
          <button className="primary-button" type="button" onClick={onStart}>
            Fazer primeira avaliacao
          </button>
        </div>
      )}
    </div>
  )
}

function toEvaluationResult(evaluation: Evaluation): EvaluationResult {
  const titles: Record<string, string> = {
    C1: 'Dominio da norma-padrao',
    C2: 'Compreensao do tema',
    C3: 'Selecao de argumentos',
    C4: 'Coesao e coerencia',
    C5: 'Proposta de intervencao',
  }
  return {
    id: evaluation.id,
    theme: evaluation.theme,
    text: evaluation.text ?? '',
    type: evaluation.type,
    finalScore: evaluation.finalScore ?? 0,
    competencies: evaluation.competencies.map((competency) => ({
      code: competency.code,
      title: titles[competency.code] ?? competency.code,
      score: competency.points,
      summary: competency.summary,
      detail:
        competency.feedbackItems.length > 0
          ? competency.feedbackItems
              .map((feedback) => `${feedback.problem} ${feedback.howToImprove}`)
              .join(' ')
          : competency.highlights.join(' '),
    })),
  }
}

function CreditsScreen() {
  return (
    <InfoScreen
      eyebrow="Seu saldo"
      title="Créditos para continuar praticando."
      description="No produto completo, esta área mostrará seu saldo, ofertas e histórico de compras. A compra de créditos fica para uma próxima etapa."
    />
  )
}
function ProfileScreen({ user }: { user: User | null }) {
  return (
    <div className="prototype-content">
      <section className="simple-screen-heading">
        <p className="prototype-eyebrow">Sua conta</p>
        <h1>Seu perfil.</h1>
        <p className="prototype-lede">Estes são os dados da sua conta autenticada.</p>
      </section>
      <div className="confirmation-card">
        <span>Nome</span>
        <strong>{user?.name ?? 'Não disponível'}</strong>
        <span>Email</span>
        <p>{user?.email ?? 'Não disponível'}</p>
      </div>
    </div>
  )
}

function InfoScreen({
  eyebrow,
  title,
  description,
}: {
  eyebrow: string
  title: string
  description: string
}) {
  return (
    <div className="prototype-content">
      <section className="simple-screen-heading">
        <p className="prototype-eyebrow">{eyebrow}</p>
        <h1>{title}</h1>
        <p className="prototype-lede">{description}</p>
      </section>
      <div className="prototype-empty prototype-empty-large">
        <span aria-hidden="true">✦</span>
        <p>Esta parte não faz parte do fluxo principal do protótipo.</p>
      </div>
    </div>
  )
}

function FlowFrame({
  eyebrow,
  title,
  description,
  onBack,
  children,
}: {
  eyebrow: string
  title: string
  description: string
  onBack: () => void
  children: ReactNode
}) {
  return (
    <div className="prototype-content flow-content">
      <button className="back-link" type="button" onClick={onBack}>
        ← Área inicial
      </button>
      <section className="flow-heading">
        <p className="prototype-eyebrow">{eyebrow}</p>
        <h1>{title}</h1>
        <p className="prototype-lede">{description}</p>
      </section>
      {children}
    </div>
  )
}
function BackButton({ onClick }: { onClick: () => void }) {
  return (
    <button className="back-button" type="button" onClick={onClick}>
      Voltar
    </button>
  )
}
