# Complaint Management System

A comprehensive Complaint Management System designed to handle complaints efficiently from submission through resolution with audit logging, role-based workflows, and assignment management.

## Features

- Create, view, update, and delete complaints.
- Complaint status workflow: OPEN → IN_PROGRESS → RESOLVED → CLOSED with enforced transitions.
- Single active assignment per complaint with staff assignment management.
- Detailed timeline audit log of updates and status changes.
- Filtering and search of complaints by status, category, city, reporter, and assignee.
- Responsive frontend with pages for new complaints, personal complaint list, admin queue, and detailed complaint timeline.

## Technology Stack

- Backend: Your chosen language/framework (e.g., Node.js/Express, Spring Boot, Django)
- Database: Relational database with constraints for status and assignment integrity (e.g., PostgreSQL, MySQL)
- Frontend: Framework of choice (e.g., React, Angular, Vue) with components for status chips, assignment dropdowns, and timelines
- Version Control: Git and GitHub

## Getting Started

### Prerequisites

- Install your backend runtime (Node, Java, Python, etc.)
- Install your database server (PostgreSQL, MySQL, etc.)
- Install frontend tooling (Node.js, npm, yarn, etc.)

### Installation

1. Clone the repository:
   git clone https://github.com/Santhoshiini/complaint-management-system.git
2. Setup backend:
- Configure your database connection.
- Run database migrations to create tables and constraints.
- Start the backend server.

3. Setup frontend:
- Navigate to the frontend directory.
- Install dependencies with `npm install` or `yarn`.
- Start the frontend development server.

### Seed Data

Seed complaints, staff, and update logs to get started quickly with realistic data.

## API Endpoints Overview

- `GET /complaints` - List and filter complaints
- `POST /complaints` - Create new complaint
- `GET /complaints/{id}` - Get complaint details
- `POST /complaints/{id}/assign` - Assign complaint to staff
- `POST /complaints/{id}/updates` - Add timeline update
- `POST /complaints/{id}/transitions` - Change complaint status
- `GET /complaints/{id}/timeline` - Get complaint timeline audit log

## Testing

- Unit and integration tests ensure enforcement of status workflow and single assignment.
- UI tests validate complaint creation, assignment, update workflows, and timeline display.

## Contributing

Contributions are welcome! Please fork the repo and submit a pull request with your changes.

## License

Specify your license here (e.g., MIT License).

## Contact

For questions or feedback, contact: srisanthoshini25@gmail.com


   
