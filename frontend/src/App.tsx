import { useEffect, useState } from 'react'
import { AuthCard, type AuthCardType } from './components/auth/AuthCard'
import { BenefitsSection } from './components/landing/BenefitsSection'
import { HeroSection } from './components/landing/HeroSection'
import { HowItWorksSection } from './components/landing/HowItWorksSection'
import { SiteFooter } from './components/layout/SiteFooter'
import { SiteHeader } from './components/layout/SiteHeader'
import { PrototypeExperience } from './components/prototype/PrototypeExperience'

export function App() {
  const [activeCard, setActiveCard] = useState<AuthCardType | null>(null)
  const [showPrototype, setShowPrototype] = useState(() => window.location.hash === '#treino')
  const closeCard = () => setActiveCard(null)

  useEffect(() => {
    const handleHashChange = () => setShowPrototype(window.location.hash === '#treino')
    window.addEventListener('hashchange', handleHashChange)
    return () => window.removeEventListener('hashchange', handleHashChange)
  }, [])

  const openPrototype = () => {
    window.location.hash = 'treino'
    setShowPrototype(true)
  }

  const closePrototype = () => {
    window.location.hash = ''
    setShowPrototype(false)
  }

  if (showPrototype) {
    return <PrototypeExperience onExit={closePrototype} />
  }

  return (
    <main>
      <SiteHeader activeCard={activeCard} onCardChange={setActiveCard} onNavigate={closeCard} />
      {activeCard && (
        <AuthCard type={activeCard} onClose={closeCard} onEnterPrototype={openPrototype} />
      )}
      <HeroSection />
      <HowItWorksSection />
      <BenefitsSection />
      <SiteFooter />
    </main>
  )
}
