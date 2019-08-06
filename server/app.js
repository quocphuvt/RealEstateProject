const express = require("express");
const bodyparser = require("body-parser");
const mongoose = require("mongoose");
const fs = require("fs");
//APIs
const UserApi = require("./connectors/users");
const RealApi = require("./connectors/reals")

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
            console.log('==> userData');
            console.log(userData);
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
            if(userExisted === 1){
                console.log("User was existed");
            }
            else {
                console.log("Register success");
                userApi.registerUser(userObject)
            };
           
        });

        app.post("/sign_in", (request, response, next) => {
            const userData = request.body;
            console.log(userData)
            userApi = new UserApi(response);
            userApi.checkUserLogin(userData.id, userData.password);
        });

        app.post("/real_creating",(request, response, next)=>{
            const realData = request.body;
            console.log(realData);
            const realApi = new RealApi(response);
            realApi.createReal(realData);

        })

        app.post("/listreal",(request,response,next)=>{
            const realApi = new RealApi(response);
            realApi.getAllReals();
            
        })
        app.post("/update_user",(request, response, next)=>{
            const realData = request.body;
            console.log(realData);
            const userApi = new UserApi(response);
            userApi.updateUserData(realData);

        })
    }
})

app.listen(8000); //Port of Server

