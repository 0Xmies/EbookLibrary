# Spring REST Showcase Project
![book-info](https://github.com/0Xmies/EbookLibrary/assets/119740235/4a7182f3-e61a-4590-a2a4-978f36125034)
This is a showcase project that features a REST API on a MySQL database with a graphical user interface on the web. The project allows users to interact with books, authors with author-details, and reviews, with different permissions based on roles.

## Features

- Create, update and delete books and authors (admin)
- Bind authors to books and manage author details (admin)
- Update and delete reviews (admin)
- Create book reviews
- Input data is thoroughly validated for:
    - Length validation: Ensuring that input data have required length constraints.
    - Mandatory fields: Ensuring that critical fields are not left empty.

## Prerequisites

- JDK 17
- Maven
- MySQL server

## Getting Started

1. Clone the repository.
2. Configure the MySQL database settings in the application properties file.
- You can set spring.jpa.hibernate.ddl-auto=create to have the schema created automatically. This setting results in data deletion upon each creation, but you can switch to 'update' or 'create-only' if you intend to persist data across multiple application executions.
3. Make sure MySQL server is running.
4. Run the application.
5. Access the application through the provided URL.

## MySQL Diagram 

![mysql_diagram](https://github.com/0Xmies/EbookLibrary/assets/119740235/a5f119e7-2573-41fc-bc5b-f9788304a222)

## Usage

The application provides the following functionality through the URLs:

- Access the menu if you are hosting it: [Link to the menu](http://localhost:8080/library/menu)
- Navigate through the site using the provided buttons and links to perform REST operations.

## Testing

The project has been extensively tested, achieving 80% test coverage. You can find the report under `/htmlReport`.

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

## Screenshots

![menu](https://github.com/0Xmies/EbookLibrary/assets/119740235/e9baee57-5352-46b4-bbd8-ba14646036a3)
![image](https://github.com/0Xmies/EbookLibrary/assets/119740235/dcb49569-a84f-46c2-a1e6-3ac0e330d393)
![author-details](https://github.com/0Xmies/EbookLibrary/assets/119740235/02a9a439-d78d-42ca-9dc4-a306027a7b89)
