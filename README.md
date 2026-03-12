# Teste Lázaro (Spring Boot + Angular + Postgres)

Projeto full-stack com CRUD de **Usuários** e **Perfis de sistema**, usando:

- Backend: Spring Boot (REST) + Spring Data JPA
- Frontend: Angular
- Banco: PostgreSQL
- Infra local: Docker Compose

## Como rodar (Docker Compose)

Pré-requisitos:

- Docker + Docker Compose

Subir tudo (banco + backend + frontend):

```bash
docker compose up --build
```

Acessos:

- Frontend: `http://localhost:4200`
- Backend: `http://localhost:8080`
- Postgres: `localhost:5433` (usuário `postgres`, senha `123`, database `TesteLazaro`)

## Banco e dados iniciais

O arquivo `script.sql` é montado no Postgres em `docker-entrypoint-initdb.d` para criar as tabelas:

- `profile` (id, description)
- `users` (id, name)
- `user_profile` (tabela de relacionamento)

E inserir alguns registros de exemplo (usuários, perfis e vínculos).

## Endpoints (Backend)

### Usuários

- `GET /users?page=0&size=5` (lista paginada)
- `GET /users/{id}` (detalhe por UUID)
- `POST /users` (cria)
- `PUT /users/{id}` (atualiza)
- `DELETE /users/{id}` (remove)

### Perfis

- `GET /profiles/` (lista)
- `GET /profiles/{id}` (detalhe por id inteiro)
- `POST /profiles` (cria)
- `PUT /profiles/{id}` (atualiza)
- `DELETE /profiles/{id}` (remove)

## Observações

- O frontend consome o backend em `http://localhost:8080`.
- O CORS do backend permite origens `http://localhost:4200` e `http://127.0.0.1:4200`.

## Troubleshooting rápido

- Se alguma porta já estiver em uso, ajuste em `compose.yaml`:
  - Postgres: `5433:5432`
  - Backend: `8080:8080`
  - Frontend: `4200:80`
