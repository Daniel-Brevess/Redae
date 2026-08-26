import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { afterEach, describe, expect, it, vi } from 'vitest'
import { App } from './App'

describe('landing page authentication cards', () => {
  afterEach(() => vi.unstubAllGlobals())

  it('opens the login form and enters the private area after authentication', async () => {
    const response = new Response(
      JSON.stringify({
        data: {
          accessToken: 'token',
          expiresIn: 900,
          user: { id: '1', name: 'Student', email: 'student@example.com', role: 'STUDENT' },
        },
        meta: {},
        traceId: 'test',
      }),
      { status: 200, headers: { 'Content-Type': 'application/json' } },
    )
    vi.stubGlobal(
      'fetch',
      vi.fn().mockImplementation((url: string) => {
        if (url.endsWith('/auth/refresh'))
          return Promise.resolve(new Response(null, { status: 401 }))
        if (url.endsWith('/profile'))
          return Promise.resolve(
            new Response(
              JSON.stringify({
                data: { id: '1', name: 'Student', email: 'student@example.com', role: 'STUDENT' },
                meta: {},
                traceId: 'test',
              }),
              { status: 200, headers: { 'Content-Type': 'application/json' } },
            ),
          )
        return Promise.resolve(response.clone())
      }),
    )
    const user = userEvent.setup()
    render(<App />)

    await user.click(screen.getByRole('button', { name: 'Login' }))
    expect(screen.getByRole('heading', { name: 'Entre na sua conta.' })).toBeInTheDocument()
    await user.type(screen.getByLabelText('Email'), 'student@example.com')
    await user.type(screen.getByLabelText('Senha'), 'password')
    await user.click(screen.getByRole('button', { name: 'Entrar' }))

    expect(await screen.findByRole('heading', { name: 'Olá, Student.' })).toBeInTheDocument()
  })
})
