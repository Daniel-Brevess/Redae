import { cleanup, render, screen, within } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { afterEach, expect, it, vi } from 'vitest'
import { SiteHeader } from './SiteHeader'

afterEach(cleanup)

it('toggles page links and closes the menu after navigation or Escape', async () => {
  const onNavigate = vi.fn()
  const user = userEvent.setup()
  render(<SiteHeader activeCard={null} onCardChange={vi.fn()} onNavigate={onNavigate} />)
  const menu = document.getElementById('mobile-navigation')!
  expect(menu).not.toBeVisible()
  await user.click(screen.getByRole('button', { name: 'Abrir menu' }))
  expect(screen.getByRole('button', { name: 'Fechar menu' })).toHaveAttribute(
    'aria-expanded',
    'true',
  )
  await user.click(within(menu).getByRole('link', { name: 'Como funciona' }))
  expect(menu).not.toBeVisible()
  expect(onNavigate).toHaveBeenCalled()
  await user.click(screen.getByRole('button', { name: 'Abrir menu' }))
  await user.keyboard('{Escape}')
  expect(screen.getByRole('button', { name: 'Abrir menu' })).toHaveAttribute(
    'aria-expanded',
    'false',
  )
})
