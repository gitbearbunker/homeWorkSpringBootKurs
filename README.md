# homeWorkSpringBootKurs - Курсовой проект «Сервис перевода денег»

## Описание проекта 

Разработано приложение — REST-сервис. Сервис предоставляет интерфейс для перевода денег с одной карты на другую по заранее описанной спецификации. 

Заранее подготовленное веб-приложение (FRONT) подключается к разработанному сервису без доработок и использует его функционал для перевода денег.

## Требования к приложению

- Сервис предоставляет REST-интерфейс для интеграции с FRONT.
- Сервис реализует все методы перевода с одной банковской карты на другую, описанные [в протоколе](https://github.com/netology-code/jd-homeworks/blob/master/diploma/MoneyTransferServiceSpecification.yaml).
- Все изменения записываются в файл — лог переводов в произвольном формате с указанием:
 - даты;
 - времени;
 - карты, с которой было списание;
 - карты зачисления;
 - суммы;
 - комиссии;
 - результата операции, если был.

## Реализация

- Приложение разработано с использованием Spring Boot.
- Использован сборщик пакетов maven.
- Для запуска используется Docker, Docker Compose .
- Код размещён на GitHub.
- Код покрыт юнит-тестами с использованием mockito.
- Добавлены интеграционные тесты с использованием testcontainers.


## Описание интеграции с FRONT

FRONT доступен [по адресу](https://github.com/serp-ya/card-transfer). Можно скачать репозиторий и запустить Node.js приложение локально (в описании репозитория FRONT добавлена информация, как запустить) или использовать уже развёрнутое демо-приложение [по адресу](https://serp-ya.github.io/card-transfer/) (тогда ваш API должен быть запущен [по адресу](http://localhost:5500/)).
> Весь API FRONT был описан в соответствии [YAML](https://github.com/netology-code/jd-homeworks/blob/master/diploma/MoneyTransferServiceSpecification.yaml)
файла по спецификации OpenAPI (подробнее [по ссылке 1](https://swagger.io/specification/) и [ссылке 2](https://starkovden.github.io/introduction-openapi-and-swagger.html)).
