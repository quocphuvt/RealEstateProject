const express = require("express");
const bodyparser = require("body-parser");
const mongoose = require("mongoose");
const app = express();

const user = require("./controllers/UserController");
const real = require("./controllers/RealController");
const router = require("./routes/router");

app.set("view engine", "ejs");
app.set("views", "./views");
app.use(bodyparser.json({ limit: "50mb" }));
app.use(bodyparser.urlencoded({ limit: "50mb", extended: true, parameterLimit: 50000 }));
app.use(express.static('public'));

mongoose.connect("mongodb://localhost/RealEstateManager", (err, client) => {
    if (err) {
        console.log("Unable to connect to MongoDB. Error: " + err)
    }
    else {
<<<<<<< HEAD
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
            if (userExisted === 1) {
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

        app.post("/real_creating", (request, response, next) => {
            const realData = request.body;
            console.log(realData);
            const realApi = new RealApi(response);
            realApi.createReal(realData);

        })

        app.post("/listreal", (request, response, next) => {
            const realApi = new RealApi(response);
            realApi.getAllReals();

        })
        app.post("/update_user", (request, response, next) => {
            const realData = request.body;
            console.log(realData);
            const userApi = new UserApi(response);
            userApi.updateUserData(realData);

        })
        app.post("/sort_price", (request, response, next) => {
            const realData = request.body;
            console.log(realData);
            const realApi = new RealApi(response);
            realApi.sortByPrice();

        })}})

        app.listen(8000); //Port of Server
=======
        app.use('/user', user); //Api
        app.use('/real', real);
        app.use('/', router); //Web page router
    }
})

app.listen(8000);
>>>>>>> dc4295590295785dec8f9cec118726040f0ae54b

