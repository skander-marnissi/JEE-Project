# JEE-Project

A JEE web App using JSF2, Hibernate, MySQL, JavaBeans and Majora running on wildfly 11.
Which manage employees missions and tasks in a company.

## Installation

Open terminal and type the following commands: 

```bash
git clone https://github.com/SkanderMarnissi/JEE-Project/
```
After downloading:

```bash
cd JEE-Project
```

Then follow the steps:

### Step 1: Install JEE IDLE(eclipse, netbeans...).

### Step 2: Install WildFly server.

### Step 3: Install all the dependencies located in the Pom.xml file.

**Note:You can just run the maven Pom for step 3 under backend_server/backend_server/backend_server-ejb/ directory.**

## How it works?
In this project, the final output web site that has different session(Admin, Manager, Financer and Employee) and in which each can:

**Admin session: manage managers, financer and employees.**
**Manager session: manage missions and affect employees to it.**
**Financer session: manage the financial budget for the employees fees in missions.**
**Employee session: show mission progression for the manager and send all fees to the financer.**

**Note: You can find all the xhtml pages files under backend_server/backend_server/backend_server-web/src/main/webapp directory.**

## Usage 
In order to process the program and make it work you need to:

### Step 1: Run the project under Maven to install all the dependencies from Pom.xml.

### Step 1: Run the WildFly server.

### Step 2: Add the project to the server from the IDLE.

### Step 3: Create a persistence-unit for ORM(Hibernate) from the wildfly server:
If you don't know how to do it go visit: https://docs.wildfly.org/17/Admin_Guide.html 

### Step 3: Connect your database from the wildfly server:
If you followed the step three documentation you will know how to connect MySQL database with wildfly persistence unit.

### Step 4: Connect your database to the ORM(Hibernate):
You can do that from the persistence.xml file under backend_server/backend_server/backend_server-ejb/src/main/resources/META-INF/

Edit the following line with the name of your new persistence-unit name:

```xml
    ...
      <jta-data-source>
        java:/piMission <!--//(Replace "piMission" by the persistence-unit name that you created in step 3) -->
      </jta-data-source>
   ...   
```

### Step 3: Open localhost(or given URL in console) and go to /login.xhtml:
#### To login default usernames/passwords were made for each session:

**Admin session: login="admin"|password="admin"**
**Manager session: login="manager"|password="manager"**
**Financer session: login="financer"|password="financer"**
**Employee session: login="emp"|password="emp"**

**Note: if you want you can create your own login for each session from the admin session where default username and password is hard coded.**



*SKANDER MARNISSI COPYRIGHT © 2020 - ALL RIGHTS RESERVED*
