import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './styles.css'

function App() {
  return (
    <main>
      <header
        style={{
          position: 'fixed',
          top: '0',
          left: '0',
          zIndex: 10,
          width: '100%',
          height: '80px',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
          padding: '0 max(24px, calc((100% - 1120px) / 2))',
          borderBottom: '1px solid #cbd5e1',
          background: 'rgba(248, 250, 252, 0.82)',
          backdropFilter: 'blur(12px)',
          WebkitBackdropFilter: 'blur(12px)',
        }}
      >
        <a className="logo" href="#top">Reda<span>ê</span></a>
        <a className="nav-link" href="#como-funciona">Como funciona</a>
      </header>
      <section id="top" className="hero page-width"><div className="hero-copy"><p className="eyebrow">Seu treino de redação, do seu jeito</p><h1>Escreva melhor.<br /><em>Chegue mais longe.</em></h1><p className="lede">O Redaê transforma seus objetivos em uma rotina simples de prática para você evoluir na redação do ENEM com mais clareza e confiança.</p><a className="button" href="#como-funciona">Quero conhecer <span>→</span></a></div><div className="hero-card"><div className="card-top"><span className="dot" /> seu próximo passo</div><p>Uma ideia de cada vez.<br /><strong>Um texto de cada vez.</strong></p><div className="progress"><span /></div><small>consistência que aparece</small></div></section>
      <section id="como-funciona" className="how page-width"><div><p className="eyebrow">Como funciona</p><h2>Menos ansiedade.<br />Mais evolução.</h2></div><div className="steps"><article><b>01</b><h3>Entenda onde você está</h3><p>Um ponto de partida claro para enxergar seus próximos avanços.</p></article><article><b>02</b><h3>Pratique com intenção</h3><p>Exercícios que fazem sentido para o seu momento e sua meta.</p></article><article><b>03</b><h3>Veja sua evolução</h3><p>Feedback simples para transformar esforço em confiança.</p></article></div></section>
      <section className="benefits page-width"><div className="benefits-heading"><p className="eyebrow">Feito para você</p><h2>Seu ritmo.<br /><span>Sua conquista.</span></h2></div><div className="benefit-list"><p>✦ Treino personalizado</p><p>✦ Linguagem simples e direta</p><p>✦ Foco no que realmente importa</p></div></section>
      <footer className="footer page-width"><a className="logo" href="#top">Reda<span>ê</span></a><p>Uma palavra de cada vez.</p><small>© 2026 Redaê</small></footer>
    </main>
  )
}

createRoot(document.getElementById('root')!).render(<StrictMode><App /></StrictMode>)
