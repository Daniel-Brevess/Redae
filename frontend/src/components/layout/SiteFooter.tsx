import redazinhoUrl from '../../../assets/redazinho-reading.png'

export function SiteFooter() {
  return (
    <footer className="footer">
      <a className="footer-mascot" href="#top" aria-label="Redaê — início">
        <img src={redazinhoUrl} alt="Redazinho sentado lendo um livro verde" loading="lazy" />
      </a>
      <p>Uma palavra de cada vez.</p>
      <small>© 2026 Redaê</small>
    </footer>
  )
}
