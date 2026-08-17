import { StrictMode, useEffect, useRef, useState } from 'react'
import { createRoot } from 'react-dom/client'
import './styles.css'

function App() {
  const [activeCard, setActiveCard] = useState<'login' | 'signup' | null>(null)
  const authCardRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    if (!activeCard) return

    const handleEscape = (event: KeyboardEvent) => {
      if (event.key === 'Escape') setActiveCard(null)
    }

    const handleOutsideClick = (event: MouseEvent) => {
      if (authCardRef.current && !authCardRef.current.contains(event.target as Node)) {
        setActiveCard(null)
      }
    }

    document.addEventListener('keydown', handleEscape)
    document.addEventListener('mousedown', handleOutsideClick)

    return () => {
      document.removeEventListener('keydown', handleEscape)
      document.removeEventListener('mousedown', handleOutsideClick)
    }
  }, [activeCard])

  const closeCard = () => setActiveCard(null)

  return (
    <main>
      <header className="site-header">
        <a className="logo" href="#top">Reda<span>ê</span></a>
        <nav className="site-nav" aria-label="Navegacao principal">
          <a className="nav-link" href="#como-funciona" onClick={closeCard}>Como funciona</a>
          <a className="nav-link" href="#sobre-nos" onClick={closeCard}>Sobre nós</a>
          <button className="nav-link nav-button" type="button" onClick={() => setActiveCard((current) => current === 'login' ? null : 'login')}>Login</button>
          <button className="nav-link nav-link-cta nav-button" type="button" onClick={() => setActiveCard((current) => current === 'signup' ? null : 'signup')}>Cadastre-se</button>
        </nav>
      </header>
      {activeCard && (
        <div className="auth-card-wrap" ref={authCardRef}>
          <section className="auth-card" role="dialog" aria-modal="false" aria-labelledby={`${activeCard}-title`}>
            <button className="auth-close" type="button" aria-label="Fechar" onClick={closeCard}>×</button>
            {activeCard === 'login' ? (
              <>
                <p className="eyebrow">Bem-vindo de volta</p>
                <h2 id="login-title">Entre na sua conta.</h2>
                <form className="auth-form" onSubmit={(event) => event.preventDefault()}>
                  <label htmlFor="login-email">Email</label>
                  <input id="login-email" name="email" type="email" autoComplete="email" required />
                  <label htmlFor="login-password">Senha</label>
                  <input id="login-password" name="password" type="password" autoComplete="current-password" required />
                  <button className="auth-submit" type="submit">Entrar</button>
                </form>
              </>
            ) : (
              <>
                <p className="eyebrow">Comece sua evolução</p>
                <h2 id="signup-title">Crie sua conta.</h2>
                <form className="auth-form" onSubmit={(event) => event.preventDefault()}>
                  <label htmlFor="signup-name">Nome</label>
                  <input id="signup-name" name="name" type="text" autoComplete="name" required />
                  <label htmlFor="signup-email">Email</label>
                  <input id="signup-email" name="email" type="email" autoComplete="email" required />
                  <label htmlFor="signup-password">Senha</label>
                  <input id="signup-password" name="password" type="password" autoComplete="new-password" minLength={8} required />
                  <label htmlFor="signup-password-confirmation">Confirmar senha</label>
                  <input id="signup-password-confirmation" name="passwordConfirmation" type="password" autoComplete="new-password" minLength={8} required />
                  <button className="auth-submit" type="submit">Cadastrar</button>
                </form>
              </>
            )}
          </section>
        </div>
      )}
      <section id="top" className="hero page-width"><div className="hero-copy"><p className="eyebrow">Seu treino de redação, do seu jeito</p><h1>Escreva melhor.<br /><em>Chegue mais longe.</em></h1><p className="lede">O Redaê transforma seus objetivos em uma rotina simples de prática para você evoluir na redação do ENEM com mais clareza e confiança.</p><a className="button" href="#como-funciona">Quero conhecer <span>→</span></a></div><div className="hero-card"><div className="card-top"><span className="dot" /> seu próximo passo</div><p>Uma ideia de cada vez.<br /><strong>Um texto de cada vez.</strong></p><div className="progress"><span /></div><small>consistência que aparece</small></div></section>
      <section id="como-funciona" className="how page-width"><div><p className="eyebrow">Como funciona</p><h2>Menos ansiedade.<br />Mais evolução.</h2></div><div className="steps"><article><b>01</b><h3>Entenda onde você está</h3><p>Um ponto de partida claro para enxergar seus próximos avanços.</p></article><article><b>02</b><h3>Pratique com intenção</h3><p>Exercícios que fazem sentido para o seu momento e sua meta.</p></article><article><b>03</b><h3>Veja sua evolução</h3><p>Feedback simples para transformar esforço em confiança.</p></article></div></section>
      <section className="benefits page-width"><div className="benefits-heading"><p className="eyebrow">Feito para você</p><h2>Seu ritmo.<br /><span>Sua conquista.</span></h2></div><div className="benefit-list"><p>✦ Treino personalizado</p><p>✦ Linguagem simples e direta</p><p>✦ Foco no que realmente importa</p></div></section>
      <footer className="footer page-width"><a className="logo" href="#top">Reda<span>ê</span></a><p>Uma palavra de cada vez.</p><small>© 2026 Redaê</small></footer>
    </main>
  )
}

createRoot(document.getElementById('root')!).render(<StrictMode><App /></StrictMode>)
