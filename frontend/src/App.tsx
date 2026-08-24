import { useState } from 'react'
import { AuthCard, type AuthCardType } from './components/auth/AuthCard'
import { BenefitsSection } from './components/landing/BenefitsSection'
import { HeroSection } from './components/landing/HeroSection'
import { HowItWorksSection } from './components/landing/HowItWorksSection'
import { SiteFooter } from './components/layout/SiteFooter'
import { SiteHeader } from './components/layout/SiteHeader'

export function App() {
  const [activeCard, setActiveCard] = useState<AuthCardType | null>(null)
  const closeCard = () => setActiveCard(null)

  return (
    <main>
      <SiteHeader activeCard={activeCard} onCardChange={setActiveCard} onNavigate={closeCard} />
      {activeCard && <AuthCard type={activeCard} onClose={closeCard} />}
      <HeroSection />
      <HowItWorksSection />
      <BenefitsSection />
      <SiteFooter />
    </main>
  )
}
