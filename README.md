This web app allows users to create and manage tasks.


Installation:

- install openJDK 17
- create a "secret.properties" file in "TaskTracker/src/resources" and choose a secret key to be used for the JWTs. "secret.properties" should contain only one line "jwt.secret=enterSecretKeyHere"
- optional: connect a MySQL database in the application.properties. The default configuration uses the in-memory database H2.
- install gradle
- navigate to "TaskTracker" and build the project with gradle ("bash ./gradlew build")
- run the project ("bash ./gradlew bootRun")
- access the app via localhost:8080 (this can be changed in the application.properties configuration)
- by default, this repository contains a build of the frontend react part in "TaskTracker/src/resources/static". This can of course be replaced if necessary


Features:
- simple account creation (username + password)
- dashboard with active tasks which can be marked as completed (or other statuses)
- viewing a list of all of the user's tasks, with edit / delete functionality
- adding tasks, consisting of title, description, status and deadline, as well as optional subtasks
- creating task templates that can be used to create active tasks following that template
- creating recurring tasks, that are automatically added to the active task list in regular intervals (daily, weekly, monthly), as well as editing / deleting them
