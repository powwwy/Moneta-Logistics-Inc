# Moneta Logistics Incorporated 🚢📦🌍 

Moneta Logistics Inc. is a distributed Java-based logistics management system designed to simulate international trade operations between countries. It features a modular role-based structure where various actors manage ports, ships, containers, and trade transactions through coordinated interactions.

## 📘 Overview

This system models the complexities of global logistics by defining four key actors, each responsible for a distinct set of functionalities. It uses object-oriented design and simulates file-based persistence to track dynamic trade activities, container movements, and shipping operations.

---

## 👥 System Roles & Responsibilities

### 🏛️ 1. Port Authority
Responsible for managing port infrastructure and activities.
- **Add Port**: Register a new port under a country's control.
- **Update Port Status**: Set port operational status (e.g., active, inactive, maintenance).
- **Assign Container to Storage**: Allocate containers to port storage.
- **View Docked Ships**: Monitor ships currently docked at the port.
- **Assign Employees**: Allocate personnel to manage port operations.

### ⛴️ 2. Ship Operator
Manages all aspects of ship-related logistics.
- **Register Ship**: Add a new ship and link it to a country.
- **Set Arrival/Departure**: Schedule when ships dock or leave ports.
- **Assign Container to Ship**: Load containers for export/import.
- **Update Ship Status**: Track ship progress (e.g., en route, delayed, arrived).

### 📦 3. Container Manager
Oversees container registration and tracking across the system.
- **Register Container**: Add new container records with metadata.
- **Track Container**: View current status and location (on ship or at port).
- **Assign Container to Port/Ship**: Allocate containers to a storage or shipping location.
- **Update Transit Info**: Log movements or status changes during transit.

### 🌐 4. Country Trade Manager
Handles inter-country relationships and trade logistics.
- **Register Country**: Create a new country profile.
- **Initiate Trade**: Begin trade transactions between countries.
- **Share Ship/Port Info**: Coordinate ship and port availability for trading.
- **View Country Assets**: See ships, ports, and containers under national ownership.

---

## 🧩 Key System Interactions (Use Case Summary)

| **Actor**               | **Use Cases**                                                                 |
|-------------------------|-------------------------------------------------------------------------------|
| Port Authority          | Add Port, Update Port Status, View Docked Ships, Assign Container, Assign Employees |
| Ship Operator           | Register Ship, Set Arrival/Departure, Assign Container to Ship, Update Ship Status |
| Container Manager       | Register Container, Track Container, Assign to Port/Ship, Update Transit Info |
| Country Trade Manager   | Register Country, Initiate Trade, Share Ship/Port, View Assets                |

Each role interacts with shared entities such as **Ports**, **Ships**, **Containers**, and **Countries**, ensuring modular, maintainable code and clear system boundaries.

---

## 🗃️ Entity Overview (Class + ERD Summary)

- **Country**: Owns ports and ships, initiates trades.
- **Port**: Stores containers, docks ships, managed by employees.
- **Ship**: Transports containers between ports; linked to a country.
- **Container**: Movable storage units tracked by ID and status.
- **Employee**: Assigned to ports for operational support.
- **Trade**: Represents export/import actions between countries.

---

## 💾 Storage Design

The system simulates file-based persistence for each module to represent realistic I/O without external databases. Flat files act as storage for:
- Country Data
- Port and Ship Registries
- Container and Trade Logs
- Employee Assignments

---

## 🔧 Technologies

- Java (Core Logic & OOP Design)
- JavaFX (Planned GUI Integration)
- Flat File I/O (Data Simulation)
- UML, ER, and Use Case Diagrams (System Design)

---

## 📚 For Students

This project serves as an academic exercise in distributed systems, object-oriented programming, and logistics modeling. It balances complexity and clarity, making it ideal for students exploring:
- Multi-role software systems
- Real-world trade simulations
- Modular and scalable design patterns

---

## 📈 Future Plans

- 🔌 JavaFX Interface for user interaction
- 🧠 AI-based container routing (bonus module)
- 🔐 Authentication system for role-based access
- 🌐 RESTful API simulation for trade messages

---

## 👨‍💻 Authors

Developed by us as part of a distributed systems project (Strathmore University) simulation under **Moneta Logistics Inc.**

---

