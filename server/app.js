const express = require("express");
const bodyparser = require("body-parser");
const mongoose = require("mongoose");
const fs = require("fs");
const app = express();
var multer = require("multer");
var path = require("path"); //Lấy đường dẫn
var upload = multer({ dest: "./images/upload" });
// const image2base64 = require('image-to-base64');
var base64Img = require('base64-img');

//APIs
const UserApi = require("./connectors/users");
const RealApi = require("./connectors/reals");
const UserModel = require("./models/user");
const RealModel = require("./models/real");

app.set("view engine", "ejs"); //Tạo view engine để đọc file ejs
app.set("views", "./views"); //Tạo views với đường dẫn chứa các file EJS
app.use(bodyparser.json({limit: "50mb"}));
app.use(bodyparser.urlencoded({limit: "50mb", extended: true,parameterLimit:50000}));
app.use(express.static('public')); //Đặt thư mục static để client đọc được các file như css,image,video,sound,... .

mongoose.connect("mongodb://localhost/RealEstateManager", (err, client) => {
    if (err) {
        console.log("Unable to connect to MongoDB. Error: " + err)
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
                gender: userData.gender,
                avatar: userData.avatar
            }
            userApi = new UserApi(response);
            const userExisted = await userApi.checkIdExisted(userData.id);
            if (userExisted != null) {
                return response.json({ "id": null });
            }
            else {
                return userApi.registerUser(userObject)
            };

        });

        app.post("/sign_in", (request, response, next) => {
            const userData = request.body;
            userApi = new UserApi(response);
            userApi.checkUserLogin(userData.id, userData.password);
        });

        app.post("/real_creating", (request, response, next) => {
            const realData = request.body;
            const realApi = new RealApi(response);
            realApi.createReal(realData);

        })

        app.post("/list_real", (request, response, next) => {
            const realApi = new RealApi(response);
            realApi.getAllReals();
        })

        app.post("/list_real_for_lease", (request, response) => {
            const realApi = new RealApi(response);
            realApi.getAllRealsForLease();
        })

        app.post("/list_real_for_sale", (request, response) => {
            const realApi = new RealApi(response);
            realApi.getAllRealsForSale();
        })

        app.post("/real_by_id", (request, response) => {
            const realData = request.body;
            const realApi = new RealApi(response);
            realApi.getRealById(realData.id);
        })

        app.post("/real_by_location", (request, response) => {
            const realData = request.body;
            const realApi = new RealApi(response);
            realApi.getRealByLocation(realData.location);
        })

        app.post("/num_real", (request, response) => {
            const realApi = new RealApi(response);
            realApi.countNumReal(request.body.id);
        })

        app.post("/update_user", (request, response, next) => {
            const realData = request.body;
            const userApi = new UserApi(response);
            userApi.updateUserData(realData);

        });

        app.post("/get_user", (req, res, next) => {
            const realData = req.body;
            const userApi = new UserApi(res);
            userApi.getCurrentUser(realData.id);
        })

        app.post("/sort_price", (request, response, next) => {
            const realData = request.body;
            console.log(realData);
            console.log(response)
            const realApi = new RealApi(response);
            realApi.sortByPrice();

        });

        app.post("/filter_price", (request, response, next) => {
            const realData = request.body;
            const realApi = new RealApi(response);
            realApi.filterRealByPrice(realData.minPrice, realData.maxPrice);

        });

        //Request to index page
        app.get("/login", (req,res) => {
            res.render("login");
        })

        app.get("/logout", (req, res) => {
            fs.writeFile("userid.json", JSON.stringify({id: null}), "utf8", (err)=> console.log(err));
            res.redirect("/login");
        })

        app.post("/login_result", (req, res) => {
            const reqData = req.body;
            UserModel.findOne({id: reqData.txtUsername}, (err, data) => {
                if(data != null && data.password === reqData.txtPassword) {
                    fs.writeFile("userid.json", JSON.stringify({id: data.id}), "utf8", (err)=> console.log(err));
                    res.render("real_estate", {user: data});
                }else res.end("SAI TAI KHOAN HOAC MAT KHAU");
            })
        })


        app.get("/", (req, res) => {
            fs.readFile("./userid.json", (err, data) => {
                const json = JSON.parse(data.toString("utf8"));
                UserModel.findOne({id: json.id}, (err, data) => {
                    if(err) {
                        res.end("Something went wrong!")
                    } else res.render("real_estate", {user: data});
                })
            });
        });

        app.post("/create_real/:id", upload.single("file"), (req, res, next) => {
            const reqData = req.body;
            base64Img.base64(req.file.path, function (err, data) {
                const real = {
                    name: reqData.txtName,
                    address: reqData.txtAddress,
                    contactNumber: reqData.txtContact,
                    description: reqData.txtDescription,
                    price: reqData.txtPrice,
                    area: reqData.txtArea,
                    city: reqData.slcCity,
                    type: reqData.chkLease != null ? reqData.chkLease : (reqData.chkSale != null ? reqData.chkSale : "LEASE"),
                    status: "AVAILABLE",
                    location: reqData.txtLocation.replace(/\s/g, ''),
                    img: data,
                    _idUser: req.params.id
                }
                RealModel.create(real).then(()=> fs.unlink(req.file.path));
            })
            res.redirect("/");
        })
    }
})

app.listen(8000); //Port of Server
module.exports = app;

