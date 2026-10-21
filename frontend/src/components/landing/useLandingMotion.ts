import { useEffect, useRef } from 'react'

export function useLandingMotion() {
  const contentRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    const content = contentRef.current
    if (!content || !window.matchMedia || !window.IntersectionObserver) return
    const root = content.closest('.sales-page') ?? content

    const preference = window.matchMedia('(prefers-reduced-motion: reduce)')
    let dispose = () => {}

    const configure = () => {
      dispose()
      if (preference.matches) return

      const targets = Array.from(
        root.querySelectorAll<HTMLElement>(
          '.vsl-hero-copy, .vsl-hero-art, .vsl-pain > div, .vsl-section-heading, .vsl-competencies article, .vsl-phone-wrap, .vsl-demo > div:last-child, .vsl-faq > div, .vsl-final-copy, .vsl-final-scene',
        ),
      )
      const scene = root.querySelector<HTMLElement>('.vsl-final-scene')
      const hands = root.querySelector<HTMLElement>('.hands-scroll')
      hands?.style.setProperty('--hands-away', '68%')
      hands?.style.setProperty('--hands-opacity', '0.22')
      let frame = 0
      let sceneVisible = false
      let handsVisible = false

      const updateScene = () => {
        frame = 0
        if (scene && sceneVisible) {
          const distance = Math.max(0, scene.getBoundingClientRect().bottom - window.innerHeight)
          const intensity = window.innerWidth <= 767 ? 0.125 : 0.25
          scene.style.setProperty(
            '--scene-dock',
            `${Math.min(140, distance * intensity).toFixed(1)}px`,
          )
        }
        if (hands && handsVisible) {
          const rect = hands.getBoundingClientRect()
          // Finish the approach while the scene is centered in the viewport.
          const progress = Math.max(
            0,
            Math.min(
              1,
              (window.innerHeight - rect.top) / (window.innerHeight * 0.55 + rect.height * 0.5),
            ),
          )
          hands.style.setProperty('--hands-away', `${((1 - progress) * 68).toFixed(2)}%`)
          hands.style.setProperty('--hands-opacity', (0.22 + progress * 0.78).toFixed(3))
        }
      }
      const schedule = () => {
        if (!frame) frame = window.requestAnimationFrame(updateScene)
      }

      const revealObserver = new IntersectionObserver(
        (entries) => {
          for (const entry of entries) {
            if (!entry.isIntersecting) continue
            entry.target.classList.remove('reveal-pending')
            revealObserver.unobserve(entry.target)
          }
        },
        { threshold: 0.12, rootMargin: '0px 0px -8% 0px' },
      )

      targets.forEach((target) => {
        const siblings = Array.from(target.parentElement?.children ?? [])
        target.style.setProperty(
          '--reveal-delay',
          `${Math.min(siblings.indexOf(target), 4) * 80}ms`,
        )
        target.classList.add('scroll-reveal', 'reveal-pending')
        revealObserver.observe(target)
      })

      const sceneObserver = new IntersectionObserver(
        (entries) => {
          for (const entry of entries) {
            if (entry.target === scene) sceneVisible = entry.isIntersecting
            if (entry.target === hands) handsVisible = entry.isIntersecting
          }
          if (sceneVisible || handsVisible) schedule()
        },
        { rootMargin: '20% 0px' },
      )
      if (scene) sceneObserver.observe(scene)
      if (hands) sceneObserver.observe(hands)
      window.addEventListener('scroll', schedule, { passive: true })
      window.addEventListener('resize', schedule)

      dispose = () => {
        revealObserver.disconnect()
        sceneObserver.disconnect()
        window.cancelAnimationFrame(frame)
        window.removeEventListener('scroll', schedule)
        window.removeEventListener('resize', schedule)
        targets.forEach((target) => {
          target.classList.remove('scroll-reveal', 'reveal-pending')
          target.style.removeProperty('--reveal-delay')
        })
        scene?.style.removeProperty('--scene-dock')
        hands?.style.removeProperty('--hands-away')
        hands?.style.removeProperty('--hands-opacity')
      }
    }

    configure()
    preference.addEventListener('change', configure)
    return () => {
      dispose()
      preference.removeEventListener('change', configure)
    }
  }, [])

  return contentRef
}
