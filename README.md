# Online cinema

---

To install Angular CLI run the command
```bash
npm install -g @angular/cli
```

Next we should install all NodeJS packages required to build application. Got the `geek-tube-angular-frontend directory` and run the command 
```bash
npm i
```

Next that we are ready to run frontend with testing server by command
```bash
ng serve
```

Frontend going to be available here http://localhost:4200/

Notice the backend proxying configuration is [here](/geek-tube-angular-frontend/proxy.conf.json) in the sources

After that go to the project root in another console and run backend with
```bash
./mvnw spring-boot:run
```

How to work with a local profile?

Create a new profile with the name "local" according to this instruction [](https://www.jetbrains.com/help/idea/customizing-profiles.html)

Go to Run -> Profile -> Edit Configurations -> Active Profiles -> "local"

---

How to work with directory.properties?

directory.properties 

    


