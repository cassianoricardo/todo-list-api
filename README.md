# TODO LIST API

Uma API RESTFUL simples em Java que armazena e atualiza tarefas

## Informações da API

- Cada usuário pode listar tarefas com filtro por status
- Tarefas criadas por um usuário não podem ser vistas por outros usuários
- Somente usuários cadastrados na base podem incluir tarefas
- Somente usuarios com a role de ADMIN poderá ver todas as tarefas de todos os usuários
- Ao listar todas as tarefas ordena primeiro com status "pending" e depois os "completed"
- Na base vai existir dois usuários pré-cadastrados um com role de ADMIN e outro com permissão de USER
- Toda ação de usuário tem que ser autenticada (com um token JWT válido) adquirido no momento do login (POST /auth/login) que expira em 5min.
- Cada tarefa é composta por:
  - ID do usuário
  - data/hora da inclusão, no formato ISO (yyyy-MM-ddTHH:mm:ss)
  - resumo da tarefa
  - descrição da tarefa
  - status (pending ou completed)
  - data/hora da alteração, no formato ISO (yyyy-MM-ddTHH:mm:ss)
- A senha dos usuário são encriptadas na base
- Para obter dados sobre a saúde dos seus componentes de acessar o endpoint /actuator/health
  - Obs: apenas usuários com a permissão de ADMIN podem acessar esse endpoint
- Para obter indicadores de performance da API (ex: volume de requisições atendidas, tempo médio de serviço em milisegundos, etc)  de acessar a rota /actuator/metrics
  - Obs: apenas usuários com a permissão de ADMIN podem acessar esse endpoint

## Pré requisitos

- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Apache Maven 3.8.5](https://dlcdn.apache.org/maven/maven-3/3.8.5/binaries/)
- [Git](https://git-scm.com/downloads)
- [Mysql](https://dev.mysql.com/downloads/mysql/) 
  - database name db_todo

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
  - src/main/java/br/com/itau/todo/list/api
- Clique com o botão direito do mouse na classe **App**, selecione "**Run As**" e clique em "**Java Application**"
- Após subir a Api será apresentado no console a URL do Swagger do TODO LIST API
  - http://localhost:8080/swagger-ui/index.html

## Como acessar a API via postman:

- Abra o postman
- Clique em **Collections**
- Clique em import
- Na Aba file adicione o arquivo presente no diretório abaixo:

Diretório: todo-list-api\src\main\resources\api-todo-list.postman_collection.json

### Tecnologias presentes na API:

- Spring boot 2.7.3;
- Swagger 3;
- Lombok;
- JUnit 5;
- MockMvc
- Spring Security
- Token JWT