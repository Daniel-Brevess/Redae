import { act, cleanup, render, screen } from '@testing-library/react'
import { afterEach, expect, it, vi } from 'vitest'
import { ScrollStory } from './ScrollStory'

afterEach(() => {
  cleanup()
  vi.restoreAllMocks()
  vi.unstubAllGlobals()
})

it('reveals words in order, reaches the last phrase and reverses with scroll', () => {
  vi.stubGlobal(
    'matchMedia',
    vi.fn(() => ({ matches: false, addEventListener: vi.fn(), removeEventListener: vi.fn() })),
  )
  let frame: FrameRequestCallback | undefined
  vi.stubGlobal(
    'requestAnimationFrame',
    vi.fn((callback: FrameRequestCallback) => {
      frame = callback
      return 1
    }),
  )
  let sectionTop = 0
  vi.spyOn(HTMLElement.prototype, 'getBoundingClientRect').mockImplementation(function (
    this: HTMLElement,
  ) {
    return new DOMRect(0, this.classList.contains('scroll-story') ? sectionTop : 0, 1000, 800)
  })
  const { container } = render(<ScrollStory />)
  act(() => frame?.(0))
  expect(container.querySelector('.scroll-story')).toHaveAttribute('data-enhanced', 'true')
  expect(container.querySelector('.story-phrase')).toHaveTextContent('Você estuda,')
  expect(container.querySelectorAll('.is-readable')).toHaveLength(1)

  const scrollToPosition = (position: number) => {
    sectionTop = -(position / 13) * 800 * 4.94
    act(() => {
      window.dispatchEvent(new Event('scroll'))
      frame?.(16)
    })
  }
  scrollToPosition(0.8)
  expect(container.querySelectorAll('.is-readable')).toHaveLength(2)
  scrollToPosition(3.8)
  expect(container.querySelector('.story-phrase')).toHaveTextContent('Sabe onde perde pontos?')
  scrollToPosition(4.8)
  expect(container.querySelector('.story-phrase')).toHaveTextContent(
    'Praticar sem entender os próprios erros pode',
  )
  expect(container.querySelector('.story-word-continuation')).toHaveTextContent('pode')
  scrollToPosition(5.8)
  expect(container.querySelector('.story-phrase')).toHaveTextContent(/^fazer você repetir/)
  expect(container.querySelector('.story-step')).toBeNull()
  expect(container.querySelector('.story-progress')).toBeNull()
  scrollToPosition(13)
  expect(container.querySelector('.story-phrase')).toHaveTextContent('para trabalhar neles')
  expect(container.querySelectorAll('.is-readable')).toHaveLength(3)
  expect(screen.getByText('Seu próximo passo está logo abaixo')).toBeInTheDocument()
  scrollToPosition(0)
  expect(container.querySelector('.story-phrase')).toHaveTextContent('Você estuda,')
})

it('shows the readable static version without a pinned journey for reduced motion', () => {
  vi.stubGlobal(
    'matchMedia',
    vi.fn(() => ({ matches: true, addEventListener: vi.fn(), removeEventListener: vi.fn() })),
  )
  const requestFrame = vi.fn()
  vi.stubGlobal('requestAnimationFrame', requestFrame)
  const { container } = render(<ScrollStory />)
  expect(container.querySelector('.scroll-story')).toHaveAttribute('data-enhanced', 'false')
  expect(
    screen.getByRole('heading', { name: 'Você estuda, escreve. Mas sabe onde perde pontos?' }),
  ).toBeInTheDocument()
  expect(requestFrame).not.toHaveBeenCalled()
})
