# Vertical Slice Architecture

Vertical Slice Architecture organiza o código em torno de funcionalidades ou resultados de negócio. Uma fatia reúne os componentes necessários para atender àquela funcionalidade, atravessando responsabilidades como entrada HTTP, validação, regras de negócio e acesso a dados. Assim, quem altera uma funcionalidade pode encontrar suas partes relacionadas próximas umas das outras. A divisão não impede que existam controllers, serviços ou repositórios; ela define como agrupá-los.

Neste projeto, a organização é uma adaptação simples dessa ideia:

- `event` recebe eventos em `EventController` e aplica as regras de depósito, saque e transferência em `EventService`.
- `balance` reúne o endpoint e a consulta de saldo.
- `reset` reúne o endpoint e a remoção de todas as contas.
- `accounts` contém o modelo, o repositório em memória e o tratamento de erros usados pelas funcionalidades.

Por exemplo, uma alteração na regra de saque tende a ficar em `event`, enquanto uma mudança no armazenamento compartilhado pode atingir as três funcionalidades. As fatias, portanto, não são completamente independentes: elas compartilham o repositório e o modelo de conta. Além disso, `event` agrupa três operações sob uma mesma funcionalidade HTTP, em vez de criar uma fatia para cada operação. Os pacotes organizam o código, mas não impõem limites de dependência pelo compilador.

O tamanho de uma fatia é uma decisão de projeto: divisões pequenas demais podem aumentar a duplicação e o código compartilhado; divisões amplas demais podem dificultar a manutenção. O objetivo é manter juntas as partes que mudam pelo mesmo motivo, preservando limites claros e evitando transformar o código compartilhado em um agrupamento genérico de regras. Se as funcionalidades crescerem, esses limites poderão ser revistos. Mensageria ou módulos separados não são necessários apenas para seguir esse estilo.

Como referência para os conceitos, granulação, compartilhamento e comunicação entre fatias, consulte [Vertical Slice Architecture, de iamprovidence](https://medium.com/@iamprovidence/vertical-slice-architecture-e3b7b8f48ce9).
