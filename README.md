# Complaint Management System

A full-stack web application for managing customer complaints with complete admin workflow and audit trail.

## Features

- User Complaint Submission - Simple form for submitting complaints
- Admin Dashboard - Complete management interface for complaints  
- Status Workflow - OPEN → IN_PROGRESS → RESOLVED → CLOSED with validation
- Staff Assignment System - Assign complaints to staff members
- Audit Trail - Complete history of all status changes and updates
- Real-time Updates - Dynamic frontend with live data
- MySQL Database - Persistent data storage

## Tech Stack

### Backend
- Spring Boot 3.5.5 - RESTful API framework
- Java 17 - Core programming language
- Spring Data JPA - Database ORM layer
- MySQL 8.0 - Relational database
- Maven - Dependency management and build tool

### Frontend
- HTML5 - Page structure and semantics
- CSS3 - Styling and responsive design
- JavaScript (ES6) - Client-side functionality and API communication
- Modern CSS - Flexbox, Grid, and responsive design

## Installation & Setup

### Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6+
- Modern web browser

### Step 1: Database Setup
Create MySQL database: complaint_management_db

### Step 2: Configure Database
Edit src/main/resources/application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/complaint_management_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

### Step 3: Run Backend
Navigate to project root: cd complaint-management-system
Run Spring Boot application: mvn spring-boot:run
Backend will start at: http://localhost:8080

### Step 4: Run Frontend
Open frontend/index.html in browser
Or use Live Server in VS Code: Right-click index.html → "Open with Live Server"
Frontend will run at: http://localhost:3000 (if using Live Server)

## API Endpoints

### Complaints
- GET /api/complaints - Get all complaints
- GET /api/complaints?status={status} - Filter by status
- GET /api/complaints/{id} - Get complaint by ID
- POST /api/complaints - Create new complaint
- GET /api/complaints/reporter/{name} - Get complaints by reporter

### Management
- POST /api/complaints/{id}/assign - Assign complaint to staff
- POST /api/complaints/{id}/update-status - Update complaint status

### Test Endpoint
- GET /api/complaints/test - Basic connectivity test

## User Roles & Workflow

### End Users
1. Submit complaints through web form
2. View their complaint history  
3. Track complaint status

### Administrators
1. View all complaints in admin dashboard
2. Assign complaints to staff members
3. Update complaint status through workflow
4. Filter complaints by status
5. View complete audit trail

### Status Workflow
OPEN → IN_PROGRESS → RESOLVED → CLOSED
- Strict validation prevents invalid status transitions
- Each status change is logged with timestamp and notes

## Frontend Features

### Pages
- New Complaint - Submission form with validation
- My Complaints - Personal complaint history
- Admin Queue - Management dashboard with filters  
- Complaint Detail - Detailed view with timeline

### Components
- Status chips with color coding
- Dynamic filtering system
- Real-time form validation
- Responsive design for mobile/desktop

## Validation & Business Rules

- Status transition validation
- Single active assignment per complaint  
- Required field validation
- SQL injection prevention
- CORS configuration for frontend-backend communication

## Testing

### Manual Testing
1. Create multiple test complaints
2. Test status transitions
3. Verify assignment functionality
4. Check audit trail logging
5. Test filtering and search

### API Testing
Use Postman or Thunder Client to test:
- All CRUD operations
- Status change validation  
- Error handling

## Deployment Ready

### Environment Configuration
- Separate profiles for development/production
- Environment-specific database configurations
- Proper error handling and logging

### Build for Production
Create executable JAR: mvn clean package
Run production build: java -jar target/complaint-management-system-0.0.1-SNAPSHOT.jar

## License

This project is open source and available under the MIT License.

## Developer

Developed as a full-stack project demonstrating Spring Boot backend with modern frontend development.
