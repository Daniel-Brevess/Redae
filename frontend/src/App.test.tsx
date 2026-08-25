import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { describe, expect, it } from 'vitest'
import { App } from './App'

describe('landing page authentication cards', () => {
  it('opens the login form and shows feedback after submission', async () => {
    const user = userEvent.setup()
    render(<App />)

    await user.click(screen.getByRole('button', { name: 'Login' }))
    expect(screen.getByRole('heading', { name: 'Entre na sua conta.' })).toBeInTheDocument()

    await user.type(screen.getByLabelText('Email'), 'student@example.com')
    await user.type(screen.getByLabelText('Senha'), 'password')
    await user.click(screen.getByRole('button', { name: 'Entrar' }))

    expect(screen.getByRole('status')).toHaveTextContent('Formulário pronto para integração.')
  })
})
