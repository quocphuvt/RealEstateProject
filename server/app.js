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
        app.post("/register", async (request, response, next) => {
            const userData = request.body;
            const userObject = {
                id: userData.id,
                password: userData.password,
                fullName: userData.fullName,
                birthday: userData.birthday,
                city: userData.city,
                phoneNumber: userData.phoneNumber,
                gender: userData.gender
            }
            userApi = new UserApi(response);
            const userExisted = await userApi.checkIdExisted(userData.id);
            if(userExisted != null){
                return response.json({"id": null});
            }
            else {
                return userApi.registerUser(userObject)
            };
           
        });

        app.post("/sign_in", (request, response, next) => {
            const userData = request.body;
            console.log(userData);
            userApi = new UserApi(response);
            userApi.checkUserLogin(userData.id, userData.password);
        });
    }
})

app.listen(8000); //Port of Server

