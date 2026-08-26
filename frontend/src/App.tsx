import { useEffect, useState } from 'react'
import { AuthCard, type AuthCardType } from './components/auth/AuthCard'
import { BenefitsSection } from './components/landing/BenefitsSection'
import { HeroSection } from './components/landing/HeroSection'
import { HowItWorksSection } from './components/landing/HowItWorksSection'
import { SiteFooter } from './components/layout/SiteFooter'
import { SiteHeader } from './components/layout/SiteHeader'
import { PrototypeExperience } from './components/prototype/PrototypeExperience'
import { AuthProvider, useAuth } from './auth/AuthContext'

function AppContent() {
  const [activeCard, setActiveCard] = useState<AuthCardType | null>(null)
  const [showPrototype, setShowPrototype] = useState(() => window.location.hash === '#treino')
  const { user, loading, signOut } = useAuth()
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

  const handleAuthenticated = () => {
    openPrototype()
  }

  const closePrototype = async () => {
    await signOut().catch(() => undefined)
    window.location.hash = ''
    setShowPrototype(false)
  }

  if (showPrototype) {
    return <PrototypeExperience onExit={closePrototype} user={user} />
  }

  if (!loading && user) return <PrototypeExperience onExit={closePrototype} user={user} />

  return (
    <main>
      <SiteHeader activeCard={activeCard} onCardChange={setActiveCard} onNavigate={closeCard} />
      {activeCard && (
        <AuthCard
          type={activeCard}
          onClose={closeCard}
          onEnterPrototype={openPrototype}
          onAuthenticated={handleAuthenticated}
        />
      )}
      <HeroSection />
      <HowItWorksSection />
      <BenefitsSection />
      <SiteFooter />
    </main>
  )
}

export function App() {
  return <AuthProvider><AppContent /></AuthProvider>
}
