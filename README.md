# Aufgabenliste

*“Aufgabenliste” means “To-do list” in German*

A simple task management web application built with **Spring Boot**, **PostgreSQL**, and a lightweight **HTML/CSS/JavaScript** frontend. The app supports creating, updating, deleting, and filtering tasks with status and date filters. It’s fully Dockerized for easy setup.

---

## Overview

Aufgabenliste helps you organize and track your tasks efficiently.

- Create, update, and delete tasks
- Mark tasks as **Completed** or **Pending**
- Filter by date and status
- Responsive frontend with a clean UI
- Self-hostable with Docker Compose

---

## Demo

[![Watch the video](https://raw.githubusercontent.com/DrDrunkenstien-10/aufgabenliste/main/demo/Aufgabenliste_Demo_Video_Thumbnail.png)](https://raw.githubusercontent.com/DrDrunkenstien-10/aufgabenliste/main/demo/Aufgabenliste_Demo.webm)

## Setup Instructions

### Option A: Run with Docker Compose (recommended)

1. **Clone this repository**
2. **Create a `.env` file** in the project root (see [Environment Variables](#environment-variables) section)
3. **Run:**
    ```sh
    docker compose up -d
    ```
4. **Access the app:**
    - Frontend: [http://localhost:5500](http://localhost:5500)
    - Backend API: [http://localhost:4000/api/v1/tasks](http://localhost:4000/api/v1/tasks)

### Option B: Run locally without Docker

1. **Install PostgreSQL** and create a database
2. **Update** `application.properties` **with DB credentials**
3. **Start Spring Boot app** from project root:
    ```sh
    mvn spring-boot:run
    ```
4. **Serve frontend** (open `frontend/index.html` in browser or use Live Server)

---

## Environment Variables

Example `.env` file:

```env
POSTGRES_DB=aufgabenliste_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=password
POSTGRES_PORT=5431
SPRING_SERVER_PORT=4000
```

---

## Usage

- Open frontend in browser: [http://localhost:5500](http://localhost:5500)
- By default, you’ll see all **Pending** tasks for today
- Use filters to change date and status (**Pending** / **Completed** / **All**)
- Add, update, or delete tasks via the UI

---

## API Documentation

OpenAPI specifications are available in `src/docs/`:
- `openapi.yaml`
- `openapi.json`

---

## Motivation

- Personal use for keeping track of daily tasks (earlier using Notepad)
- Practice writing REST APIs with Spring Boot
- Learn and apply best practices for REST API design

