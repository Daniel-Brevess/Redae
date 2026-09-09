import { request, type ApiEnvelope } from './http'

export type PaymentResponse = {
  id: string
  status: 'CRIADA' | 'PENDENTE' | 'PAGA' | 'CANCELADA' | 'FALHOU' | 'ESTORNADA'
  credits: number
  amount: string
  externalReference: string | null
  checkoutUrl: string | null
  createdAt: string
}

export type CreditBalanceResponse = {
  credits: number
}

export function createPurchase(creditAmount: number, accessToken?: string) {
  return request<ApiEnvelope<PaymentResponse>>(
    '/purchases',
    {
      method: 'POST',
      body: JSON.stringify({ creditAmount }),
    },
    accessToken,
  )
}

export function getCreditBalance(accessToken?: string) {
  return request<ApiEnvelope<CreditBalanceResponse>>('/credit-balance', {}, accessToken)
}
