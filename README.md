# Hibernate-University
Created a hibernate project "University".
The application is divided into 4 accounts ("university", "administration", "faculty", "department"). Each account has its own responsibilities.
In an application using DAO to decouple all low-level data access operations from high-level business services.
The application interacts with the database: PostgreSQL.
The main CRUD methods for interacting with the database are create, read, update and delete.
The hibernation app was built with annotations like @Entity, @Id, @Table, etc.
Used 4 types of association mapping: one to one, one to many, many to one, many to many.
The following fetch strategies are used to read related objects from the database: EAGER and LAZY.
In a project applied JSR 303: Bean Validation which allows to express and validate application constraints.
Wrote JUnit tests for testing CRUD operations and for hibernate-validator.
