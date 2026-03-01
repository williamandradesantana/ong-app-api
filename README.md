# ONG App API

API RESTful desenvolvida em **Java com Spring Boot** para gerenciamento de organizações não governamentais (ONGs). O sistema permite o controle de usuários, papéis, projetos, eventos e doações, com autenticação e autorização via **OAuth2/JWT** integrado ao Keycloak.

---

## Tecnologias Utilizadas

- **Java** + **Spring Boot**
- **Spring Security** (OAuth2 Resource Server com JWT)
- **Spring Data JPA** com **Hibernate**
- **MySQL** como banco de dados relacional
- **Flyway** para migrations do banco de dados
- **MapStruct** (inferido pelo padrão de Mappers)
- **Keycloak** como provedor de identidade (Identity Provider)

---

## Arquitetura

O projeto segue uma arquitetura modular por domínio, onde cada módulo contém suas próprias camadas:

```
src/main/java/.../ong_app_api/
├── config/          # Configurações (Security, CORS, Password)
├── exceptions/      # Exceções customizadas e handler global
├── security/        # Serviço de detalhes do usuário
└── modules/
    ├── users/       # Gestão de usuários
    ├── roles/       # Gestão de papéis/perfis
    ├── user_roles/  # Associação entre usuários e papéis
    ├── projects/    # Gestão de projetos
    ├── events/      # Gestão de eventos
    └── donations/   # Gestão de doações
```

Cada módulo segue o padrão: `controller → service → repository`, com DTOs de request/response e entidades JPA separadas.

---

## Módulos e Endpoints

Todos os endpoints requerem autenticação via JWT. A base da URL é `/api`.

### Usuários — `/api/users` 🔒 `ADMIN`

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/users/` | Lista usuários (paginado) |
| GET | `/api/users/{id}` | Busca usuário por ID |
| POST | `/api/users/` | Cria novo usuário |
| PUT | `/api/users/{id}` | Atualiza usuário |
| DELETE | `/api/users/{id}` | Remove usuário |

### Papéis — `/api/roles` 🔒 `ADMIN`

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/roles/` | Lista papéis (paginado) |
| GET | `/api/roles/{id}` | Busca papel por ID |
| POST | `/api/roles/` | Cria novo papel |
| PUT | `/api/roles/{id}` | Atualiza papel |
| DELETE | `/api/roles/{id}` | Remove papel |

### Associação Usuário-Papel — `/api/user-roles` 🔒 `ADMIN`

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/user-roles/` | Lista associações (paginado) |
| POST | `/api/user-roles/` | Associa papel a usuário |
| DELETE | `/api/user-roles/{id}` | Remove associação |

### Projetos — `/api/projects` 🔒 `ADMIN, VOLUNTEER, DONOR`

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/projects/` | Lista projetos (paginado) |
| GET | `/api/projects/{id}` | Busca projeto por ID |
| POST | `/api/projects/` | Cria novo projeto |
| PUT | `/api/projects/{id}` | Atualiza projeto |
| PATCH | `/api/projects/{id}` | Desativa projeto |
| DELETE | `/api/projects/{id}` | Remove projeto |

### Eventos — `/api/events` 🔒 `ADMIN, VOLUNTEER, DONOR`

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/events/` | Lista eventos (paginado) |
| GET | `/api/events/{id}` | Busca evento por ID |
| POST | `/api/events/` | Cria novo evento |
| PUT | `/api/events/{id}` | Atualiza evento |
| PATCH | `/api/events/{id}` | Desativa evento |
| DELETE | `/api/events/{id}` | Remove evento |

### Doações — `/api/donations` 🔒 `ADMIN, VOLUNTEER, DONOR`

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/donations/` | Lista doações (paginado) |
| GET | `/api/donations/{id}` | Busca doação por ID |
| POST | `/api/donations/` | Registra nova doação |
| PUT | `/api/donations/{id}` | Atualiza doação |
| DELETE | `/api/donations/{id}` | Remove doação |

---

## Segurança

A autenticação é feita via **OAuth2 com JWT**, integrado ao **Keycloak**. Os tokens são validados pelo `issuer-uri` configurado via variável de ambiente.

Os papéis são extraídos do claim `realm_access.roles` do JWT e mapeados para as autoridades do Spring Security. Existem três perfis principais:

- **ADMIN** — acesso total, incluindo gestão de usuários, papéis e associações
- **VOLUNTEER** — acesso a projetos, eventos e doações
- **DONOR** — acesso a projetos, eventos e doações

---

## Formatos Suportados

A API aceita e retorna dados nos formatos:
- `application/json`
- `application/xml`
- `application/yaml`

---

## Paginação

Os endpoints de listagem suportam os seguintes parâmetros de query:

| Parâmetro | Padrão | Descrição |
|-----------|--------|-----------|
| `page` | `0` | Número da página |
| `size` | `12` | Itens por página |
| `direction` | `asc` | Direção da ordenação (`asc` ou `desc`) |

---

## Enums

### `DonationStatus`
- `PENDING` — Pendente
- `CONFIRMED` — Confirmada
- `REFUNDED` — Estornada

### `PaymentMethod`
- `PIX`
- `CREDIT_CARD` — Cartão de Crédito
- `DEBIT_CARD` — Cartão de Débito

### `ProjectStatus`
- Definido na entidade de projetos (ex: `ACTIVE`, `INACTIVE`, etc.)

---

## Configuração e Variáveis de Ambiente

Crie um arquivo `.env` ou configure as variáveis de ambiente antes de iniciar a aplicação:

```env
DB_USERNAME=seu_usuario_mysql
DB_PASSWORD=sua_senha_mysql
SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI=http://localhost:8080/realms/seu-realm
```

### `application.yaml` relevante

```yaml
spring:
  datasource:
    url: jdbc:mysql://mysql:3306/ong_app_api?useTimezone=true&serverTimezone=UTC
  jpa:
    hibernate:
      ddl-auto: none

cors:
  originPatterns: http://localhost:8080,http://localhost:3000
```

> O banco de dados é gerenciado exclusivamente via **Flyway migrations** — não há geração automática de schema pelo Hibernate.

---

## Banco de Dados — Migrations (Flyway)

As migrations estão localizadas em `src/main/resources/db/migration/` e são executadas em ordem:

| Versão | Descrição |
|--------|-----------|
| V1 | Criação da tabela `tb_users` |
| V2 | Criação da tabela de roles |
| V3 | Adição de constraint no nome da role |
| V4 | Criação da tabela `user_roles` |
| V5 | Criação da tabela de projetos |
| V6 | Remoção do unique em `project_name` |
| V7 | Índice único em `project_name` + período |
| V8 | Criação da tabela de eventos |
| V9 | Criação da tabela de doações |

---

## Como Executar

### Pré-requisitos
- Java 21+
- MySQL rodando (ou via Docker)
- Keycloak configurado com um realm e cliente

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/williamandradesantana/ong-app-api.git
   cd ong-app-api
   ```

2. Configure as variáveis de ambiente (conforme seção acima).

3. Compile e execute:
   ```bash
   ./mvnw spring-boot:run
   ```

A API estará disponível em `http://localhost:8080`.

---

## Tratamento de Erros

A API possui um handler global de exceções (`CustomEntityResponseHandler`) que retorna respostas padronizadas via `ExceptionResponse` para os seguintes casos:

- `ResourceNotFoundException` — Recurso não encontrado (404)
- `RequiredObjectIsNullException` — Objeto obrigatório nulo (400)
- `BusinessException` — Violação de regra de negócio (422)
