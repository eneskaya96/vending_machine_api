# Vending Machine Backend

## Overview
This backend service for a vending machine supports essential operations for both customers and suppliers, facilitating an effective vending service with features like real-time transactions, temperature control, and security measures against tampering and scams.

## Features

### Customer Features
- **Accepts Money:** Supports denominations of 1, 5, 10, and 20 units.
- **Product Selection:**
  - Water: 25 units
  - Coke: 35 units
  - Soda: 45 units
- **Refund Capability:** Allows users to cancel the request and receive a refund.
- **Change Return:** Dispenses the correct product and returns any remaining change.

### Supplier Features
- **Reset Operations:** Resets the machine to default settings.
- **Collect Money:** Facilitates the collection of accumulated money.
- **Stock Management:** Enables restocking and stock adjustments.
- **Price Management:** Allows raising or discounting product prices.
- **Temperature Management:** Periodically checks and maintains the temperature to ensure product quality.
- **Security Measures:** Implements tamper and scam protection for money and products.

### Data Persistence
- **Database Integration:** Uses either SQL or NoSQL database to ensure all product and transaction data is persistently stored, preventing data loss during crashes or restarts.

## Technologies Used
- **Backend Language:** [Spring]
- **Database:** [Postgres]

## Setup and Installation
1. **Clone the Repository:**
   ```bash
   git clone [repository URL]
   cd [project-folder]
   
## API Documentation

Detailed API documentation is provided to guide you through using the backend services effectively. Here are some endpoints:

Add Money: POST /money
Select Product: GET /select-product/{productId}
Refund: POST /refund
Collect Money (Supplier): POST /collect-money
For more details, refer to the full API documentation.

## Testing

Run test :

		 ./gradlew cleanTest 
		 
		./gradlew test --info
		




