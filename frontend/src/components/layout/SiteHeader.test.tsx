import { cleanup, render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { afterEach, expect, it, vi } from 'vitest'
import { SiteHeader } from './SiteHeader'

afterEach(cleanup)

it('toggles page links and closes the menu after navigation or Escape', async () => {
  const onNavigate = vi.fn()
  const user = userEvent.setup()
  render(<SiteHeader activeCard={null} onCardChange={vi.fn()} onNavigate={onNavigate} />)
  expect(screen.queryByRole('link', { name: 'Como funciona' })).not.toBeInTheDocument()
  await user.click(screen.getByRole('button', { name: 'Abrir menu' }))
  expect(screen.getByRole('button', { name: 'Fechar menu' })).toHaveAttribute(
    'aria-expanded',
    'true',
  )
  await user.click(screen.getByRole('link', { name: 'Como funciona' }))
  expect(screen.queryByRole('link', { name: 'Sobre nós' })).not.toBeInTheDocument()
  expect(onNavigate).toHaveBeenCalled()
  await user.click(screen.getByRole('button', { name: 'Abrir menu' }))
  await user.keyboard('{Escape}')
  expect(screen.getByRole('button', { name: 'Abrir menu' })).toHaveAttribute(
    'aria-expanded',
    'false',
  )
})
