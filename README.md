# bajaj-finserv
SQL query:
SELECT
    p.AMOUNT AS SALARY,
    CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME,
    TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) AS AGE,
    d.DEPARTMENT_NAME AS DEPARTMENT_NAME
FROM
    PAYMENTS p
JOIN
    EMPLOYEE e ON p.EMP_ID = e.EMP_ID
JOIN
    DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID
WHERE
    DAY(p.PAYMENT_TIME) <> 1
ORDER BY
    p.AMOUNT DESC
LIMIT 1;

Based on your output and the entire process you've gone through, your README.md file should be a clear, concise summary of the project. It should explain what the application does, how to set it up and run it, and briefly address the challenges you faced with the API calls.

Project Title
Bajaj Finserv Health - JAVA Qualifier

Project Description
This is a Spring Boot application developed for the Bajaj Finserv Health coding qualifier. The application automates a two-step process:

On startup, it sends a POST request to generate a webhook URL and an access token.


It uses the provided SQL question to formulate a query to find the highest salary credited on a day other than the first of the month.


Finally, it uses the generated webhook URL and access token to submit the solution (the final SQL query).


Requirements
Java 17 or higher

Maven

IntelliJ IDEA (or another IDE that supports Spring Boot)

Setup and Execution
Clone the Repository:

Bash

git clone https://github.com/Ashutosh-Anand-1018/bajaj-finserv-health.git
cd bajaj-finserv-health
Update Personal Details:

Open BajajFinservHealthApplication.java.

Replace the placeholder values for 

name, regNo, and email with your personal details as specified in the problem statement.

Run the Application:

Use the following Maven command to build and run the application:

Bash

mvn spring-boot:run
The application's logic is designed to execute immediately on startup using an ApplicationRunner and will print the results to the console.

Solution
The SQL query formulated to solve the problem is:

SQL

SELECT
    p.AMOUNT AS SALARY,
    CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME,
    TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) AS AGE,
    d.DEPARTMENT_NAME AS DEPARTMENT_NAME
FROM
    PAYMENTS p
JOIN
    EMPLOYEE e ON p.EMP_ID = e.EMP_ID
JOIN
    DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID
WHERE
    DAY(p.PAYMENT_TIME) <> 1
ORDER BY
    p.AMOUNT DESC
LIMIT 1;
Known Issues
During the development and testing of this application, a persistent 401 Unauthorized error was encountered on the final API submission. This was observed despite a new access token being successfully retrieved on each run, and the Authorization header being correctly formatted with the 

Bearer prefix. This suggests a potential issue with the server-side token validation or a very short token expiration time, preventing successful submission
