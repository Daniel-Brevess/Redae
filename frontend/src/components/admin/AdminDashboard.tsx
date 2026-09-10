import type { User } from '../../api/authApi'

type AdminDashboardProps = { user: User; onBack: () => void }

export function AdminDashboard({ user, onBack }: AdminDashboardProps) {
  if (user.role !== 'ADMIN') {
    return (
      <main className="admin-page">
        <section className="admin-state admin-state-denied" aria-labelledby="admin-denied-title">
          <p className="prototype-eyebrow">Área administrativa</p>
          <h1 id="admin-denied-title">Acesso negado.</h1>
          <p>Esta área está disponível somente para administradores.</p>
          <button className="primary-button" type="button" onClick={onBack}>
            Voltar para o treino
          </button>
        </section>
      </main>
    )
  }

  return (
    <main className="admin-page" aria-labelledby="admin-title">
      <header className="admin-header">
        <div>
          <p className="prototype-eyebrow">Área administrativa</p>
          <h1 id="admin-title">Olá, {user.name}.</h1>
          <p>Os indicadores e ferramentas administrativas aparecerão aqui.</p>
        </div>
        <button className="text-button" type="button" onClick={onBack}>
          Voltar para o treino <span aria-hidden="true">→</span>
        </button>
      </header>
      <section className="admin-grid" aria-label="Indicadores administrativos">
        <article className="admin-card">
          <span>Usuários</span>
          <strong>—</strong>
          <p>Indicador disponível na próxima etapa.</p>
        </article>
        <article className="admin-card">
          <span>Créditos</span>
          <strong>—</strong>
          <p>Indicador disponível na próxima etapa.</p>
        </article>
      </section>
    </main>
  )
}
