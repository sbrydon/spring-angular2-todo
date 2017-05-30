## todo-backend
A typical Spring Boot application so you can use the usual Spring Maven plugins for building & running.
It can be packaged as a `war` for deployment on Tomcat or similar.

#### Setup
Referring to `todo-rest/../application.yml` add the `flyway` user to your MariaDB (or MySQL) server.
This user is for migrations (`todo-data/../V1.[0,1]__[Schema,Seed].sql`) which automatically create the 
`todo` database as well as the `todo-rest` user in the same `application.yml` file.

#### Overview
The application is split into 3 projects: `todo-data` for storing Todos, `todo-service`
for managing them, and `todo-rest` for exposing them as REST resources. To visualise the dependencies: 
`todo-data` <- `todo-service` <- `todo-rest`.

There are some JUnit tests for each project covering private and public data validation, services,
and controllers.

`todo-rest` implements Basic Auth purely for demonstrating security configuration - OAuth2 would be 
preferred in a real scenario.

###### DB Schema
![schema](https://i.imgur.com/ZTrbGOP.png)

## todo-frontend
An Angular4 single-page application built using the `angular-quickstart` seed. Just run `npm install`
then `npm start` from the `todo-frontend/` directory.

#### Setup
If you changed the port in `todo-rest/../application.yml` or aren't running `todo-rest` on localhost,
open `todo-frontend/../app-config.module.ts` and configure:

```javascript
export const APP_DI_CONFIG: AppConfig = {
  apiUrl: 'http://localhost:42050'
};
```
#### Overview
The application is written in TypeScript and uses Bootstrap and its Grid layout. `@ng-bootstrap/ng-bootstrap`
is used for its pagination component.

Forgive the lack of directory structure in `src/app` and the auto-generated `*.js` & `*.js.map` pollution.

The same comment regarding Basic Auth applies here, and is particularly relevant to the client because to simulate
login with stateless REST it stores credentials in localStorage 😬
