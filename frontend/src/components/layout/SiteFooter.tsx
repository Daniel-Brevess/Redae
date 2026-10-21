import redazinhoUrl from '../../../assets/redazinho-reading.png'

export function SiteFooter({ onStart }: { onStart: () => void }) {
  return (
    <footer className="footer">
      <div className="footer-inner">
        <nav className="footer-columns" aria-label="Navegação do rodapé">
          <div>
            <h3>Explore o Redaê</h3>
            <a href="#top">Início</a>
            <a href="#como-funciona">Como funciona</a>
            <a href="#sobre-nos">As cinco competências</a>
            <a href="#duvidas">Perguntas frequentes</a>
          </div>
          <div>
            <h3>Seu próximo texto</h3>
            <button onClick={onStart}>Começar uma correção</button>
            <a href="#como-funciona">Enviar por texto ou foto</a>
            <a href="#demonstracao">Ver a demonstração</a>
          </div>
          <div>
            <h3>Antes de começar</h3>
            <a href="#duvidas">Diagnóstico e créditos</a>
            <a href="#duvidas">Como a avaliação funciona</a>
            <a href="#duvidas">Cuidados com seu texto</a>
          </div>
          <div className="footer-invitation">
            <span className="footer-spark" aria-hidden="true">
              ✦
            </span>
            <h3>Treine. Evolua. Conquiste.</h3>
            <p>
              Uma palavra de cada vez.
              <br />
              Um próximo passo mais claro.
            </p>
            <button onClick={onStart}>Vamos ao próximo passo?</button>
          </div>
        </nav>
        <div className="footer-bottom">
          <a className="footer-mascot" href="#top" aria-label="Redaê — início">
            <img src={redazinhoUrl} alt="Redazinho sentado lendo um livro verde" loading="lazy" />
          </a>
          <p>© 2026 Redaê. Feito para acompanhar sua evolução.</p>
          <a href="#top" className="footer-back-top">
            Voltar ao início
          </a>
        </div>
        <p className="footer-disclaimer">
          O Redaê usa inteligência artificial para apoiar o treino de redação. As notas são
          estimativas, podem conter erros e não equivalem à avaliação oficial do INEP. Não há
          garantia de resultado no ENEM.
        </p>
      </div>
    </footer>
  )
}
