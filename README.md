# Spring MVC Showcase Project

This is a showcase project that features a CRUD API on a MySQL database with a graphical user interface on the web. The project allows users to interact with books, authors with author-details, and reviews, with different permissions based on roles.

## Features

- Create, update and delete books and authors (admin)
- Bind authors to books and manage author details (admin)
- Update and delete reviews (admin)
- Create book reviews
- Input data is thoroughly validated for:
    -  Length validation: Ensuring that input data have required length constraints.
    - Mandatory fields: Ensuring that critical fields are not left empty.

## Prerequisites

- JDK 17
- Maven
- MySQL server

## Getting Started

1. Clone the repository.
2. Configure the MySQL database settings in the application properties file.
- You can set spring.jpa.hibernate.ddl-auto=create to have the schema created automatically. This setting results in data deletion upon each creation, but you can switch to 'update' or 'create-only' if you intend to persist data across multiple application executions.
3. Make sure MySQL database is running.
4. Run the application.
5. Access the application through the provided URL.

## Usage

The application provides the following functionality through the URLs:

- Access the menu if you are hosting it: [Link to the menu](http://localhost:8080/library/menu)
- Navigate through the site using the provided buttons and links.

## Testing

The project has been tested extensively with 80% test coverage. You can find report under /htmlReport.

## Technologies Used

- Java
- **Spring**
    - aop
    - validation
    - security
    - data-jpa
    - thymeleaf
    - web
- Maven
- Thymeleaf
- HTML
- Bootstrap
- MySQL
- Git during development process.

And for testing:

- JUnit
- Mockito
- MockMvc

## Contributing

If you find any issues, have any questions, or suggestions for improvement, please feel free to create a pull request or contact me.