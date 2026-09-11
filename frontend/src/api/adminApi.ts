import { request, type ApiEnvelope } from './http'

export type AdminUserCount = { totalUsers: number }

export type AdminUser = {
  id: string
  name: string
  email: string
  role: string
  emailVerified: boolean
  credits: number
}

export type AdminUserPageMeta = {
  page: number
  size: number
  totalElements: number
  totalPages: number
  hasNext: boolean
}

export function getAdminUserCount(accessToken: string) {
  return request<ApiEnvelope<AdminUserCount>>('/admin/users/count', {}, accessToken)
}

export function getAdminUsers(accessToken: string, page = 0, size = 20, search = '') {
  const query = search.trim() ? `&search=${encodeURIComponent(search.trim())}` : ''
  return request<ApiEnvelope<AdminUser[], AdminUserPageMeta>>(
    `/admin/users?page=${page}&size=${size}&sort=createdAt,desc${query}`,
    {},
    accessToken,
  )
}

export function grantAdminCredits(accessToken: string, userId: string, credits: number) {
  return request<ApiEnvelope<{ userId: string; credits: number }>>(
    '/admin/credit-adjustments',
    {
      method: 'POST',
      body: JSON.stringify({ userId, credits }),
    },
    accessToken,
  )
}
