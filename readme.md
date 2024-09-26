
# Encrypt API

Este projeto é uma implementação de uma API simples com criptografia transparente, focando na proteção de dados sensíveis em entidades de banco de dados. Ele foi desenvolvido utilizando o framework **Spring Boot**, com o objetivo de criptografar e descriptografar campos sensíveis, como o número de documento de usuário e o token de cartão de crédito, de forma automatizada.

## Objetivo

O desafio principal é garantir que os campos sensíveis dos objetos da entidade não sejam visíveis diretamente, criptografando-os em tempo de execução durante a conversão da entidade para a coluna correspondente no banco de dados, e vice-versa.

## Tecnologias Utilizadas

- **Java 11**
- **Spring Boot 2.x**
- **JPA/Hibernate**: Para a persistência de dados no banco.
- **H2 Database**: Banco de dados em memória para facilitar o desenvolvimento e testes.
- **Lombok**: Para reduzir o boilerplate de código (getters, setters, construtores).
- **Spring Security**: Para suporte de criptografia.
- **Junit 5**: Para testes unitários.

## Estrutura do Projeto

A estrutura do projeto segue o padrão de uma aplicação Spring Boot organizada em pacotes principais:

- **config/**: Contém classes de configuração do Spring Boot.
- **controllers/**: Define os endpoints da API REST e lida com as requisições HTTP.
- **domain/**: Entidades de banco de dados.
- **dto/**: Objetos de Transferência de Dados (Data Transfer Objects).
- **repositories/**: Repositórios JPA que lidam com a persistência de dados.
- **services/**: Contém a lógica de negócios, incluindo a criptografia e descriptografia de campos sensíveis.

## Instalação e Execução

### Pré-requisitos

- Java 11 ou superior
- Maven

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/joannegs/backendbr-cryptography
   ```

2. Acesse o diretório do projeto:
   ```bash
   cd encrypt-api
   ```

3. Execute o projeto usando Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

4. Acesse a API na URL:
   ```
   http://localhost:8080
   ```

## Testes

Os testes unitários estão configurados utilizando **JUnit 5**. Para rodar os testes, execute:

```bash
./mvnw test
```

Os testes garantem que os campos sensíveis são criptografados e descriptografados corretamente ao interagir com a API e a camada de banco de dados.

## Criptografia

A implementação de criptografia é feita utilizando Spring Security e sua classe `Encryptor` personalizada que lida com o processo de criptografia de forma transparente para as entidades.

