import { request, type ApiEnvelope } from './http'

export type User = { id: string; name: string; email: string; role: string }
export type AuthData = { accessToken: string; expiresIn: number; user: User }
export type RegisterInput = { name: string; email: string; password: string; passwordConfirmation: string }

export function register(input: RegisterInput) {
  return request<ApiEnvelope<User>>('/auth/register', { method: 'POST', body: JSON.stringify(input) })
}
export function login(email: string, password: string) {
  return request<ApiEnvelope<AuthData>>('/auth/login', { method: 'POST', body: JSON.stringify({ email, password }) })
}
export function refresh() {
  return request<ApiEnvelope<AuthData>>('/auth/refresh', { method: 'POST' })
}
export function logout() {
  return request<void>('/auth/logout', { method: 'POST' })
}

export function profile(accessToken: string) {
  return request<ApiEnvelope<User>>('/profile', {}, accessToken)
}
