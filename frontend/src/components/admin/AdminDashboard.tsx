import { useEffect, useState } from 'react'
import {
  getAdminUserCount,
  getAdminUsers,
  grantAdminCredits,
  type AdminUser,
} from '../../api/adminApi'
import type { User } from '../../api/authApi'

type AdminDashboardProps = { user: User; accessToken: string | null; onBack: () => void }

export function AdminDashboard({ user, accessToken, onBack }: AdminDashboardProps) {
  const [totalUsers, setTotalUsers] = useState<number | null>(null)
  const [error, setError] = useState<string | null>(null)
  const [users, setUsers] = useState<AdminUser[]>([])
  const [userPage, setUserPage] = useState(0)
  const [search, setSearch] = useState('')
  const [totalPages, setTotalPages] = useState(0)
  const [loadedUserPage, setLoadedUserPage] = useState<number | null>(null)
  const [usersError, setUsersError] = useState<{ page: number; message: string } | null>(null)
  const [grantingUserId, setGrantingUserId] = useState<string | null>(null)
  const [grantAmount, setGrantAmount] = useState('')
  const [grantError, setGrantError] = useState<string | null>(null)
  const [grantSuccess, setGrantSuccess] = useState<string | null>(null)
  const usersLoading = loadedUserPage !== userPage
  const usersErrorMessage = usersError?.page === userPage ? usersError.message : null

  useEffect(() => {
    if (user.role !== 'ADMIN' || !accessToken) return
    getAdminUserCount(accessToken)
      .then((response) => setTotalUsers(response.data.totalUsers))
      .catch((requestError: unknown) => {
        setError(
          requestError instanceof Error
            ? requestError.message
            : 'Não foi possível carregar o indicador.',
        )
      })
  }, [accessToken, user.role])

  useEffect(() => {
    if (user.role !== 'ADMIN' || !accessToken) return
    getAdminUsers(accessToken, userPage, 20, search)
      .then((response) => {
        setUsers(response.data)
        setTotalPages(response.meta.totalPages)
        setLoadedUserPage(userPage)
      })
      .catch((requestError: unknown) => {
        setUsersError({
          page: userPage,
          message:
            requestError instanceof Error
              ? requestError.message
              : 'Não foi possível carregar os usuários.',
        })
        setLoadedUserPage(userPage)
      })
  }, [accessToken, search, user.role, userPage])

  function openGrantForm(userId: string) {
    setGrantingUserId(userId)
    setGrantAmount('')
    setGrantError(null)
    setGrantSuccess(null)
  }

  function closeGrantForm() {
    setGrantingUserId(null)
    setGrantAmount('')
    setGrantError(null)
  }

  async function submitGrant(userId: string) {
    if (!accessToken) return
    const credits = Number(grantAmount)
    if (!Number.isInteger(credits) || credits <= 0) {
      setGrantError('Informe uma quantidade inteira e positiva.')
      return
    }
    try {
      const response = await grantAdminCredits(accessToken, userId, credits)
      setUsers((currentUsers) =>
        currentUsers.map((adminUser) =>
          adminUser.id === userId ? { ...adminUser, credits: response.data.credits } : adminUser,
        ),
      )
      setGrantSuccess('Créditos adicionados com sucesso.')
      setGrantError(null)
      setGrantAmount('')
    } catch (requestError: unknown) {
      setGrantError(
        requestError instanceof Error
          ? requestError.message
          : 'Não foi possível adicionar créditos.',
      )
    }
  }

  if (user.role !== 'ADMIN') {
    return (
      <main className="admin-page">
        <section className="admin-state admin-state-denied" aria-labelledby="admin-denied-title">
          <p className="prototype-eyebrow">Área administrativa</p>
          <h1 id="admin-denied-title">Acesso negado.</h1>
          <p>Esta área está disponível somente para administradores.</p>
          <button className="primary-button" type="button" onClick={onBack}>
            Voltar para o treino
          </button>
        </section>
      </main>
    )
  }

  return (
    <main className="admin-page" aria-labelledby="admin-title">
      <header className="admin-header">
        <div>
          <p className="prototype-eyebrow">Área administrativa</p>
          <h1 id="admin-title">Olá, {user.name}.</h1>
          <p>Os indicadores e ferramentas administrativas aparecerão aqui.</p>
        </div>
        <button className="text-button" type="button" onClick={onBack}>
          Voltar para o treino <span aria-hidden="true">→</span>
        </button>
      </header>
      <section className="admin-grid" aria-label="Indicadores administrativos">
        <article className="admin-card">
          <span>Usuários</span>
          <strong>{totalUsers ?? '—'}</strong>
          <p>{error ?? 'Quantidade total cadastrada.'}</p>
        </article>
        <article className="admin-card">
          <span>Créditos</span>
          <strong>—</strong>
          <p>Indicador disponível na próxima etapa.</p>
        </article>
      </section>
      <section className="admin-users" aria-labelledby="admin-users-title">
        <div className="admin-section-heading">
          <div>
            <p className="prototype-eyebrow">Consulta</p>
            <h2 id="admin-users-title">Usuários cadastrados</h2>
          </div>
          <span>{usersLoading ? 'Carregando...' : `${users.length} nesta página`}</span>
        </div>
        <label className="admin-search">
          Buscar usuário
          <input
            value={search}
            onChange={(event) => {
              setSearch(event.target.value)
              setUserPage(0)
            }}
            placeholder="Nome ou e-mail"
            type="search"
          />
        </label>
        {usersErrorMessage && (
          <p className="admin-feedback admin-feedback-error">{usersErrorMessage}</p>
        )}
        {!usersLoading && !usersErrorMessage && users.length === 0 && (
          <p className="admin-feedback">Nenhum usuário cadastrado.</p>
        )}
        {!usersErrorMessage && users.length > 0 && (
          <div className="admin-user-list">
            {users.map((adminUser) => (
              <article className="admin-user-row" key={adminUser.id}>
                <div>
                  <strong>{adminUser.name}</strong>
                  <span>{adminUser.email}</span>
                </div>
                <span>{adminUser.role}</span>
                <span>{adminUser.emailVerified ? 'E-mail verificado' : 'E-mail pendente'}</span>
                <span>{adminUser.credits} créditos disponíveis</span>
                <div className="admin-credit-action">
                  {grantingUserId === adminUser.id ? (
                    <>
                      <label>
                        Créditos
                        <input
                          aria-label={`Quantidade de créditos para ${adminUser.name}`}
                          min="1"
                          onChange={(event) => setGrantAmount(event.target.value)}
                          type="number"
                          value={grantAmount}
                        />
                      </label>
                      <button
                        className="primary-button"
                        type="button"
                        onClick={() => submitGrant(adminUser.id)}
                      >
                        Adicionar
                      </button>
                      <button className="text-button" type="button" onClick={closeGrantForm}>
                        Cancelar
                      </button>
                    </>
                  ) : (
                    <button
                      className="text-button"
                      type="button"
                      onClick={() => openGrantForm(adminUser.id)}
                    >
                      Adicionar créditos
                    </button>
                  )}
                </div>
              </article>
            ))}
          </div>
        )}
        {grantError && <p className="admin-feedback admin-feedback-error">{grantError}</p>}
        {grantSuccess && <p className="admin-feedback">{grantSuccess}</p>}
        {totalPages > 1 && (
          <nav className="admin-pagination" aria-label="Paginação de usuários">
            <button
              className="text-button"
              type="button"
              disabled={userPage === 0 || usersLoading}
              onClick={() => setUserPage((page) => page - 1)}
            >
              Anterior
            </button>
            <span>
              Página {userPage + 1} de {totalPages}
            </span>
            <button
              className="text-button"
              type="button"
              disabled={userPage >= totalPages - 1 || usersLoading}
              onClick={() => setUserPage((page) => page + 1)}
            >
              Próxima
            </button>
          </nav>
        )}
      </section>
    </main>
  )
}
