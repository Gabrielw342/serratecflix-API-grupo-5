



# 🎬 SerratecFlix — API RESTful

## 👥 Integrantes — Grupo 5

| Nome | GitHub | Parte no projeto|
|------|--------|-----------------|
| Gabriel | [Gabrielw342](https://github.com/Gabrielw342) | Classes e interfaces Filmes, Rank de mais avaliados|
| Enzo | [`EnzoBCCosta`](https://github.com/EnzoBCCosta) | Classes e interfaces Series e Envio de email|
| João Clemente | [`JClemente-web`](https://github.com/JClemente-web) |Classes e interfaces Avaliação filme e serie e Paginação|
| Matheus | [`feat/categoria-matheus`](https://github.com/zMatheus77) |Classes e interfaces Categoria e Verificação de email|
| Breno França Magrani | [`brenofranca2000`](https://github.com/brenofranca2000) |Classes e interfaces lista favoritos e Clonar Lista de favoritos|
| Bruno Freitas | [`Brun0Fr3itas`](https://github.com/Brun0Fr3itas) | Classes e interfaces pessoa e foto de perfil|

---

## 📋 Descrição do Projeto

O **SerratecFlix** é uma API RESTful desenvolvida como projeto final do curso de Desenvolvimento Backend. A plataforma simula um serviço de streaming temático (filmes, séries e animes), permitindo que usuários:

- 🎥 Avaliem filmes e séries com nota (0–10) e comentário
- 📋 Criem listas de favoritos personalizadas (públicas ou privadas)
- 🏷️ Naveguem por categorias (Ação, Terror, Ficção Científica...)
- 🌐 Consultem informações externas via integração com a **API OMDb**
- 🔐 Façam autenticação segura com **JWT**

---

## 🚀 Tecnologias Utilizadas

| Tecnologia | Descrição |
|------------|-----------|
| Java 17+ | Linguagem principal |
| Spring Boot | Framework principal |
| Spring Security + JWT | Autenticação e autorização |
| Spring Data JPA + Hibernate | Persistência de dados |
| PostgreSQL | Banco de dados relacional |
| Bean Validation | Validação de dados de entrada |
| Swagger / OpenAPI | Documentação da API |
| Maven | Gerenciador de dependências |
| OMDb API | API externa para dados de filmes |
| JavaMailSender | Envio de e-mails |

---

## 🏗️ Arquitetura do Projeto

O projeto segue o padrão de **arquitetura em camadas**:

```
src/main/java/com/streamingflix/serraflixgrupo5/
├── config/           → Configurações (Swagger, Jackson, Mail, DataInitializer)
├── controller/       → Endpoints REST
├── dto/
│   ├── request/      → DTOs de entrada
│   └── response/     → DTOs de saída
├── entity/           → Entidades JPA
├── enum/             → Enumerações (ClassificacaoIndicativa)
├── exception/        → Exceções customizadas e GlobalExceptionHandler
├── integration/      → Clientes de APIs externas (OmdbClient)
├── repository/       → Interfaces JPA
├── security/         → Configuração de segurança e JWT
└── service/          → Regras de negócio
```

---

## 🗄️ Entidades e Relacionamentos

```
Usuario ──1:N──▶ AvaliacaoFilme ◀──N:1── Filme
Usuario ──1:N──▶ AvaliacaoSerie ◀──N:1── Serie
Filme   ──N:N──▶ Categoria
Serie   ──N:N──▶ Categoria
Usuario ──1:N──▶ ListaFavoritos
ListaFavoritos ──N:N──▶ Filme
ListaFavoritos ──N:N──▶ Serie
```

### Entidades principais

- **Usuario** — id, nome, email, username (único), senha, fotoPerfil, dataCriacao
- **Filme** — id, titulo, descricao, duracao (min), dataLancamento, classificacaoIndicativa, notaMedia
- **Serie** — id, titulo, descricao, temporadas, episodios, dataLancamento, notaMedia
- **Categoria** — id, nome (ex: Ação, Terror), descricao
- **AvaliacaoFilme / AvaliacaoSerie** — id, nota (0–10), comentario, dataAvaliacao
- **ListaFavoritos** — id, nomeLista, privada (boolean), dataCriacao

---

## ⚙️ Como Executar

### Pré-requisitos

- Java 17+
- Maven
- PostgreSQL rodando localmente
- (Opcional) Chave de API da [OMDb](https://www.omdbapi.com/apikey.aspx)

### 1. Clone o repositório

```bash
git clone https://github.com/Gabrielw342/serratecflix-API-grupo-5.git
cd serratecflix-API-grupo-5
```

### 2. Configure o banco de dados

Crie um banco PostgreSQL e ajuste o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/serratecflix
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

omdb.api.key=sua_chave_omdb

# (Opcional) Configurações de e-mail
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu_email@gmail.com
spring.mail.password=sua_senha_app
```

### 3. Execute a aplicação

```bash
mvn spring-boot:run
```

A aplicação irá iniciar na porta `8080` e o banco já será populado automaticamente com dados iniciais via `DataInitializer`.

### 4. Acesse o Swagger

```
http://localhost:8080/swagger-ui.html
```

---

## 🔐 Autenticação

A API utiliza **JWT (JSON Web Token)**. Para acessar rotas protegidas:

### 1. Registre um usuário (rota pública)
```
POST /usuarios
```

### 2. Faça login
```
POST /auth/login
```
Retorna um `token` JWT.

### 3. Use o token nas demais requisições
```
Authorization: Bearer <seu_token>
```

No Swagger, clique em **Authorize** e insira `Bearer <seu_token>`.

---

## 🌐 Endpoints Principais

### Autenticação
| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| POST | `/auth/login` | Login e geração de token JWT | ❌ Público |

### Usuários
| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| GET | `/usuarios` | Listar todos os usuários | ✅ |
| GET | `/usuarios/{id}` | Buscar usuário por ID | ✅ |
| POST | `/usuarios` | Criar novo usuário | ❌ Público |
| PUT | `/usuarios/{id}` | Atualizar usuário | ✅ |
| DELETE | `/usuarios/{id}` | Remover usuário | ✅ |

### Filmes
| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| GET | `/filmes` | Listar todos os filmes | ✅ |
| GET | `/filmes/{id}` | Buscar filme por ID | ✅ |
| POST | `/filmes` | Cadastrar filme | ✅ |
| PUT | `/filmes/{id}` | Atualizar filme | ✅ |
| DELETE | `/filmes/{id}` | Remover filme | ✅ |

### Séries
| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| GET | `/series` | Listar todas as séries | ✅ |
| GET | `/series/{id}` | Buscar série por ID | ✅ |
| POST | `/series` | Cadastrar série | ✅ |
| PUT | `/series/{id}` | Atualizar série | ✅ |
| DELETE | `/series/{id}` | Remover série | ✅ |

### Categorias
| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| GET | `/categorias` | Listar categorias | ✅ |
| GET | `/categorias/{id}` | Buscar categoria por ID | ✅ |
| POST | `/categorias` | Criar categoria | ✅ |
| PUT | `/categorias/{id}` | Atualizar categoria | ✅ |
| DELETE | `/categorias/{id}` | Remover categoria | ✅ |

### Avaliações
| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| GET | `/avaliacoes-filmes` | Listar avaliações de filmes | ✅ |
| POST | `/avaliacoes-filmes` | Avaliar um filme (nota 0–10) | ✅ |
| GET | `/avaliacoes-series` | Listar avaliações de séries | ✅ |
| POST | `/avaliacoes-series` | Avaliar uma série (nota 0–10) | ✅ |

### Listas de Favoritos
| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| GET | `/listas-favoritos` | Listar todas as listas | ✅ |
| GET | `/listas-favoritos/{id}` | Buscar lista por ID | ✅ |
| POST | `/listas-favoritos` | Criar lista de favoritos | ✅ |
| PUT | `/listas-favoritos/{id}` | Atualizar lista | ✅ |
| DELETE | `/listas-favoritos/{id}` | Remover lista | ✅ |

---

## ⚠️ Tratamento de Erros

A API retorna respostas padronizadas para todos os erros:

| Status | Descrição |
|--------|-----------|
| `400` | Dados inválidos / falha de validação |
| `401` | Não autorizado (token ausente ou inválido) |
| `404` | Recurso não encontrado |
| `409` | Conflito de dados (ex: username já cadastrado) |

Exemplo de resposta de erro:
```json
{
  "status": 404,
  "mensagem": "Filme não encontrado com id: 99",
  "timestamp": "2026-05-27T20:30:00"
}
```

---

## 🌐 Integração com API Externa — OMDb

O projeto consome a **OMDb API** para buscar informações externas sobre filmes e séries (sinopse, poster, elenco, etc.).

Endpoint de exemplo:
```
GET /omdb/buscar?titulo=Inception
```

---

## ✨ Funcionalidades Extras Implementadas

- 📸 **Upload de foto de perfil** para usuários (`/usuarios/{id}/foto`)
- 📧 **Envio de e-mail** via JavaMailSender
- 🔒 **Listas privadas** — controle de visibilidade por usuário

---

## 📊 Status do Projeto

- [x] Entidades e relacionamentos JPA
- [x] CRUDs completos para todas as entidades
- [x] DTOs de Request e Response
- [x] Autenticação JWT
- [x] Validações com Bean Validation
- [x] Tratamento global de exceções
- [x] Swagger / OpenAPI documentado
- [x] Banco pré-populado (DataInitializer)
- [x] Integração com OMDb API
- [x] Upload de imagem
- [x] Envio de e-mail

> Projeto desenvolvido para o curso de Desenvolvimento Backend — Serratec 2026 🚀
