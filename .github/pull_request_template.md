# Pull Request Template

Provide a clear description of the changes in this pull request. Follow the sections below.

---

## Summary of Changes

Briefly describe what this pull request adds, fixes, or modifies.

Example:
- Implemented book search UI
- Added BookDao CRUD operations
- Updated MySQL schema for Books table

---

## Areas Affected

- GUI (JavaFX controllers or FXML)
- BO (business logic)
- DAO (database access and SQL)
- Utils (shared utilities)
- Model (entity classes)
- Database schema or SQL files
- Documentation

---

## How to Test

Describe the steps used to verify your changes. Include setup notes if applicable.

Example:

1. Launch the application.
2. Navigate to the Books catalog screen.
3. Search for a known book title.
4. Confirm that results are displayed correctly.
5. Confirm no exceptions appear in the console.

List the tests you performed:

- Application builds without compilation errors
- Feature works as expected when running the application
- SQL queries operate correctly (if applicable)
- No unrelated features were broken during development

---

## Screenshots (if applicable)

Add screenshots here for any GUI-related changes.

---

## Additional Notes

Include any extra details, open questions, or context reviewers should know before approval.

Example:
- This PR requires seed-data.sql to be updated.
- This PR depends on another branch being merged first.

