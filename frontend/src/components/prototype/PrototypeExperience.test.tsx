import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { afterEach, describe, expect, it, vi } from 'vitest'
import { PrototypeExperience } from './PrototypeExperience'

describe('prototype main evaluation flow', () => {
  afterEach(() => vi.unstubAllGlobals())

  it('sends a confirmed typed essay to the evaluations API', async () => {
    const fetchMock = vi.fn().mockResolvedValue(
      new Response(
        JSON.stringify({
          data: {
            id: 'evaluation-1',
            theme: 'Os desafios da educação digital no Brasil',
            type: 'DIAGNOSTICO',
            origin: 'DIGITADA',
            status: 'PROCESSANDO',
            createdAt: '2026-08-27T12:00:00Z',
          },
          meta: {},
          traceId: 'test-trace',
        }),
        { status: 201, headers: { 'Content-Type': 'application/json' } },
      ),
    )
    vi.stubGlobal('fetch', fetchMock)

    const user = userEvent.setup()
    render(<PrototypeExperience onExit={() => undefined} />)

    await user.click(screen.getByRole('button', { name: /Começar agora/ }))
    await user.click(screen.getByRole('button', { name: /Escrever agora/ }))
    await user.clear(screen.getByLabelText('Sua redação'))
    const essay =
      'A educação transforma oportunidades quando oferece ferramentas para que cada pessoa participe das decisões da sociedade.'
    await user.type(screen.getByLabelText('Sua redação'), essay)
    await user.click(screen.getByRole('button', { name: /Revisar redação/ }))
    expect(screen.getByRole('heading', { name: /Tudo pronto para revisar/ })).toBeInTheDocument()

    await user.click(screen.getByRole('button', { name: /Confirmar e avaliar/ }))
    expect(await screen.findByRole('status')).toHaveTextContent('Analisando seu texto')
    expect(fetchMock).toHaveBeenCalledWith(
      'http://localhost:8080/api/v1/evaluations',
      expect.objectContaining({
        method: 'POST',
        body: JSON.stringify({
          origin: 'DIGITADA',
          theme: 'Os desafios da educação digital no Brasil',
          text: essay,
        }),
      }),
    )
  })
})
