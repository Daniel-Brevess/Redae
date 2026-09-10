import { useCallback, useEffect, useState, useSyncExternalStore } from 'react'
import { AuthCard, type AuthCardType } from './components/auth/AuthCard'
import { BenefitsSection } from './components/landing/BenefitsSection'
import { HeroSection } from './components/landing/HeroSection'
import { HowItWorksSection } from './components/landing/HowItWorksSection'
import { SiteFooter } from './components/layout/SiteFooter'
import { SiteHeader } from './components/layout/SiteHeader'
import { PrototypeExperience } from './components/prototype/PrototypeExperience'
import { AuthProvider, useAuth } from './auth/AuthContext'
import { AdminDashboard } from './components/admin/AdminDashboard'

type AppPath = '/' | '/home' | '/admin'

function getPath(): AppPath {
  if (window.location.pathname === '/admin') return '/admin'
  return window.location.pathname === '/home' ? '/home' : '/'
}

function subscribeToPath(onChange: () => void) {
  window.addEventListener('popstate', onChange)
  return () => window.removeEventListener('popstate', onChange)
}

function AppContent() {
  const [activeCard, setActiveCard] = useState<AuthCardType | null>(null)
  const path = useSyncExternalStore(subscribeToPath, getPath, () => '/')
  useEffect(() => {
    if (window.location.hash === '#treino') {
      window.history.replaceState({}, '', '/home')
      window.dispatchEvent(new PopStateEvent('popstate'))
    }
  }, [])
  const { user, accessToken, loading, signOut } = useAuth()
  const closeCard = () => setActiveCard(null)

  const navigate = useCallback((nextPath: AppPath) => {
    if (window.location.pathname !== nextPath || window.location.hash) {
      window.history.pushState({}, '', nextPath)
    }
    window.dispatchEvent(new PopStateEvent('popstate'))
  }, [])

  useEffect(() => {
    if (loading) return
    if (user && path === '/') {
      navigate('/home')
      return
    }
    if (!user && (path === '/home' || path === '/admin')) navigate('/')
  }, [loading, navigate, path, user])

  const closePrototype = async () => {
    await signOut().catch(() => undefined)
    navigate('/')
  }

  if (path === '/admin') {
    if (loading || !user) return null
    return <AdminDashboard user={user} accessToken={accessToken} onBack={() => navigate('/home')} />
  }

  if (path === '/home') {
    if (loading || !user) return null
    return (
      <PrototypeExperience
        onExit={closePrototype}
        onOpenAdmin={() => navigate('/admin')}
        user={user}
      />
    )
  }

  return (
    <main>
      <SiteHeader activeCard={activeCard} onCardChange={setActiveCard} onNavigate={closeCard} />
      {activeCard && (
        <AuthCard
          type={activeCard}
          onClose={closeCard}
          onEnterPrototype={() => navigate('/home')}
          onAuthenticated={() => navigate('/home')}
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
  return (
    <AuthProvider>
      <AppContent />
    </AuthProvider>
  )
}
