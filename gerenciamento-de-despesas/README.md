# Gerenciamento de Despesas — Spring Boot + Redis

## Requisitos
- JDK 17 ou superior
- Docker Desktop (com WSL 2 se estiver no Windows) ou Redis instalado
- IntelliJ IDEA ou outra IDE com suporte a Maven

## Iniciar o Redis com Docker
No terminal, execute:
```bash
docker run --name redis-despesas -p 6379:6379 -d redis
```
Se o container já existir, inicie-o com:
```bash
docker start redis-despesas
```

## Executar a aplicação
Abra a pasta do projeto no IntelliJ e aguarde o Maven carregar as dependências. Execute a classe
`GerenciamentoDeDespesasApplication`.

Também é possível usar o Maven Wrapper se ele existir no projeto:
- Windows: `mvnw.cmd spring-boot:run`
- Linux/macOS: `./mvnw spring-boot:run`

Se o IntelliJ não gerar o Maven Wrapper, execute a classe principal pela IDE.

## Menu no terminal
O menu permite cadastrar, listar, buscar por ID, atualizar e excluir despesas.
A aplicação também expõe uma API REST em `http://localhost:8080/despesas`.

## Exemplos de requisições HTTP

Criar (POST `/despesas`):
```json
{
  "descricao": "Conta de internet",
  "valor": 119.90,
  "data": "2026-10-26"
}
```

Listar: `GET http://localhost:8080/despesas`

Buscar: `GET http://localhost:8080/despesas/ID_DA_DESPESA`

Atualizar (PUT `/despesas/ID_DA_DESPESA`):
```json
{
  "descricao": "Conta de internet atualizada",
  "valor": 129.90,
  "data": "2026-10-27"
}
```

Excluir: `DELETE http://localhost:8080/despesas/ID_DA_DESPESA`

## Dependências
- Spring Web: API REST e requisições HTTP
- Spring Data Redis: acesso ao Redis via repositório
- Spring Validation: validação dos campos da entidade
- Lombok: getters e setters e construtores
