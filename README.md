*** Bookstore Inventory Management System ***

*** Open APP from - BookStoreManagementSystem\dist - BookStoreManagementSystem.jar ***


The Bookstore Inventory Management System is a Java Swing (NetBeans) desktop application used to manage:

-Books
-Categories
Suppliers
-Stock levels
-Reports
-User login
-This project uses:
-Java Swing (GUI)
-MySQL database
-DAO architecture
-Modular UI components
-Window Manager for auto-closing frames

*** Project Structure ***
BookStoreManagementSystem/
│
├── src/com/bookstore/
│   ├── MainApp.java
│   ├── utils/DBConnection.java
│   │
│   ├── model/
│   │   ├── Category.java
│   │   ├── Supplier.java
│   │   ├── Book.java
│   │   └── User.java
│   │
│   ├── dao/
│   │   ├── CategoryDAO.java
│   │   ├── SupplierDAO.java
│   │   ├── BookDAO.java
│   │   └── UserDAO.java 
│   │
│   ├── ui/
│       ├── dashboard/DashboardFrame.java
│       ├── categories/CategoryFrame.java
│       ├── suppliers/SupplierFrame.java
│       ├── books/AddBookFrame.java
│       ├── books/EditBookFrame.java
│       ├── books/BookListFrame.java
│       ├── stock/StockFrame.java
│       ├── reports/ReportsFrame.java
│       ├── login/LoginFrame.java
│       └── common/
│           ├── FormUtils.java
│           ├── StyledButton.java
│           ├── HeaderPanel.java
│           ├── Theme.java
│           ├── TableStyler.java
│           ├── WindowManager.java
│           └── UIUtils.java
│
├── resources/
│   ├── images/app_icon.png
│   └── docs/report_templates
│
├── script.sql
└── README.md


*** How to Run the Project ***
1. Requirements

-Java JDK 
-NetBeans 
-MySQL 8+
-MySQL Connector JAR added to project libraries

2. Database Setup

-Open MySQL Workbench
-Copy script.sql
-Run it
-Database bookstore will be created

3. Configure DB Connection

Open:
src/com/bookstore/utils/DBConnection.java

Set your MySQL username/password:

private static final String URL = "jdbc:mysql://localhost:3306/bookstore";
private static final String USER = "root";
private static final String PASS = "your_password";

*** Features ***
- User Login
- Dashboard (Summary + Low Stock Panel)
- Manage Books (CRUD)
- Manage Categories
- Manage Suppliers
- Manage Stock (Increase/Decrease with popup)
- Auto-close all windows using WindowManager
- Modern UI theme (StyledButton, HeaderPanel, FormUtils)
- JTable with custom styling


