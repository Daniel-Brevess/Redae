import { createContext, useContext, useEffect, useMemo, useState, type ReactNode } from 'react'
import {
  login,
  logout,
  profile,
  refresh,
  register,
  type AuthData,
  type RegisterInput,
  type User,
} from '../api/authApi'

type AuthContextValue = {
  user: User | null
  accessToken: string | null
  loading: boolean
  signUp: (input: RegisterInput) => Promise<void>
  signIn: (email: string, password: string) => Promise<void>
  signOut: () => Promise<void>
}
const AuthContext = createContext<AuthContextValue | null>(null)

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(null)
  const [accessToken, setAccessToken] = useState<string | null>(null)
  const [loading, setLoading] = useState(true)

  async function establishSession(data: AuthData) {
    setAccessToken(data.accessToken)
    const currentProfile = await profile(data.accessToken)
    setUser(currentProfile.data)
  }

  useEffect(() => {
    refresh()
      .then(({ data }) => establishSession(data))
      .catch(() => undefined)
      .finally(() => setLoading(false))
  }, [])
  const value = useMemo<AuthContextValue>(
    () => ({
      user,
      accessToken,
      loading,
      async signUp(input) {
        await register(input)
        const result = await login(input.email, input.password)
        await establishSession(result.data)
      },
      async signIn(email, password) {
        const result = await login(email, password)
        await establishSession(result.data)
      },
      async signOut() {
        await logout()
        setAccessToken(null)
        setUser(null)
      },
    }),
    [accessToken, loading, user],
  )
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export function useAuth() {
  const context = useContext(AuthContext)
  if (!context) throw new Error('useAuth deve ser usado dentro de AuthProvider')
  return context
}
