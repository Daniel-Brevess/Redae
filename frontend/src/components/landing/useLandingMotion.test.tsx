import { cleanup, render, screen } from '@testing-library/react'
import { afterEach, expect, it, vi } from 'vitest'
import { useLandingMotion } from './useLandingMotion'

function MotionFixture() {
  const ref = useLandingMotion()
  return (
    <div ref={ref}>
      <section className="vsl-hero-copy">
        <h2>Seu próximo texto</h2>
      </section>
      <div className="vsl-final-scene" data-testid="scene" />
      <div className="hands-scroll" data-testid="hands" />
    </div>
  )
}

afterEach(() => {
  cleanup()
  vi.restoreAllMocks()
  vi.unstubAllGlobals()
})

it('reveals content on entry and removes animation state on unmount', () => {
  const observers: {
    callback: IntersectionObserverCallback
    observe: ReturnType<typeof vi.fn>
    disconnect: ReturnType<typeof vi.fn>
    unobserve: ReturnType<typeof vi.fn>
  }[] = []
  vi.stubGlobal(
    'matchMedia',
    vi.fn(() => ({ matches: false, addEventListener: vi.fn(), removeEventListener: vi.fn() })),
  )
  vi.stubGlobal(
    'IntersectionObserver',
    vi.fn(function (callback: IntersectionObserverCallback) {
      const observer = { callback, observe: vi.fn(), disconnect: vi.fn(), unobserve: vi.fn() }
      observers.push(observer)
      return observer
    }),
  )
  vi.spyOn(HTMLElement.prototype, 'getBoundingClientRect').mockReturnValue(
    new DOMRect(0, 0, 200, 200),
  )
  const { unmount } = render(<MotionFixture />)
  const heading = screen.getByRole('heading').parentElement!
  expect(heading).toHaveClass('reveal-pending')
  observers[0].callback(
    [
      {
        target: heading,
        isIntersecting: true,
        boundingClientRect: new DOMRect(),
        intersectionRect: new DOMRect(),
        intersectionRatio: 1,
        rootBounds: null,
        time: 0,
      },
    ],
    {} as IntersectionObserver,
  )
  expect(heading).not.toHaveClass('reveal-pending')
  expect(observers[0].unobserve).toHaveBeenCalledWith(heading)
  unmount()
  expect(observers.every((observer) => observer.disconnect.mock.calls.length === 1)).toBe(true)
  expect(heading).not.toHaveClass('scroll-reveal')
})

it('keeps content visible and does not animate with reduced motion', () => {
  vi.stubGlobal(
    'matchMedia',
    vi.fn(() => ({ matches: true, addEventListener: vi.fn(), removeEventListener: vi.fn() })),
  )
  const observer = vi.fn()
  vi.stubGlobal('IntersectionObserver', observer)
  render(<MotionFixture />)
  expect(screen.getByRole('heading')).not.toHaveClass('reveal-pending')
  expect(observer).not.toHaveBeenCalled()
  expect(screen.getByTestId('scene').style.getPropertyValue('--scene-dock')).toBe('')
  expect(screen.getByTestId('hands').style.getPropertyValue('--hands-away')).toBe('')
})

it('brings the hands together with scroll and separates them when scrolling back', () => {
  const callbacks: IntersectionObserverCallback[] = []
  vi.stubGlobal(
    'matchMedia',
    vi.fn(() => ({ matches: false, addEventListener: vi.fn(), removeEventListener: vi.fn() })),
  )
  vi.stubGlobal(
    'IntersectionObserver',
    vi.fn(function (callback: IntersectionObserverCallback) {
      callbacks.push(callback)
      return { observe: vi.fn(), unobserve: vi.fn(), disconnect: vi.fn() }
    }),
  )
  let nextFrame: FrameRequestCallback | undefined
  vi.stubGlobal(
    'requestAnimationFrame',
    vi.fn((callback: FrameRequestCallback) => {
      nextFrame = callback
      return 1
    }),
  )
  let top = window.innerHeight
  vi.spyOn(HTMLElement.prototype, 'getBoundingClientRect').mockImplementation(
    () => new DOMRect(0, top, 600, 300),
  )
  render(<MotionFixture />)
  const hands = screen.getByTestId('hands')
  callbacks[1](
    [
      {
        target: hands,
        isIntersecting: true,
        boundingClientRect: new DOMRect(),
        intersectionRect: new DOMRect(),
        intersectionRatio: 1,
        rootBounds: null,
        time: 0,
      },
    ],
    {} as IntersectionObserver,
  )
  nextFrame?.(0)
  expect(hands.style.getPropertyValue('--hands-away')).toBe('68.00%')
  top = -300
  window.dispatchEvent(new Event('scroll'))
  nextFrame?.(16)
  expect(hands.style.getPropertyValue('--hands-away')).toBe('0.00%')
  top = window.innerHeight
  window.dispatchEvent(new Event('scroll'))
  nextFrame?.(32)
  expect(hands.style.getPropertyValue('--hands-away')).toBe('68.00%')
})
