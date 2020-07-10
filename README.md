#Пример использования Spring Data JPA audit


Чтобы использовать Spring Data JPA audit, необходимо:

1.Добавить @EnableJpaAuditing в свой класс @Configuration.


2.Создать базовый класс с полями для аудита BaseAudit.
Столбцы, помеченные @CreatedBy и @LastModifiedBy, заполняются именем участника, 
который создал или последним изменил объект. 
Информация извлекается из SecurityContext Authentication экземпляра. 
Для примера без Spring Security создан класс AuditorAwareImpl реализующий AuditorAware<String>, 
и его метод getCurrentAuditor() в котором в этом примере передаётся просто строка имитирующая пользователя.
Также там есть реализация при использовании Spring Security.


3.Необходимо добавить в контекст бин созданного класса AuditorAwareImpl:
	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}
4.Необходимо добавить в аннотацию EnableJpaAuditing имя бина @EnableJpaAuditing(auditorAwareRef = "auditorAware")	

5.Унаследовать сущность User от BaseAudit.


6.Пометить его аннотацией @EntityListeners(AuditingEntityListener.class)


При старте приложения Hibernate автоматически создаст необходимую таблицу users,
так как в application.properties мы используем настройку spring.jpa.hibernate.ddl-auto = update
и встроенную базу H2.


---------------------------------------------------------------------------------------------------------


Теперь при вызове запроса на создание нового пользователя, он создастся и запишется в базу,
с автоматически подставленными полями createdBy, createdDate, lastModifiedBy, lastModifiedDate.

Request:

POST:  http://localhost:8080/api/v1/users

С body :

{
    "firstName": "James",
    "lastName": "Evans",
    "emailId": "spring@yandex.ru"
}

Response:

{
    "createdBy": "Ekhart86",
    "createdDate": "2020-07-09T21:34:12.293+00:00",
    "lastModifiedBy": "Ekhart86",
    "lastModifiedDate": "2020-07-09T21:34:12.293+00:00",
    "id": 1,
    "firstName": "James",
    "lastName": "Evans",
    "emailId": "spring@yandex.ru"
}

Далее если вызвать запрос на получение всех пользователей, то получим созданного пользователя с полями аудита.

GET: http://localhost:8080/api/v1/users

[
    {
        "createdBy": "Ekhart86",
        "createdDate": "2020-07-09T21:34:12.293+00:00",
        "lastModifiedBy": "Ekhart86",
        "lastModifiedDate": "2020-07-09T21:34:12.293+00:00",
        "id": 1,
        "firstName": "James",
        "lastName": "Evans",
        "emailId": "spring@yandex.ru"
    }
]














