# Задание по модулю Spring Data

### Spring Data Module

После клонирования репозитория необходимо:

1. Пролить скрипты для создания таблиц ([schema](src/test/resources/init-db.sql)) и добавления данных в
   них ([data](src/test/resources/books.sql)).
2. Добавить конфигурации для БД в [application.properties](src/main/resources/application.yaml).
3. `mvn clean package` (если есть Docker, можно запустить тесты с помощью TestContainers, убрав аннотацию `@Disabled` в
   тестах в пакете `container`).

***

### Быстрые ссылки:

- [Поиск книг](http://localhost:8080/search)
