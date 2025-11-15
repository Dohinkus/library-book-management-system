# Contributing Guide for the Library Book Management System (LBMS)

This document describes how our team collaborates on this project using Git and GitHub. Please follow these guidelines to keep the repository consistent and maintainable.

---

# Branching Model

The main branch is protected. This prevents accidental pushes, force pushes, and deletion.

All work must be done in feature branches and merged through pull requests.

---

# Creating a Feature Branch

Before creating a new branch, make sure your local main branch is up to date:

```
git checkout main
```
```
git pull
```

Create your branch using the format:

```
git checkout -b feature/<short-description>-<your-name>
```

Examples:

```
git checkout -b feature/books-crud-erik
git checkout -b feature/login-ui-brycen
git checkout -b feature/auth-backend-elisa
git checkout -b feature/db-schema-everette
```

---

# Committing Your Work

Check what has changed:

```
git status
```

Stage your changes:

```
git add .
```

or stage individual files:

```
git add path/to/File.java
```

Commit using the project's commit message format:

```
git commit -m "feat: implement basic book search UI"
```

Push your branch to GitHub:

```
git push -u origin feature/login-ui-brycen
```

For more details on commit formatting, see:

docs/COMMIT_MESSAGES.md

---

# Pull Requests

When your work is ready for review:

1. Go to the GitHub repository.
2. Select Compare and Pull Request.
3. Complete the pull request template.
4. Assign at least one reviewer.
5. Wait for feedback.
6. Make necessary changes, then push again:

```
git add .
```
```
git commit -m "fix: address review feedback"
```
```
git push
```

Only the project manager (or designated approver) merges pull requests into main.

Do not merge your own pull request unless explicitly instructed.

---

# Project Structure

All Java code is stored under:

src/main/java/lbms/

Package organization:

gui/      JavaFX controllers and user interface logic  
dao/      Data Access Objects for MySQL and JDBC  
bo/       Business Objects containing application rules  
utils/    Shared utilities including database connection and hashing

---

# Testing Requirements

Before opening a pull request:

- Ensure that the project builds successfully.
- Run your feature and verify that it behaves correctly.
- If you touched database code, verify your SQL in MySQL Workbench.
- Provide screenshots for GUI-related changes.
- Document anything relevant in your PR.

---

# Coding Style

Use this project's general coding style:

- Classes use UpperCamelCase (e.g. BookService, UserDao)
- Methods and variables use lowerCamelCase (e.g. checkLogin)
- Constants use SCREAMING_SNAKE_CASE
- Keep methods focused and readable
- Follow established patterns in existing files

---

# Getting Help

If you need help:

- Ask in the discord
- Comment directly on your pull request
- Tag Erik for branch or merge issues

Communication is encouraged. Do not wait until the last minute to ask questions.
