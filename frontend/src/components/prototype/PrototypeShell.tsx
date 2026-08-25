import type { ReactNode } from 'react'
import logoUrl from '../../../assets/redae_logo.png'
import type { PrototypeScreen } from '../../prototype/types'

type PrototypeShellProps = {
  activeScreen: PrototypeScreen
  onNavigate: (screen: PrototypeScreen) => void
  onExit: () => void
  children: ReactNode
}

export function PrototypeShell({
  activeScreen,
  onNavigate,
  onExit,
  children,
}: PrototypeShellProps) {
  const navigation: { id: PrototypeScreen; label: string }[] = [
    { id: 'home', label: 'Início' },
    { id: 'history', label: 'Histórico' },
    { id: 'credits', label: 'Créditos' },
  ]

  return (
    <div className="prototype-app">
      <header className="prototype-header">
        <a className="prototype-brand" href="#prototype-home" aria-label="Redaê — início">
          <img src={logoUrl} alt="Redaê" />
        </a>
        <nav className="prototype-nav" aria-label="Área de treino">
          {navigation.map((item) => (
            <button
              className={`prototype-nav-link${activeScreen === item.id ? ' is-active' : ''}`}
              type="button"
              key={item.id}
              onClick={() => onNavigate(item.id)}
              aria-current={activeScreen === item.id ? 'page' : undefined}
            >
              {item.label}
            </button>
          ))}
        </nav>
        <div className="prototype-user-actions">
          <button
            className={`prototype-profile${activeScreen === 'profile' ? ' is-active' : ''}`}
            type="button"
            onClick={() => onNavigate('profile')}
            aria-label="Abrir perfil de Marina"
          >
            <span aria-hidden="true">MC</span>
            <strong>Marina</strong>
          </button>
          <button className="prototype-exit" type="button" onClick={onExit}>
            Sair
          </button>
        </div>
      </header>
      <main className="prototype-main" id="prototype-home">
        {children}
      </main>
    </div>
  )
}
