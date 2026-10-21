import redazinhoUrl from '../../../assets/redazinho-vsl.png'

type SalesPageProps = { onStart: () => void }

const competencies = [
  ['C1', 'Escrita formal', 'Gramática, ortografia e construção das frases.'],
  ['C2', 'Tema e repertório', 'Como você compreende a proposta e desenvolve o tema.'],
  ['C3', 'Argumentação', 'A organização das ideias que sustentam seu ponto de vista.'],
  ['C4', 'Coesão', 'A conexão entre as partes do seu texto.'],
  ['C5', 'Intervenção', 'Uma proposta de solução para o problema apresentado.'],
]

export function SalesPage({ onStart }: SalesPageProps) {
  return (
    <>
      <section id="top" className="vsl-hero page-width">
        <div className="vsl-hero-copy">
          <h1>
            Você pode estar <strong className="hero-highlight">PERDENDO PONTOS</strong>
            <span className="hero-title-ending">na redação e nem sabe.</span>
          </h1>
          <p className="vsl-lede">
            Não espere o ENEM para descobrir seus erros. Envie sua redação, receba uma avaliação nas
            5 competências e{' '}
            <strong className="hero-lede-highlight">
              extraia o máximo do seu potencial na redação.
            </strong>
          </p>
          <button className="vsl-cta" onClick={onStart}>
            Corrigir minha redação agora
          </button>
        </div>
        <div className="vsl-hero-art">
          <div className="vsl-orbit" aria-hidden="true" />
          <img
            className="vsl-mascot"
            src={redazinhoUrl}
            alt="Redazinho, a borracha amarela de mochila verde, fazendo sinal de positivo"
            fetchPriority="high"
          />
          <div className="vsl-art-note">
            <span aria-hidden="true">✦</span>
            <div>
              <strong>Mais clareza. Menos dúvida.</strong>
              <small>Entenda seu texto, competência por competência.</small>
            </div>
          </div>
        </div>
      </section>

      <div className="vsl-strip">
        <div className="page-width">
          <span>Seu texto como ponto de partida.</span>
          <strong>
            Treine. Evolua. Conquiste. <span aria-hidden="true">✦</span>
          </strong>
        </div>
      </div>

      <section className="vsl-section page-width vsl-pain" aria-labelledby="vsl-pain-title">
        <div>
          <p className="eyebrow">Escrever é só o começo</p>
          <h2 id="vsl-pain-title">
            Você estuda, escreve.
            <br />
            Mas sabe <em>onde perde pontos?</em>
          </h2>
        </div>
        <div>
          <p className="vsl-lede">
            Praticar sem entender os próprios erros pode fazer você repetir as mesmas dificuldades
            na próxima redação.
          </p>
          <p className="vsl-body">
            Um argumento que não ficou claro. Uma conexão que faltou. Uma intervenção incompleta.
            Enxergar esses detalhes é o primeiro passo para trabalhar neles.
          </p>
          <a className="vsl-text-link" href="#sobre-nos">
            Veja o que o Redaê analisa <span aria-hidden="true">↓</span>
          </a>
        </div>
      </section>

      <section id="sobre-nos" className="vsl-solution">
        <div className="page-width vsl-section">
          <div className="vsl-section-heading">
            <p className="eyebrow">Um olhar para cada competência</p>
            <h2>
              Descubra o que está te
              <br />
              <em>impedindo de melhorar.</em>
            </h2>
            <p className="vsl-lede">
              O Redaê transforma sua redação em um ponto de partida: nota estimada e orientações
              para guiar sua próxima tentativa.
            </p>
          </div>
          <div className="vsl-competencies">
            {competencies.map(([code, title, description]) => (
              <article key={code}>
                <span>{code}</span>
                <h3>{title}</h3>
                <p>{description}</p>
              </article>
            ))}
          </div>
          <p className="vsl-micro">
            Avaliação automática para apoiar seu treino. Não é uma nota oficial do INEP.
          </p>
        </div>
      </section>

      <section id="como-funciona" className="vsl-section page-width vsl-demo">
        <div className="vsl-phone-wrap">
          <div
            className="vsl-phone"
            role="img"
            aria-label="Exemplo ilustrativo de resultado: nota estimada 760 de 1000, com notas por competência"
          >
            <div className="vsl-phone-island" />
            <p className="vsl-phone-brand">
              Seu resultado <span>✦</span>
            </p>
            <small>NOTA ESTIMADA</small>
            <strong className="vsl-phone-score">
              760<span>/1000</span>
            </strong>
            <p>
              Um texto de cada vez.
              <br />
              <b>Um próximo passo mais claro.</b>
            </p>
            <div className="vsl-phone-rows">
              {[160, 160, 140, 160, 140].map((score, index) => (
                <div key={index}>
                  <span>C{index + 1}</span>
                  <meter min="0" max="200" value={score}>
                    {score} de 200
                  </meter>
                  <b>{score}</b>
                </div>
              ))}
            </div>
            <div className="vsl-phone-tip">
              <b>Olhe com atenção para a C3</b>
              <p>Revise como seus argumentos sustentam o ponto de vista.</p>
            </div>
          </div>
          <p className="vsl-micro">Exemplo ilustrativo · Não representa uma correção real.</p>
        </div>
        <div>
          <p className="eyebrow">Do seu texto ao próximo passo</p>
          <h2>
            Sua próxima correção
            <br />
            começa em <em>3 passos.</em>
          </h2>
          <ol className="vsl-steps">
            <li>
              <span>01</span>
              <div>
                <h3>Escreva ou fotografe</h3>
                <p>Digite seu texto ou envie uma foto. Revise a transcrição antes de continuar.</p>
              </div>
            </li>
            <li>
              <span>02</span>
              <div>
                <h3>Envie para análise</h3>
                <p>Confirme a redação e acompanhe o processamento pela IA.</p>
              </div>
            </li>
            <li>
              <span>03</span>
              <div>
                <h3>Entenda e pratique de novo</h3>
                <p>Consulte a avaliação e use as orientações no seu próximo texto.</p>
              </div>
            </li>
          </ol>
          <button className="vsl-cta" onClick={onStart}>
            Quero começar
          </button>
        </div>
      </section>

      <section className="vsl-section page-width vsl-faq" aria-labelledby="vsl-faq-title">
        <div>
          <p className="eyebrow">Pode perguntar</p>
          <h2 id="vsl-faq-title">
            Não precisa ficar
            <br />
            <em>na dúvida.</em>
          </h2>
          <p className="vsl-lede">
            Clareza sobre seu texto.
            <br />E sobre como tudo funciona.
          </p>
        </div>
        <div className="vsl-questions">
          <details>
            <summary>O que eu recebo na avaliação?</summary>
            <p>
              Uma estimativa da nota nas cinco competências. O primeiro envio é um diagnóstico
              resumido; avaliações completas usam créditos e incluem feedback detalhado para
              orientar seu treino.
            </p>
          </details>
          <details>
            <summary>Essa é a nota que eu tiraria no ENEM?</summary>
            <p>
              A nota é uma estimativa gerada por IA, que pode cometer erros. Ela apoia seus estudos
              e não equivale à correção oficial do INEP nem garante uma nota na prova.
            </p>
          </details>
          <details>
            <summary>Como funciona o pagamento?</summary>
            <p>
              Depois do diagnóstico inicial gratuito, cada avaliação completa utiliza um crédito.
              Consulte o preço na área de créditos antes de comprar. Você confirma o pagamento antes
              da cobrança.
            </p>
          </details>
          <details>
            <summary>Posso enviar uma redação escrita à mão?</summary>
            <p>
              Sim, por foto. A transcrição é experimental: confira e ajuste o texto reconhecido
              antes de confirmar a avaliação. Também é possível digitar sua redação.
            </p>
          </details>
          <details>
            <summary>Onde consulto minhas redações?</summary>
            <p>
              Na sua área autenticada, você pode consultar suas avaliações. O envio e o acesso aos
              resultados exigem uma conta; evite incluir informações pessoais desnecessárias no
              texto.
            </p>
          </details>
        </div>
      </section>

      <section className="vsl-final page-width">
        <p className="eyebrow">O próximo texto começa aqui</p>
        <h2>
          A próxima redação
          <br />
          pode ser <em>melhor.</em>
        </h2>
        <p>Primeiro, descubra onde está errando.</p>
        <button className="vsl-cta" onClick={onStart}>
          Quero corrigir minha redação
        </button>
        <p className="vsl-micro">Seu esforço merece um próximo passo mais claro.</p>
      </section>
    </>
  )
}
