# LAMISPlus 2.0 Central Client-Sync Module

## System Requirements

### Prerequisites to Install
- IDE of choice (IntelliJ, Eclipse, etc.)
- Java 8+
- PostgreSQL 14+
- `.m2` folder from Google Drive https://drive.google.com/file/d/17F6wHPADVVaDBabajrM9wABUeB04i6D1/view?usp=sharing

## Run in Development Environment

### How to Install Dependencies
1. Install Java 8+
2. Install PostgreSQL 14+
3. Download and replace the system `.m2` folder.
4. Clone the git repository:
    ```bash
    git clone https://github.com/lamisplus/central-sync-client.git
    ```
5. Open the project in your IDE of choice.


### Run Build and Install Commands
1.. Run Frontend Build Command from the root directory:
```bash
npm run build
```
2. Run Maven clean install from the root directory:
    ```bash
    mvn clean install
    ```

## How to Package for Production Environment
1. Run Maven package command from the root directory:
    ```bash
    mvn clean package
    ```

## Install Packaged Jar file on LAMISPlus Core
1. Access install page using LAMISPlus core UI

2. New install or update Client-Sync Module:

## Visit the Application
- Visit the application on a browser at the configured port:
    ```
    http://localhost:8080
    ```
- Visit the Client-Sync Module by selecting "Client-Sync" on the sidebar


## Access Swagger Documentation
- Visit the application at:
    ```
    http://localhost:8080/swagger-ui.html#/
    ```

## Access Application Logs
- Application logs can be accessed in the `application-debug` folder.

## Authors & Acknowledgments
### Main contributors
- Emeka https://github.com/drjavanew
- Mathew Adegbite https://github.com/mathewade
- Kennedy Kirui https://github.com/kenkirui
- Peter Abiodun https://github.com/Asquarep

### Special mentions
- Niyi Ogungbemi https://github.com/niyiment
