import { cleanup, render, screen, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { afterEach, describe, expect, it, vi } from 'vitest'
import { AdminDashboard } from './AdminDashboard'
import { getAdminUserCount, getAdminUsers, grantAdminCredits } from '../../api/adminApi'

vi.mock('../../api/adminApi', () => ({
  getAdminUserCount: vi.fn(),
  getAdminUsers: vi.fn(),
  grantAdminCredits: vi.fn(),
}))

const user = (role: 'ADMIN' | 'STUDENT') => ({
  id: '1',
  name: 'Usuário',
  email: 'user@example.com',
  role,
  emailVerified: true,
  credits: 0,
})

describe('AdminDashboard', () => {
  afterEach(cleanup)

  afterEach(() => {
    vi.clearAllMocks()
  })

  it('renders the total users returned by the backend', async () => {
    vi.mocked(getAdminUserCount).mockResolvedValue({
      data: { totalUsers: 4 },
      meta: {},
      traceId: 'trace-id',
    })
    vi.mocked(getAdminUsers).mockResolvedValue({
      data: [],
      meta: { page: 0, size: 20, totalElements: 0, totalPages: 0, hasNext: false },
      traceId: 'trace-id',
    })

    render(<AdminDashboard user={user('ADMIN')} accessToken="token" onBack={vi.fn()} />)

    expect(await screen.findByText('4')).toBeInTheDocument()
  })

  it('renders users returned by the paginated endpoint', async () => {
    vi.mocked(getAdminUserCount).mockResolvedValue({
      data: { totalUsers: 1 },
      meta: {},
      traceId: 'trace-id',
    })
    vi.mocked(getAdminUsers).mockResolvedValue({
      data: [
        {
          id: 'user-id',
          name: 'Estudante',
          email: 'student@example.com',
          role: 'STUDENT',
          emailVerified: false,
          credits: 5,
        },
      ],
      meta: { page: 0, size: 20, totalElements: 1, totalPages: 1, hasNext: false },
      traceId: 'trace-id',
    })

    render(<AdminDashboard user={user('ADMIN')} accessToken="token" onBack={vi.fn()} />)

    expect(await screen.findByText('Estudante')).toBeInTheDocument()
    expect(screen.getByText('student@example.com')).toBeInTheDocument()
    expect(screen.getByText('E-mail pendente')).toBeInTheDocument()
    expect(screen.getByText('5 créditos disponíveis')).toBeInTheDocument()
  })

  it('renders the administrative layout for an admin', () => {
    vi.mocked(getAdminUserCount).mockResolvedValue({
      data: { totalUsers: 0 },
      meta: {},
      traceId: 'trace-id',
    })
    vi.mocked(getAdminUsers).mockResolvedValue({
      data: [],
      meta: { page: 0, size: 20, totalElements: 0, totalPages: 0, hasNext: false },
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

  it('searches users and grants credits from the user card', async () => {
    const listedUser = {
      id: 'user-id',
      name: 'Estudante',
      email: 'student@example.com',
      role: 'STUDENT',
      emailVerified: false,
      credits: 0,
    }
    vi.mocked(getAdminUserCount).mockResolvedValue({
      data: { totalUsers: 1 },
      meta: {},
      traceId: 'trace-id',
    })
    vi.mocked(getAdminUsers).mockResolvedValue({
      data: [listedUser],
      meta: { page: 0, size: 20, totalElements: 1, totalPages: 1, hasNext: false },
      traceId: 'trace-id',
    })
    vi.mocked(grantAdminCredits).mockResolvedValue({
      data: { userId: listedUser.id, credits: 5 },
      meta: {},
      traceId: 'trace-id',
    })

    const userEventSetup = userEvent.setup()
    render(<AdminDashboard user={user('ADMIN')} accessToken="token" onBack={vi.fn()} />)
    await userEventSetup.type(await screen.findByPlaceholderText('Nome ou e-mail'), 'student')
    await waitFor(() => expect(getAdminUsers).toHaveBeenLastCalledWith('token', 0, 20, 'student'))
    await userEventSetup.click(screen.getByRole('button', { name: 'Adicionar créditos' }))
    await userEventSetup.type(
      screen.getByRole('spinbutton', { name: 'Quantidade de créditos para Estudante' }),
      '5',
    )
    await userEventSetup.click(screen.getByRole('button', { name: 'Adicionar' }))

    expect(grantAdminCredits).toHaveBeenCalledWith('token', listedUser.id, 5)
    expect(await screen.findByText('Créditos adicionados com sucesso.')).toBeInTheDocument()
    expect(await screen.findByText('5 créditos disponíveis')).toBeInTheDocument()
  })
})
