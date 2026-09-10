import { request, type ApiEnvelope } from './http'

export type AdminUserCount = { totalUsers: number }

export function getAdminUserCount(accessToken: string) {
  return request<ApiEnvelope<AdminUserCount>>('/admin/users/count', {}, accessToken)
}
