# Contratos HTTP

A API usa JSON nas requisições de eventos e nas respostas das operações. Os saldos e valores são inteiros; `amount` deve ser positivo e caber em um inteiro de 64 bits com sinal. Os IDs exigidos são strings que não podem estar vazias ou conter apenas espaços em branco. IDs são usados como fornecidos, sem remoção de espaços.

## Reiniciar as contas

`POST /reset` remove todas as contas e retorna status 200:

```json
{"message":"OK"}
```

## Consultar saldo

`GET /balance?account_id=100` retorna status 200 e o saldo como número JSON, por exemplo `10`. Uma conta inexistente retorna 404 com `{"details":"Account not found"}`. A ausência do parâmetro `account_id` retorna 400.

## Registrar evento

Envie `POST /event` com `Content-Type: application/json`. Em caso de sucesso, a resposta tem status 201:

| Operação | Requisição | Exemplo de resposta |
|---|---|---|
| Depósito | `{"type":"deposit","destination":"100","amount":10}` | `{"destination":{"id":"100","balance":10}}` |
| Saque | `{"type":"withdraw","origin":"100","amount":5}` | `{"origin":{"id":"100","balance":5}}` |
| Transferência | `{"type":"transfer","origin":"100","destination":"300","amount":5}` | `{"origin":{"id":"100","balance":5},"destination":{"id":"300","balance":5}}` |

Os exemplos são independentes: saque e transferência partem de saldo 10 na conta 100, e a transferência considera que a conta 300 ainda não existe. Depósitos criam o destino quando necessário; transferências válidas também criam o destino. Saques e transferências exigem uma origem existente e saldo suficiente.

| Condição | Status | Corpo |
|---|---|---|
| Tipo desconhecido, campo obrigatório ausente ou ID obrigatório em branco, ou `amount` ausente ou não positivo | 400 | `{"error":"Invalid event"}` |
| Saldo insuficiente em saque ou transferência | 400 | `{"details":"Insufficient funds"}` |
| Origem inexistente em saque ou transferência | 404 | `{"details":"Account not found"}` |
| Transferência para a mesma conta, ou depósito ou transferência que excederia o saldo máximo de 64 bits | 404 | `0` |

JSON malformado, corpo ausente e valores que não podem ser convertidos para os tipos esperados são tratados pelo Spring com status 400; o formato do corpo desses erros não faz parte do contrato acima.
