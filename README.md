# Space Price
<details open=""><summary><h2>Описание</h2></summary>
  <div>
    <b>Space Price</b> - агрегатор поиска в интернет-магазинах, с возможностью добавления товаров в избранное для отслеживания изменения цен. 
    В качестве пользовательского интерфейса реализован Telegram Bot.
  </div>
  <p></p>
  <div>
    В настоящий момент реализован поиск в интернет магазинах <a href="https://www.oldi.ru/">Oldi.ru</a> и <a href="https://citilink.ru/">Citilink.ru</a>
  </div>
  <p></p>
  <img alt="GitHub all releases" src="https://img.shields.io/github/downloads/Geek-Team-Development/market-analyzer/total?color=brightgreen">
  <img alt="coverage" src="https://img.shields.io/badge/coverage-60%25-yellow">
  <img alt="build" src="https://img.shields.io/badge/build-passing-brightgreen">
  <img alt="GitHub commit activity" src="https://img.shields.io/github/commit-activity/w/Geek-Team-Development/market-analyzer">
</details>
<details><summary><h2>Сборка и запуск проекта</h2></summary>
<ul>
  <li><a href="#mvn-build">Сборка проекта</a></li>
  <li><a href="#docker-compose">Создание docker-compose файла</a></li>
  <li><a href="#run-app">Запуск проекта</a></li>
</ul>

