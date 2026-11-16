# Banking API

API de contas bancárias em memória, desenvolvida com Java e Spring Boot. Os dados começam vazios e são perdidos quando a aplicação é reiniciada.

## Executar

É necessário um JDK 17 ou superior compatível com o Gradle 9.8.2. O Gradle Wrapper baixa a distribuição e as dependências na primeira execução.

No Windows, execute `.\gradlew.bat bootRun`; no Linux ou macOS, use `./gradlew bootRun`. A API fica disponível em `http://localhost:8080` por padrão.

## Experimentar

Com a aplicação em execução, faça um depósito e consulte o saldo:

```bash
curl -X POST http://localhost:8080/event -H "Content-Type: application/json" -d '{"type":"deposit","destination":"100","amount":10}'
curl "http://localhost:8080/balance?account_id=100"
```

O depósito retorna status 201 com a conta e saldo 10; a consulta retorna `10`. Os exemplos usam sintaxe de shell Unix; no PowerShell, use `curl.exe` e ajuste as aspas conforme necessário.

Consulte os [contratos HTTP](docs/schemas.md) para todas as operações e erros, e as [decisões do projeto](docs/decisoes.md) para as regras de armazenamento e concorrência.

## Testes

No Windows, execute `.\gradlew.bat test`; no Linux ou macOS, use `./gradlew test`. O relatório gerado fica em `build/reports/tests/test/index.html`.

## Coleção Postman

Importe [Banking-API.postman_collection.json](postman/Banking-API.postman_collection.json) e execute a coleção na ordem apresentada. A variável `baseUrl` aponta para `http://localhost:8080` e pode ser alterada no Postman. O primeiro passo remove todas as contas; os demais dependem do estado criado anteriormente. Cada requisição valida o status e o corpo da resposta.
