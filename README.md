# TODO LIST API

Criar uma RESTFUL API simples em Java que armazene e atualize tarefas (TODO-LIST API)

## REQUISITOS FUNCIONAIS

- Deve haver uma base de usuários pré-cadastrados (não precisa implementar a funcionalidade de cadastro) - ok
- Somente usuários cadastrados nessa base podem incluir tarefas - ok
- Cada tarefa deve possuir minimamente os dados:
 - ID do usuário
 - data/hora da inclusão
 - resumo da tarefa
 - descrição da tarefa
 - status (pending ou completed)
 - data/hora da alteração (quando houver mudança de status)
- Tarefas criadas por um usuário não podem ser vistas por outros usuários
- Cada usuário deve poder listar tarefas com filtro por status
- Se o usuário listar todas as tarefas, deve priorizar (no topo da listagem) tarefas com status "pending"
- Plus: Deve haver um usuário com perfil de "super user" e somente ele poderá ver todas as tarefas de todos os usuários

## REQUISITOS NÃO FUNCIONAIS

- Toda ação do usuário sobre a lista de tarefas deve ser autenticada (acesso somente com token válido) - ok
- Campos de data/hora devem ser exibidos na API no formato ISO (yyyy-MM-ddTHH:mm:ss) - ok
- Plus: A senha / o secret não deve ficar exposto(a) na base de cadastro - ok
- Plus: deve haver autorização para permitir somente o “super user” de ver todas as tarefas
 
## INFORMAÇÕES SOBRE A API

- Deve existir uma rota de autenticação e devolver um token a expirar em 5min (POST /auth) - avaliar soluções persistência da sessão em cache
- Deve persistir os dados uma base de sua escolha (Relacional / NoSQL) - explicar sobre a abordagem escolhida
- Fornecer as rotas e verbos HTTP adequados para cada função / requisito funcional (ex: GET, PUT, POST, DELETE /todo)
- Deve validar sessão (token) do usuário em todas as rotas (exceto na de autenticação)
- Deve manter log/trace das ações executadas (pode utilizar componentes de terceiros)
- Deve conter testes unitários (JUnit ou similar)
- Plus: Disponibilizar uma rota para fornecer dados sobre a saúde dos seus componentes (GET /healthcheck) (pode utilizar componentes de terceiros)
- Plus: Disponibilizar uma rota para indicadores de performance da API (ex: volume de requisições atendidas, tempo médio de serviço em milisegundos, etc) (GET /metrics) (pode utilizar componentes de terceiros)
- OBS: não é necessário desenvolver o Frontend para input dos dados na API

## ENTREGA

A entrega da API deve ser disponibilizada no github.com em um repositório público. Escreva um breve arquivo README.md contendo informações da API, exemplos de uso (preferencialmente usando CURL), e como podemos rodar a API (incluindo recursos acoplados: DB, cache, etc.) via linha de comando para avaliação.

## DIFERENCIAL

Serão considerados como um diferencial na avaliação:

- Documentação do código
- Testes unitários com cobertura de no mínimo 80%
- Testes de carga (JMeter ou similar)
- Testes automatizados (linha de comando ou alguma plataforma de sua escolha)
- Alta disponibilidade, escalabilidade horizontal, estratégia de deployment em containers ou Cloud publica

## Pré requisitos

- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Apache Maven 3.8.5](https://dlcdn.apache.org/maven/maven-3/3.8.5/binaries/)
- [Git](https://git-scm.com/downloads)

## Configuração Windows

### Adicionando o MAVEN_HOME e o JAVA_HOME
- Acesso o menu Windows,
- Digite "**Editar as variáveis de ambiente do sistema**"
- Em "**Propriedades do Sistema**" Acesse a Aba "**Avançado**" e clique em "**Variaveis de Ambiente...**"
- Em "**Variáveis de Ambiente**", Variaveis do Sistema, clique em "**Novo...**"
  - Nome da variável: MAVEN_HOME
  - Valor da variável: c:\apache-maven-3.8.5

- Em "**Nova variável de sistema**" Clique "**Ok**" e depois clique novamente em "**Novo...**"

  - Nome da variável: JAVA_HOME
  - Valor da variável: C:\Program Files\Java\jdk-11.0.0
- Em "**Variáveis de ambiente**" Clique "**Ok**".
- Em "**Propriedades do sistema**" Clique "**Ok**".


## Clonando o projeto

- Abra a sua pasta de repositório, clique com o botão direito do mouse e selecione a opção "**Git Bash**"
- Execute o comando abaixo:
  - git clone https://github.com/cassianoricardo/todo-list-api.git

## Pré requisitos do projeto

# Bando de dados Mysql com banco database name db_todo

## Importando o projeto

### Eclipse:

- Acesse o Menu "**File**", selecione "**Import**"
- Em "**Import**" Expanda a pasta "**Maven**" e selecione a opção "**Existing Maven Projects**" e clique em "**Next**"
- Em "**Import Maven Projects**" clique em "**browse...**" e selecione o pasta do projeto "**Password API**" e clique em "**Finish**"
- Ainda no Eclipse, clique com o botão direito do mouse sob o projeto "**Password API**" selecione "**Run As**" e depois "**Maven clean**"
  - "**Run As**" e depois "**Maven install**"

### Intellij:

- Acesse o Menu "**File**", depois clique em "**Open**"
- Em "**Open File or Project**, selecione o diretório do "**Password API**"
- Clique em "**Load Maven Project**"
- Acesse a perspectiva [**Maven**] do lado direito da tela, e depois clique em um icone de "**M**" e selecione: "**mvn clean**" aguarde o finalização no consolse e depois em "**mvn install**"

## Executando o projeto

Esse passo é comum para as IDE Eclipse e Intellij:

- Abra a classe **App.java** no diretório:
  - src/main/java/br/com/itau/password/api
- Clique com o botão direito do mouse na classe **App**, selecione "**Run As**" e clique em "**Java Application**"
- Após subir a Api será apresentado no console a URL do Swagger do Password API
  - http://localhost:8080/swagger-ui/index.html

### Tecnologias presentes na API:

- Spring boot 2.6.7;
- Swagger 3;
- Lombok;
- JUnit 5;
- MockMvc
- Spring Security
- Token JWT