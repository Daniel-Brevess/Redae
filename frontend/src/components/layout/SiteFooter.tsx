import logoUrl from '../../../assets/redae_logo.png'
import redazinhoUrl from '../../../assets/redazinho.png'

export function SiteFooter() {
  return (
    <footer className="footer page-width">
      <a className="logo brand-lockup" href="#top" aria-label="Redaê — início">
        <img src={logoUrl} alt="Redaê" />
        <img className="brand-mascot" src={redazinhoUrl} alt="" aria-hidden="true" />
      </a>
      <p>Uma palavra de cada vez.</p>
      <small>© 2026 Redaê</small>
    </footer>
  )
}
