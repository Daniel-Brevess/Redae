export function EmptyState({ message = 'Nenhum item encontrado.' }: { message?: string }) {
  return <p className="empty-state">{message}</p>
}
