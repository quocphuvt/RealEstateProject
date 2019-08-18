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
        app.use('/user', user); //Api
        app.use('/real', real);
        app.use('/', router); //Web page router
    }
})

app.listen(8000);