<a name="mvn-build"><h3>Сборка проекта:</h3></a>
```
mvn clean install
```
<a name="docker-compose"><h3>Создание docker-compose файла:</h3></a>
```
version: '3'

services:

  config-server-service:
    image: registry.heroku.com/space-price-config-server-service/web:latest
    container_name: config-server-service
    environment:
      CONFIG_SERVER_PORT: 8070
    ports:
      - 8070:8070
    volumes:
      - ./config-server-service/config:/apps/config

  eureka-discovery-service:
    image: registry.heroku.com/space-price-eureka-discovery-service/web:latest
    container_name: eureka-discovery-service
    environment:
      EUREKA_SERVER_HOST: eureka-discovery-service
      EUREKA_SERVER_PORT: 8060
      CONFIG_SERVER_HOST: config-server-service
      CONFIG_SERVER_PORT: 8070
    ports:
      - 8060:8060
    depends_on:
      - config-server-service
    command: bash -c "/apps/wait-for.sh config-server-service 8070 && /apps/entrypoint.sh"

  gateway-service:
    image: registry.heroku.com/space-price-gateway-service/web:latest
    container_name: gateway-service
    environment:
      GATEWAY_SERVER_PORT: 8050
      EUREKA_SERVER_HOST: eureka-discovery-service
      EUREKA_SERVER_PORT: 8060
      CONFIG_SERVER_HOST: config-server-service
      CONFIG_SERVER_PORT: 8070
    ports:
      - 8050:8050
    depends_on:
      - eureka-discovery-service
    command: bash -c "/apps/wait-for.sh eureka-discovery-service 8060 && /apps/entrypoint.sh"

  search-product-service:
    image: registry.heroku.com/space-price-backend-search-product-service/web:latest
    container_name: search-product-service
    environment:
      SEARCH_PRODUCT_SERVICE_PORT: 8110
      EUREKA_SERVER_HOST: eureka-discovery-service
      EUREKA_SERVER_PORT: 8060
      CONFIG_SERVER_HOST: config-server-service
      CONFIG_SERVER_PORT: 8070
      AUTH_SERVER_HOST: keycloak
      AUTH_SERVER_PORT: 8080
      CLIENT_SECRET: <secret>
      PROFILES: dev
    ports:
      - 8110:8110
    depends_on:
      - gateway-service
    command: bash -c "/apps/wait-for.sh gateway-service 8050 && /apps/wait-for.sh rabbitmq 5672 && /apps/wait-for.sh keycloak 8080 && /apps/entrypoint.sh"

  favorite-product-service:
    image: registry.heroku.com/space-price-backend-favorite-product-service/web:latest
    container_name: favorite-product-service
    environment:
      SEARCH_PRODUCT_SERVICE_PORT: 8100
      EUREKA_SERVER_HOST: eureka-discovery-service
      EUREKA_SERVER_PORT: 8060
      CONFIG_SERVER_HOST: config-server-service
      CONFIG_SERVER_PORT: 8070
      MONGODB_HOST: mongo
      MONGODB_PORT: 27017
      MONGODB_USERNAME: root
      MONGODB_PASSWORD: <mongo_password>
      AUTH_SERVER_HOST: keycloak
      AUTH_SERVER_PORT: 8080
      CLIENT_SECRET: <secret>
      PROFILES: dev
    ports:
      - 8100:8100
    depends_on:
      - gateway-service
    command: bash -c "/apps/wait-for.sh gateway-service 8050 && /apps/wait-for.sh mongo 27017 && /apps/wait-for.sh keycloak 8080 && /apps/entrypoint.sh"

  telegram-bot-service:
    image: registry.heroku.com/space-price-telegram-bot-service/web:latest
    container_name: telegram-bot-service
    environment:
      TELEGRAM_BOT_PORT: 8090
      EUREKA_SERVER_HOST: eureka-discovery-service
      EUREKA_SERVER_PORT: 8060
      CONFIG_SERVER_HOST: config-server-service
      CONFIG_SERVER_PORT: 8070
      GATEWAY_SERVICE_HOST: gateway-service
      GATEWAY_SERVICE_PORT: 8050
      RABBITMQ_HOST: rabbitmq
      RABBITMQ_USERNAME: guest
      RABBITMQ_PASSWORD: <rabbit_password>
      REDIS_HOST: redis
      REDIS_PORT: 6379
      TELEGRAM_BOT_USERNAME: <bot_name>
      TELEGRAM_BOT_TOKEN: <bot_token>
      AUTH_SERVER_HOST: keycloak
      AUTH_SERVER_PORT: 8080
      CLIENT_SECRET: <secret>
      PROFILES: dev
    ports:
      - 8090:8090
    depends_on:
      - gateway-service
    command: bash -c "/apps/wait-for.sh search-product-service 8110 && /apps/wait-for.sh favorite-product-service 8100 && /apps/wait-for.sh redis 6379 && /apps/entrypoint.sh"

  mongo:
    image: 'mongo:latest'
    container_name: mongo
    restart: always
    environment:
      MONGO_INITDB_ROOT_USERNAME: root
      MONGO_INITDB_ROOT_PASSWORD: <mongo_password>
    ports:
      - 27017:2701
    volumes:
      - mongodb-data:/data/db

  postgres:
    image: 'postgres:latest'
    container_name: postgres
    environment:
      POSTGRES_DB: keycloak
      POSTGRES_USER: keycloak
      POSTGRES_PASSWORD: <postgres_password>
    volumes:
      - keycloak-db:/var/lib/postgresql/data

  keycloak:
    image: 'jboss/keycloak:14.0'
    container_name: keycloak
    restart: always
    environment:
      DB_VENDOR: POSTGRES
      DB_ADDR: postgres
      DB_DATABASE: keycloak
      DB_USER: keycloak
      DB_PASSWORD: <postgres_password>
      KEYCLOAK_USER: admin
      KEYCLOAK_PASSWORD: <keycloak_password>
    ports:
      - 8080:8080
    depends_on:
      - postgres

  rabbitmq:
    image: 'rabbitmq:3-management'
    container_name: rabbitmq
    ports:
      - 5672:5672
      - 15672:15672
      - 61613:61613
    command: sh -c "rabbitmq-plugins enable --offline rabbitmq_stomp && rabbitmq-server"
    volumes:
      - rabbitmq-data:/var/lib/rabbitmq

  redis:
    image: 'bitnami/redis:latest'
    container_name: redis
    environment:
      - ALLOW_EMPTY_PASSWORD=yes
    ports:
      - 6379:6379

volumes:
  keycloak-db:
  mongodb-data:
  rabbitmq-data:
```
<a name="run-app"><h3>Запуск проекта:</h3></a>
```
docker-compose up -d
```
</details>

