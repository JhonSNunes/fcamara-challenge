# Mini Autorizador

API REST que simula um autorizador de transações de benefícios (Vale Refeição, Vale Alimentação, etc.). O sistema permite criar cartões, consultar saldo e autorizar transações com débito automático do saldo.

## Sobre o desafio

Este projeto é a solução do desafio técnico da VR Benefícios / FCamara. O objetivo é implementar um **mini-autorizador** que valide transações antes de aprová-las, seguindo regras de negócio como existência do cartão, senha correta e saldo disponível.

As regras completas do desafio, contratos dos endpoints e cenários de teste estão em **[CHALLENGE-README.md](./CHALLENGE-README.md)**.

## Stack

- Java 21
- Spring Boot 3.5
- Spring Data JPA
- MySQL 5.7
- Flyway
- Gradle
- Lombok
- SpringDoc OpenAPI (Swagger UI)

## Pré-requisitos

- Java 21
- Docker e Docker Compose

## Como rodar

1. Suba o banco de dados:

```bash
docker compose -f docker/docker-compose.yml up -d
```

2. Inicie a aplicação:

```bash
./gradlew bootRun
```

O Flyway roda automaticamente junto com a aplicação.

A API ficará em `http://localhost:8080` e o Swagger em `http://localhost:8080/swagger-ui.html`.

## Endpoints principais

| Método | URL                        | Descrição              |
|--------|----------------------------|------------------------|
| POST   | `/cartoes`                 | Criar cartão           |
| GET    | `/cartoes/{numeroCartao}`  | Consultar saldo        |
| POST   | `/transacoes`              | Autorizar transação    |

Detalhes de request/response e códigos de erro: **[CHALLENGE-README.md](./CHALLENGE-README.md)**.

## Testes

```bash
./gradlew test
```

> Os testes exigem o MySQL em execução.
