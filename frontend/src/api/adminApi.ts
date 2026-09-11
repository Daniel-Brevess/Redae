import { request, type ApiEnvelope } from './http'

export type AdminUserCount = { totalUsers: number }

export type AdminUser = {
  id: string
  name: string
  email: string
  role: string
  emailVerified: boolean
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

export function getAdminUsers(accessToken: string, page = 0, size = 20) {
  return request<ApiEnvelope<AdminUser[], AdminUserPageMeta>>(
    `/admin/users?page=${page}&size=${size}&sort=createdAt,desc`,
    {},
    accessToken,
  )
}
