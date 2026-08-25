export function LoadingState({ label = 'Carregando...' }: { label?: string }) {
  return (
    <p className="loading-state" role="status" aria-live="polite">
      {label}
    </p>
  )
}
