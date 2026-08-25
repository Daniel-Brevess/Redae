export function HeroSection() {
  return (
    <section id="top" className="hero page-width">
      <div className="hero-copy">
        <p className="eyebrow">Seu treino de redação, do seu jeito</p>
        <h1>
          Escreva melhor.
          <br />
          <em>Chegue mais longe.</em>
        </h1>
        <p className="lede">
          O Redaê transforma seus objetivos em uma rotina simples de prática para você evoluir na
          redação do ENEM com mais clareza e confiança.
        </p>
        <a className="button" href="#como-funciona">
          Quero conhecer <span>→</span>
        </a>
      </div>
      <div className="hero-card">
        <div className="card-top">
          <span className="dot" /> seu próximo passo
        </div>
        <p>
          Uma ideia de cada vez.
          <br />
          <strong>Um texto de cada vez.</strong>
        </p>
        <div className="progress">
          <span />
        </div>
        <small>consistência que aparece</small>
      </div>
    </section>
  )
}
