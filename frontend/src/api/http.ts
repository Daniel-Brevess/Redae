const API_BASE_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8080/api/v1'

export type ApiEnvelope<T> = { data: T; meta: Record<string, unknown>; traceId: string }

export async function request<T>(path: string, options: RequestInit = {}, accessToken?: string) {
  const headers = new Headers(options.headers)
  headers.set('Content-Type', 'application/json')
  if (accessToken) headers.set('Authorization', `Bearer ${accessToken}`)
  const response = await fetch(`${API_BASE_URL}${path}`, {
    ...options,
    headers,
    credentials: 'include',
  })
  if (!response.ok) {
    const body = await response.json().catch(() => undefined)
    throw new ApiRequestError(
      body?.error?.message ?? 'Não foi possível concluir a solicitação.',
      response.status,
    )
  }
  if (response.status === 204) return undefined as T
  return (await response.json()) as T
}

export class ApiRequestError extends Error {
  constructor(
    message: string,
    readonly status: number,
  ) {
    super(message)
    this.name = 'ApiRequestError'
  }
}
