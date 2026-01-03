ATM Machine Simulation Project
📋 Project Overview
A Java-based ATM simulation system built using Servlet, JSP, JDBC, MySQL, and HTML. This project simulates real ATM operations with user authentication, transaction processing, and session management.

🚀 Features
✅ User Registration (Set PIN)

✅ Secure Login Authentication

✅ Deposit Money

✅ Withdraw Money

✅ Balance Inquiry

✅ PIN Change Facility

✅ Session Management

✅ Database Persistence

✅ Responsive HTML Interface

✅ Error Handling with Popups

🛠️ Technologies Used
Backend: Java Servlet, JDBC

Frontend: HTML, JSP, JavaScript

Database: MySQL

Server: Apache Tomcat

IDE: NetBeans (Recommended)

Build Tool: Maven (Standard)

📁 Project Structure
text
ATM_Simulation_Project/
│
├── src/main/java/
│   └── setpin.java                 # Main Servlet handling all operations
│
├── src/main/webapp/
│   ├── index.html                  # Home page with options
│   ├── setpin.html                 # User registration page
│   ├── atm.html                    # Login page
│   ├── atmoperation.html           # Operations menu
│   ├── deposit.html                # Deposit form
│   ├── withdraw.html               # Withdraw form
│   ├── balance.html                # Balance check form
│   ├── changepin.html              # Change PIN form
│   └── WEB-INF/
│       └── web.xml                 # Servlet configuration
│
├── database/
│   └── atm_database.sql            # Database schema
│
├── lib/                            # JDBC driver (mysql-connector-java-8.0.xx.jar)
├── README.md                       # This file
└── pom.xml                         # Maven configuration
🗄️ Database Setup
1. Create Database
sql
CREATE DATABASE atmmachine;
USE atmmachine;
2. Create Users Table
sql
CREATE TABLE setpin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    pin VARCHAR(6) NOT NULL,
    cpin VARCHAR(6) NOT NULL,
    balance DECIMAL(10,2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
3. (Optional) Create Transactions Table
sql
CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    transaction_type VARCHAR(20),
    amount DECIMAL(10,2),
    balance_after DECIMAL(10,2),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES setpin(id)
);
⚙️ Installation & Setup
Prerequisites
Java JDK 8 or higher

Apache Tomcat 9 or higher

MySQL 5.7 or higher

NetBeans IDE (or Eclipse/IntelliJ)

MySQL Connector/J driver

Step 1: Clone/Download Project
bash
git clone https://github.com/yourusername/atm-simulation.git
Step 2: Database Configuration
Start MySQL server

Execute atm_database.sql script

Update database credentials in setpin.java:

java
return DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/atmmachine", 
    "root", 
    "yourpassword"
);
Step 3: Configure Project in IDE
NetBeans:

File → New Project → Java Web → Web Application

Copy files to respective folders

Add MySQL Connector to Libraries

Eclipse:

File → New → Dynamic Web Project

Copy files to WebContent folder

Add MySQL Connector to Build Path

Step 4: Deploy and Run
Build the project

Deploy to Tomcat server

Access application: http://localhost:8080/ATM_Simulation/

📊 Application Flow
Home Page (index.html) → Choose Set PIN or ATM Operations

Registration (setpin.html) → Create account with name and PIN

Login (atm.html) → Enter credentials for authentication

Operations Menu (atmoperation.html) → Choose transaction type

Perform Operations → Deposit/Withdraw/Balance/Change PIN

Logout → Session destroyed, return to home

🔒 Security Features
Password hashing (if implemented in future)

Session-based authentication

SQL injection prevention using PreparedStatement

Input validation on client and server side

Automatic session timeout

🧪 Testing the Application
Test Cases:
User Registration:

Enter valid credentials → Success popup

Mismatched PINs → Error message

Duplicate username → Error message

Login:

Valid credentials → Welcome message

Invalid credentials → Error message

Transactions:

Deposit positive amount → Balance increases

Withdraw within limit → Balance decreases

Withdraw exceeding balance → Error message

Balance check → Display current balance

PIN change with verification → Success message

🐛 Troubleshooting
Issue	Solution
404 Error	Check servlet mapping in web.xml
Database Connection Failed	Verify MySQL credentials and service
Session not working	Check session attribute names match
Popups not showing	Enable JavaScript in browser
Tomcat deployment error	Clean and rebuild project
📈 Future Enhancements
Add transaction history

Implement email/SMS notifications

Add admin panel for user management

Implement biometric authentication

Add multi-language support

Create mobile-responsive design

Implement REST API version

👥 Contributor Guidelines
Fork the repository

Create a feature branch (git checkout -b feature/AmazingFeature)

Commit changes (git commit -m 'Add AmazingFeature')

Push to branch (git push origin feature/AmazingFeature)

Open a Pull Request

📄 License
This project is licensed under the MIT License - see the LICENSE file for details.

🙏 Acknowledgments
Java Servlet Documentation

MySQL Official Documentation

Apache Tomcat Team

All contributors and testers

📧 Contact
Developer: SANTHOSH M
Email: santhosh473abi@gmail.com
GitHub: https://github.com/Santhoshabi473

⭐ If you find this project useful, please give it a star! ⭐

🚀 Quick Start Commands
For Windows:
bash
# Start MySQL
net start mysql

# Start Tomcat
catalina start

# Access application
start http://localhost:8080/ATM_Simulation/
For Linux/Mac:
bash
# Start MySQL
sudo systemctl start mysql

# Start Tomcat
./catalina.sh start

MySQL Connector/J

Happy Coding! 🎯
