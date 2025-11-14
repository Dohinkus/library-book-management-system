# Contributing Guide – Library Book Management System (LBMS)

Thanks for contributing to the LBMS project! This document explains **how we work as a team** using Git and GitHub.

---

### Branching Model

- The `main` branch is **protected**:
  - No direct pushes
  - No force pushes
  - No deletions
- All work happens on **feature branches** and is merged via **Pull Requests (PRs)**.

# Creating a feature branch

Always start from the latest `main`:

```
git checkout main
git pull
git checkout -b feature/<short-description>-<your-name>
```
# then creating your feature branch
Examples:
```
git checkout -b feature/books-crud-erik
git checkout -b feature/login-ui-brycen
git checkout -b feature/auth-backend-elisa
git checkout -b feature/db-schema-everette
```

---

### Committing Your Work

Check what changed:
```
git status
```

Stage files:
```
git add .
```
or
```
git add path/to/File.java
```

Commit using our commit message style (see docs/COMMIT_MESSAGES.md):
```
git commit -m "feat: implement basic book search UI"
```

Push your branch:
```
git push -u origin feature/login-ui-brycen
```
