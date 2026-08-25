import logoUrl from '../../../assets/redae_logo.png'
import type { AuthCardType } from '../auth/AuthCard'

type SiteHeaderProps = {
  activeCard: AuthCardType | null
  onCardChange: (card: AuthCardType | null) => void
  onNavigate: () => void
}

export function SiteHeader({ activeCard, onCardChange, onNavigate }: SiteHeaderProps) {
  const toggleCard = (card: AuthCardType) => onCardChange(activeCard === card ? null : card)

  return (
    <header className="site-header">
      <a className="logo" href="#top" aria-label="Redaê — início">
        <img src={logoUrl} alt="Redaê" />
      </a>
      <nav className="site-nav" aria-label="Navegação principal">
        <a className="nav-link" href="#como-funciona" onClick={onNavigate}>
          Como funciona
        </a>
        <a className="nav-link" href="#sobre-nos" onClick={onNavigate}>
          Sobre nós
        </a>
        <button
          className="nav-link nav-button auth-trigger"
          type="button"
          onClick={() => toggleCard('login')}
        >
          Login
        </button>
        <button
          className="nav-link nav-link-cta nav-button auth-trigger"
          type="button"
          onClick={() => toggleCard('signup')}
        >
          Cadastre-se
        </button>
      </nav>
    </header>
  )
}
