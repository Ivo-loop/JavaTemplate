# Decisões

- As funcionalidades HTTP estão organizadas nas fatias `event`, `balance` e `reset`, com serviços para as regras de negócio. `accounts` contém o repositório em memória compartilhado.
- O armazenamento inicia vazio. Depósitos criam contas de destino ausentes; transferências válidas também podem criar o destino. Reset remove todas as contas. Os dados são perdidos ao reiniciar.
- O serviço mantém o bloqueio do repositório durante operações compostas. A transferência é atômica e leituras e reset não observam alterações parciais. O estado não é compartilhado entre instâncias.
- Os valores são inteiros positivos. Transferências exigem contas distintas, origem existente, saldo suficiente e resultado representável em inteiro de 64 bits. Valores fora dos limites são recusados.
- O projeto usa o Gradle Wrapper existente. Testes HTTP são separados por cenário e seguem Arrange, Act, Assert.

A organização por funcionalidades é detalhada em [Vertical Slice Architecture](vertical-slice.md).
