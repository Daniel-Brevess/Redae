import { render, screen, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { describe, expect, it } from 'vitest'
import { PrototypeExperience } from './PrototypeExperience'

describe('prototype main evaluation flow', () => {
  it('allows a student to create and read a simulated evaluation', async () => {
    const user = userEvent.setup()
    render(<PrototypeExperience onExit={() => undefined} />)

    await user.click(screen.getByRole('button', { name: /Começar agora/ }))
    await user.click(screen.getByRole('button', { name: /Escrever agora/ }))
    await user.clear(screen.getByLabelText('Sua redação'))
    await user.type(
      screen.getByLabelText('Sua redação'),
      'A educação transforma oportunidades quando oferece ferramentas para que cada pessoa participe das decisões da sociedade.',
    )
    await user.click(screen.getByRole('button', { name: /Revisar redação/ }))
    expect(screen.getByRole('heading', { name: /Tudo pronto para revisar/ })).toBeInTheDocument()

    await user.click(screen.getByRole('button', { name: /Confirmar e avaliar/ }))
    expect(screen.getByRole('status')).toHaveTextContent('Analisando seu texto')

    await waitFor(
      () =>
        expect(
          screen.getByRole('heading', { name: /Você já sabe por onde continuar/ }),
        ).toBeInTheDocument(),
      { timeout: 2500 },
    )
    expect(screen.getByText('C1')).toBeInTheDocument()

    await user.click(screen.getByRole('button', { name: /Domínio da norma-padrão/ }))
    expect(screen.getByText(/Revise períodos longos/)).toBeInTheDocument()
  })
})
