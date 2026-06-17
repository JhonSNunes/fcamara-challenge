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

## API

A aplicação expõe três rotas:

- `POST /cartoes` — cadastra um cartão com saldo inicial de R$ 500,00
- `GET /cartoes/{numeroCartao}` — retorna o saldo do cartão
- `POST /transacoes` — tenta autorizar uma transação e debita o valor, se aprovada

Contratos completos (payloads, status HTTP e códigos de erro) estão no **[CHALLENGE-README.md](./CHALLENGE-README.md)**.

## Testes

Os testes de integração utilizam **H2 em memória**.

### Executar todos os testes

```bash
./gradlew clean test
```

### Testes unitários

Validam regras de negócio de forma isolada, com **JUnit 5** e **Mockito**.

### Testes de integração

Validam os contratos dos endpoints via **MockMvc**, com contexto Spring e banco H2.
