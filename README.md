# recipe_site
Simple multi-user recipe database. Project made with vue.js, spring boot and mongodb. Do not use in production - lacks a lot of security features.

# Setup
1. Download and install mongodb from here: https://www.mongodb.com/download-center/community
2. Create a database named `recipe_site`. You can use Mongodb Compass Community.
![MongoDB Compass Community](https://imgur.com/2NrzIW2)
3. Create collections: `comments`, `images`, `recipes` and `users`. 
4. Clone this repository into an empty drirectory of your choice `git clone https://github.com/impune-pl/recipe_site .`
5. Open new terminal window in the directory, and execute command `.\mvnw spring-boot:run`
6. Visit http://localhost:8081/ to test the application.
