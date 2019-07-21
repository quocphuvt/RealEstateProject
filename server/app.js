const express = require("express");
const bodyparser = require("body-parser");
const mongoose = require("mongoose");
const fs = require("fs");
//APIs
const UserApi = require("./connectors/users");

const app = express();

app.use(bodyparser.urlencoded({ extended: false }));
app.use(bodyparser.json());

mongoose.connect("mongodb://localhost/RealEstateManager", (err, client) => {
    if(err){
        console.log("Unable to connect to MongoDB. Error: "+err)
    }
    else {
        app.post("/register", (request, response, next) => {
            const userData = request.body;
            const userObject = {
                id: userData.id,
                password: userData.password
            }
            UserApi = new UserApi(response);
            UserApi.registerUser(userObject);
            // response.json("User was created!");
        });

        app.post("/sign_in", (request, response, next) => {
            const userData = request.body;
            UserApi.checkUserLogin(userData.id, userData.password);
            // response.json("Login success");
        });
    }
})

app.listen(8000); //Port of Server

