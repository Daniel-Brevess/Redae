import { cleanup, render, screen } from '@testing-library/react'
import { afterEach, describe, expect, it, vi } from 'vitest'
import { AdminDashboard } from './AdminDashboard'
import { getAdminUserCount } from '../../api/adminApi'

vi.mock('../../api/adminApi', () => ({
  getAdminUserCount: vi.fn(),
}))

const user = (role: 'ADMIN' | 'STUDENT') => ({
  id: '1',
  name: 'Usuário',
  email: 'user@example.com',
  role,
  emailVerified: true,
})

describe('AdminDashboard', () => {
  afterEach(cleanup)

  it('renders the total users returned by the backend', async () => {
    vi.mocked(getAdminUserCount).mockResolvedValue({
      data: { totalUsers: 4 },
      meta: {},
      traceId: 'trace-id',
    })

    render(<AdminDashboard user={user('ADMIN')} accessToken="token" onBack={vi.fn()} />)

    expect(await screen.findByText('4')).toBeInTheDocument()
  })

  it('renders the administrative layout for an admin', () => {
    vi.mocked(getAdminUserCount).mockResolvedValue({
      data: { totalUsers: 0 },
      meta: {},
      traceId: 'trace-id',
    })
    render(<AdminDashboard user={user('ADMIN')} accessToken="token" onBack={vi.fn()} />)

    expect(screen.getByRole('heading', { name: 'Olá, Usuário.' })).toBeInTheDocument()
    expect(screen.getByRole('region', { name: 'Indicadores administrativos' })).toBeInTheDocument()
  })

  it('blocks a student with an access denied state', () => {
    render(<AdminDashboard user={user('STUDENT')} accessToken={null} onBack={vi.fn()} />)

    expect(screen.getByRole('heading', { name: 'Acesso negado.' })).toBeInTheDocument()
    expect(
      screen.queryByRole('region', { name: 'Indicadores administrativos' }),
    ).not.toBeInTheDocument()
  })
})
