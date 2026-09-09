import { request, type ApiEnvelope } from './http'

export type PaymentStatus = 'CRIADA' | 'PENDENTE' | 'PAGA' | 'CANCELADA' | 'FALHOU' | 'ESTORNADA'

export type PaymentTransaction = {
  id: string
  status: PaymentStatus
  credits: number
  amount: string
  externalReference: string | null
  createdAt: string
}

export type PaymentResponse = PaymentTransaction & {
  checkoutUrl: string | null
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

export function listTransactions(accessToken?: string) {
  return request<ApiEnvelope<PaymentTransaction[]>>('/purchases', {}, accessToken)
}
