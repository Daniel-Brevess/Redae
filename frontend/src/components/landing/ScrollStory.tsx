import { useEffect, useRef, useState } from 'react'

const storyPhrases = [
  'Você estuda,',
  'escreve.',
  'Mas…',
  'Sabe onde perde pontos?',
  'Praticar sem entender os próprios erros pode',
  'fazer você repetir as mesmas dificuldades na próxima redação',
  'Um argumento que não ficou claro',
  'Uma conexão que faltou.',
  'Uma intervenção incompleta.',
  'Enxergar',
  'Esses detalhes',
  'é o primeiro passo',
  'para trabalhar neles',
]

type Reading = { step: number; words: number }

export function ScrollStory() {
  const sectionRef = useRef<HTMLElement>(null)
  const panelRef = useRef<HTMLDivElement>(null)
  const [reading, setReading] = useState<Reading | null>(null)

  useEffect(() => {
    if (!window.matchMedia) return
    const preference = window.matchMedia('(prefers-reduced-motion: reduce)')
    let dispose = () => {}
    const configure = () => {
      dispose()
      if (preference.matches) {
        setReading(null)
        return
      }
      let frame = 0
      const update = () => {
        frame = 0
        const section = sectionRef.current
        if (!section) return
        const height = panelRef.current?.getBoundingClientRect().height || window.innerHeight
        const progress = Math.max(
          0,
          Math.min(1, -section.getBoundingClientRect().top / (height * 4.94)),
        )
        const position = progress * storyPhrases.length
        const step = Math.min(storyPhrases.length - 1, Math.floor(position))
        const count = storyPhrases[step].split(' ').length
        const words = Math.max(1, Math.ceil(Math.min(1, (position - step) / 0.7) * count))
        setReading((previous) =>
          previous?.step === step && previous.words === words ? previous : { step, words },
        )
      }
      const schedule = () => {
        if (!frame) frame = window.requestAnimationFrame(update)
      }
      schedule()
      window.addEventListener('scroll', schedule, { passive: true })
      window.addEventListener('resize', schedule)
      dispose = () => {
        window.cancelAnimationFrame(frame)
        window.removeEventListener('scroll', schedule)
        window.removeEventListener('resize', schedule)
      }
    }
    configure()
    preference.addEventListener('change', configure)
    return () => {
      dispose()
      preference.removeEventListener('change', configure)
    }
  }, [])

  const step = reading?.step ?? 0
  const phrase = storyPhrases[step]
  const tone = [3, 4, 9, 10, 11, 12].includes(step) ? 'green' : step === 2 ? 'blue' : 'ink'

  return (
    <section
      ref={sectionRef}
      className="scroll-story"
      data-enhanced={reading !== null}
      aria-label="Entenda onde perde pontos na redação"
    >
      <div className="story-panel" ref={panelRef}>
        <div className="story-static">
          <h2>Você estuda, escreve. Mas sabe onde perde pontos?</h2>
          {storyPhrases.slice(4).map((text) => (
            <p key={text}>{text}</p>
          ))}
        </div>
        <div className="story-visual" aria-hidden="true">
          <div
            key={step}
            className={`story-phrase story-tone-${tone}${phrase.length > 35 ? ' story-phrase-long' : ''}`}
          >
            {phrase.split(' ').map((word, index) => (
              <span
                key={index}
                className={`${index < (reading?.words ?? 0) ? 'story-word is-readable' : 'story-word'}${step === 4 && word === 'pode' ? ' story-word-continuation' : ''}`}
              >
                {word}{' '}
              </span>
            ))}
          </div>
          <span className="story-scroll-hint">
            {step === storyPhrases.length - 1
              ? 'Seu próximo passo está logo abaixo'
              : 'Continue rolando para descobrir'}
          </span>
        </div>
      </div>
    </section>
  )
}
