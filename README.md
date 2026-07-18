# Todolist

API REST simples de tarefas (to-do list) com utilizadores, feita em Spring Boot.

## Stack

- Java 21
- Spring Boot 4.1.0 (Web, Data JPA, Validation)
- H2 (base de dados em memória)
- Spring Security Crypto (hash de passwords com BCrypt)
- Lombok
- Gradle

## Como correr

```bash
./gradlew bootRun
```

A aplicação sobe em `http://localhost:8080`.

Em desenvolvimento, o [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools) reinicia a app automaticamente quando o código é recompilado.

## Base de dados

H2 em memória (os dados são perdidos ao reiniciar a app). Consola web disponível em:

```
http://localhost:8080/h2-console
```

- **JDBC URL**: `jdbc:h2:mem:todolist`
- **User Name**: `sa`
- **Password**: (vazio)

## Endpoints

### Utilizadores

| Método | Rota     | Auth | Descrição              |
|--------|----------|------|-------------------------|
| POST   | `/users` | Não  | Cria um novo utilizador |

Body:
```json
{
  "name": "John Doe",
  "username": "jd123",
  "password": "super"
}
```

### Tarefas

Todas as rotas `/tasks` exigem **Basic Auth** (username e password de um utilizador já criado). Cada utilizador só vê e altera as suas próprias tarefas.

| Método | Rota          | Descrição                    |
|--------|---------------|-------------------------------|
| POST   | `/tasks`      | Cria uma tarefa               |
| GET    | `/tasks`      | Lista as tarefas do utilizador|
| PUT    | `/tasks/{id}` | Substitui uma tarefa          |
| PATCH  | `/tasks/{id}` | Atualiza parcialmente         |
| DELETE | `/tasks/{id}` | Apaga uma tarefa              |

Body (POST/PUT):
```json
{
  "title": "Primeira task",
  "description": "Deve melhorar essa api",
  "priority": "Alta",
  "startAt": "2027-04-12T12:30:00",
  "endAt": "2028-05-12T12:30:00"
}
```

## Testar a API

Exemplos de pedidos prontos em [test.http](test.http) (extensão [REST Client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client) do VS Code).
