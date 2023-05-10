# CreditManagementRepository
Репозиторий содержит серверную и клиентскую части приложения.
Серверная часть реализована на Java с использованием Spring(Spring Boot, Web, Security, Data JPA). Для хранения данных использовалась PostgreSQL. Тестирование проводилось с помощью JUnit5 и Mockito.
Клиентская часть реализована на Angular.

Приложение позволяет манипулировать данными о клиентах, кредитах, кредитных предложениях и выплатах по кредиту.
Кредитный план, суммы выплат по телу кредита и процентам рассчитываются системой автоматически при создании кредита - для этого требуется выбрать клиента, кредитное предложение, сумму кредита и срок выплат.

Для доступа к системе требуется аутентификация. Доступна регистрация.
Для аутентификации пользователей используются JWT токены.