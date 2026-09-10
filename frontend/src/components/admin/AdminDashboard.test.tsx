import { cleanup, render, screen } from '@testing-library/react'
import { afterEach, describe, expect, it, vi } from 'vitest'
import { AdminDashboard } from './AdminDashboard'

const user = (role: 'ADMIN' | 'STUDENT') => ({
  id: '1',
  name: 'Usuário',
  email: 'user@example.com',
  role,
  emailVerified: true,
})

describe('AdminDashboard', () => {
  afterEach(cleanup)

  it('renders the administrative layout for an admin', () => {
    render(<AdminDashboard user={user('ADMIN')} onBack={vi.fn()} />)

    expect(screen.getByRole('heading', { name: 'Olá, Usuário.' })).toBeInTheDocument()
    expect(screen.getByRole('region', { name: 'Indicadores administrativos' })).toBeInTheDocument()
  })

  it('blocks a student with an access denied state', () => {
    render(<AdminDashboard user={user('STUDENT')} onBack={vi.fn()} />)

    expect(screen.getByRole('heading', { name: 'Acesso negado.' })).toBeInTheDocument()
    expect(
      screen.queryByRole('region', { name: 'Indicadores administrativos' }),
    ).not.toBeInTheDocument()
  })
})
