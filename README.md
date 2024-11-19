# Order Store
В данном репозитории располагается исходный код клиент-серверного веб-приложения, которое реализовывает
функционал управления заказами.



## Содержание
- [Технологии](#технологии)
- [Использование](#использование)
- [Тестирование](#тестирование)

## Технологии
- [Java](https://www.java.com/ru/)
- [Spring Framework](https://spring.io/)
- [Maven](https://maven.apache.org/)
- [Spring Data JDBC](https://hibernate.org/)
- [Liquibase](https://www.liquibase.com/)
- [Postgresql](https://www.postgresql.org/)
- [JUnit](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)

## Использование
Освободите порты 8080 и 8081.

Чтобы запустить приложение, запустите скрипт start.sh:
```sh
$ ./start.sh
```

## Тестирование
Проект покрыт юнит-тестами JUnit. Для их запуска выполните команду:
```sh
mvn test
```