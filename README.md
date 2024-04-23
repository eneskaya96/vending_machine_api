# vending_machine_api
Vending Machine Backend API with spring boot
Vending Machine Backend

Overview

This backend service for a vending machine supports operations necessary for both customers and suppliers. It handles monetary transactions, product selections, temperature checks, and more to facilitate an efficient vending machine operation.

Features

For Customers
Accepts Money: The machine accepts denominations of 1, 5, 10, and 20 units.
Product Selection: Users can select from the following products:
Water: 25 units
Coke: 35 units
Soda: 45 units
Refund Option: Users can cancel the request and take a refund.
Change Return: If applicable, the machine returns the selected product along with any remaining change.
For Suppliers
Reset Operations: Suppliers can reset the machine to its initial state.
Collect Money: Enables suppliers to collect all money from the machine.
Stock Management: Suppliers can add stock to the machine and update stock levels.
Pricing Management: Ability to raise or discount prices of the products.
Temperature Management: Ensures products are kept cool by performing dummy cooling operations periodically.
Security Features: Includes tamper and scam protection for both money and products.
Data Persistence
Reliable Storage: The machine uses a database (SQL or NoSQL, based on your preference) to permanently store all product information, ensuring no data loss in case of a crash or restart.
Technologies Used

Backend Language: [Preferred Language]
Database: [Your Database Choice]
Other libraries and tools as needed.
Setup and Installation

Clone the repository:
bash
Copy code
git clone [repository URL]
cd [project-folder]
Install dependencies:
bash
Copy code
[dependency installation commands]
Configure the environment:
Create a .env file or similar for environment-specific configurations (e.g., database connection details).
Initialize the database:
bash
Copy code
[database setup commands or scripts]
Run the application:
bash
Copy code
[command to run the application]
API Documentation

Add Money:
Endpoint: POST /money
Payload: { "denomination": 10, "quantity": 1 }
Select Product:
Endpoint: GET /select-product/{productId}
Cancel and Refund:
Endpoint: POST /refund
Collect Money (Supplier):
Endpoint: POST /collect-money
Add Stock (Supplier):
Endpoint: POST /add-stock
Payload: { "productId": 1, "quantity": 20 }
Temperature Check (Automated):
System automatically checks and logs temperature at regular intervals.
For detailed API routes and specifications, please refer to the included API documentation (link to detailed API docs).

Testing

Describe how to run unit, integration, or end-to-end tests.

Contributing

Guidelines for how other developers can contribute to this project.

License

Specify the project license.

This template provides a comprehensive guide for setting up and running your vending machine backend. Adjust the content to match your project specifics, especially in terms of setup instructions and technology choices.