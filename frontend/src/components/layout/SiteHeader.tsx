import { useState } from 'react'
import logoUrl from '../../../assets/redae-logo-transparent.png'
import type { AuthCardType } from '../auth/AuthCard'

type SiteHeaderProps = {
  activeCard: AuthCardType | null
  onCardChange: (card: AuthCardType | null) => void
  onNavigate: () => void
}

const pageLinks = [
  { href: '#como-funciona', label: 'Como funciona' },
  { href: '#sobre-nos', label: 'Competências' },
  { href: '#duvidas', label: 'Dúvidas' },
]

export function SiteHeader({ activeCard, onCardChange, onNavigate }: SiteHeaderProps) {
  const [menuOpen, setMenuOpen] = useState(false)
  const toggleCard = (card: AuthCardType) => {
    setMenuOpen(false)
    onCardChange(activeCard === card ? null : card)
  }
  const closeMenu = () => {
    setMenuOpen(false)
    onNavigate()
  }

  return (
    <header
      className="site-header"
      onKeyDown={(event) => {
        if (event.key === 'Escape') setMenuOpen(false)
      }}
    >
      <a className="logo" href="#top" aria-label="Redaê — início">
        <img src={logoUrl} alt="Redaê" />
      </a>
      <nav className="desktop-navigation" aria-label="Seções da página">
        {pageLinks.map(({ href, label }) => (
          <a key={href} className="nav-link" href={href} onClick={closeMenu}>
            {label}
          </a>
        ))}
      </nav>
      <nav className="site-nav" aria-label="Navegação principal">
        <button
          className="nav-link nav-button login-link auth-trigger"
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
        <button
          className="mobile-menu-toggle"
          type="button"
          aria-label={menuOpen ? 'Fechar menu' : 'Abrir menu'}
          aria-expanded={menuOpen}
          aria-controls="mobile-navigation"
          onClick={() => {
            onNavigate()
            setMenuOpen(!menuOpen)
          }}
        >
          <span />
          <span />
          <span />
        </button>
      </nav>
      <nav
        id="mobile-navigation"
        className="mobile-navigation"
        aria-label="Links da página"
        hidden={!menuOpen}
      >
        {pageLinks.map(({ href, label }) => (
          <a key={href} className="nav-link" href={href} onClick={closeMenu}>
            {label}
          </a>
        ))}
        <button
          className="nav-link nav-button login-link auth-trigger"
          type="button"
          onClick={() => toggleCard('login')}
        >
          Login
        </button>
      </nav>
    </header>
  )
}
