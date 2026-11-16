# Como executar o projeto

## Pré-requisitos

- JDK 17 ou superior, compatível com o Gradle 9.8.2. Confira com `java -version`.
- Conexão com a internet na primeira execução, para o Gradle Wrapper baixar o Gradle e as dependências. Não é necessário instalar o Gradle separadamente.
- Porta 8080 disponível, caso utilize a configuração padrão.

Abra um terminal na pasta raiz do projeto, onde estão `build.gradle` e os arquivos `gradlew`. Se o Java não for encontrado, instale um JDK e configure `JAVA_HOME` e o `PATH` para apontar para ele.

## Iniciar a aplicação

No Windows (PowerShell):

```powershell
.\gradlew.bat bootRun
```

No Linux ou macOS:

```bash
./gradlew bootRun
```

Aguarde a mensagem de inicialização do Spring Boot. A API estará disponível em `http://localhost:8080`. Mantenha o terminal aberto enquanto a aplicação estiver em uso; pressione `Ctrl+C` para encerrá-la. Se o script Unix não tiver permissão de execução, execute `chmod +x gradlew` e tente novamente.

Para usar outra porta, informe a propriedade do Spring Boot ao iniciar:

```powershell
.\gradlew.bat bootRun --args='--server.port=8081'
```

No Linux ou macOS, substitua `.\gradlew.bat` por `./gradlew`. Ajuste a porta nas requisições abaixo conforme necessário.

## Iniciar sem o Gradle Wrapper

1. Instale o Gradle 9.8.2, a mesma versão configurada no Wrapper, seguindo as [instruções oficiais de instalação](https://docs.gradle.org/current/userguide/installation.html). Configure o `PATH` para incluir a pasta `bin` da instalação.
2. Abra um novo terminal e execute `java -version` e `gradle --version` para verificar o JDK e o Gradle. O projeto requer um JDK 17 ou superior compatível com essa versão do Gradle.
3. Acesse a pasta raiz do projeto, que contém `build.gradle`, e execute:

   ```bash
   gradle bootRun
   ```

O comando é o mesmo no Windows, Linux e macOS. Na primeira execução, o Gradle instalado também precisa de acesso à internet para baixar as dependências. A API fica disponível em `http://localhost:8080`; use `Ctrl+C` para encerrá-la. Para outra porta, execute `gradle bootRun --args='--server.port=8081'`.

Para executar os testes com o Gradle instalado, use `gradle test`. Para gerar o pacote executável, use `gradle bootJar` e siga as instruções da seção **Gerar e executar o pacote**.

## Verificar a API

Em outro terminal, crie uma conta por meio de um depósito e consulte seu saldo. No PowerShell:

```powershell
Invoke-RestMethod -Method Post -Uri 'http://localhost:8080/event' -ContentType 'application/json' -Body '{"type":"deposit","destination":"100","amount":10}'
Invoke-RestMethod -Uri 'http://localhost:8080/balance?account_id=100'
```

No Linux ou macOS:

```bash
curl -X POST http://localhost:8080/event -H 'Content-Type: application/json' -d '{"type":"deposit","destination":"100","amount":10}'
curl 'http://localhost:8080/balance?account_id=100'
```

O depósito retorna HTTP 201 e a conta com saldo 10; a consulta retorna `10`. As contas são armazenadas em memória e são perdidas ao reiniciar a aplicação. Consulte os [contratos HTTP](docs/schemas.md) para conhecer as demais operações.

## Executar os testes

Na raiz do projeto, execute `.\gradlew.bat test` no Windows ou `./gradlew test` no Linux ou macOS. Abra `build/reports/tests/test/index.html` para consultar o relatório.

## Gerar e executar o pacote

Execute `.\gradlew.bat bootJar` no Windows ou `./gradlew bootJar` no Linux ou macOS. O arquivo executável será gerado em `build/libs/`. Execute-o com `java -jar build/libs/TemplateJava-0.0.1-SNAPSHOT.jar` (o nome depende da configuração de versão do projeto).

## Problemas comuns

- **Porta em uso:** encerre o processo que utiliza a porta ou inicie a aplicação com outra porta, como mostrado acima.
- **Falha no download do Gradle ou das dependências:** verifique a conexão com a internet e as configurações de proxy da sua rede.
- **Versão de Java incompatível:** confira `java -version` e `JAVA_HOME`; o terminal e o Gradle devem usar um JDK compatível.

## Referências

- [README](README.md)
- [Documentação do Gradle](https://docs.gradle.org/)
- [Plugin Gradle do Spring Boot 4.1.2](https://docs.spring.io/spring-boot/4.1.2/gradle-plugin/)