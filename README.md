# Customer Service API

Uma API simples de microserviço de customers provendo todo o CRUD básico, 
ligação dos logs do sistema com Elasticsearch e LogStash, com visualização de dados no Kibana.
API dockerizada.

## Tech Stack

A buid deste projeto conta com:

- Java 11 -> Language
- Spring Boot -> Main Framework
- MySql -> Main Database
- H2(In Memory DB) -> Test Database
- Logback -> Logging Framework
- ElasticSearch -> NoSQL Database used to store Logs asynchronously

## Requirements

Para executar este projeto, você precisará baixar o seguinte:

- [Java](https://www.java.com/en/download/)
- [Maven](https://maven.org/install)
- [Docker](https://docs.docker.com/engine/installation/)

E se você quiser executá-lo sozinho, você precisará baixar [MySQL] (https://www.mysql.com/downloads/) e configurar o arquivo de configuração ʻapplication.properties` para apontar para a configuração de banco de dados correta ( porta, host, dbName, dbUser e dbPass)

## Running the Project With Docker

Depois de ter todos os requisitos, a primeira etapa é gerar a imagem do docker. Para gerar a imagem docker, vá até a raiz do projeto e execute

- docker image build -t /unbox/customer-service

Em seguida, você precisará executar o arquivo   `Dockerfile` e `docker.compose.yml` que está no diretório através do seguinte comando:

- `Dockerfile`
- `$ docker-compose up`

Depois disso, você poderá começar.

- `http://localhost:8080`

### JUnits

Os testes serão rodados no `Dockerfile` e poderão ser rodados com: 

- `mvn run test`

### Documentação da API (Swagger)

- Acesse (` http://localhost:8080/ `)

### Endpoints

CRUD básico sobre customer

## Acesso à API

Depois de ter o projeto em execução, você pode acessar a api através do [curl] (https://curl.haxx.se/download.html) ou qualquer outro manipulador http de sua preferência. 
Ele é executado por padrão no `http://localhost:8080/`.
