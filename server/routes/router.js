const express = require('express');
const router = express.Router();
const fs = require("fs");
var multer = require("multer");
var path = require("path");
var upload = multer({ dest: "./images/upload" });
var base64Img = require('base64-img');
const UserService = require("../services/UserService");
const RealService = require("../services/RealService");

router.get("/login", (req, res) => {
    res.render("login");
})

router.get("/logout", (req, res) => {
    fs.writeFile("userid.json", JSON.stringify({ id: null }), "utf8", (err) => console.log(err));
    res.redirect("/login");
})

router.post("/login_result", async (req, res) => {
    const reqData = req.body;
    const id = reqData.txtUsername;
    const password = reqData.txtPassword;
    const user = await UserService.getUser(id);
    if (user != null && user.password === password) {
        fs.writeFile("userid.json", JSON.stringify({ id: user.id }), "utf8", (err) => console.log(err));
        res.render("real_estate", { user });
    } else res.end("SAI TAI KHOAN HOAC MAT KHAU");
})


router.get("/", (req, res) => {
    fs.readFile("./userid.json", async (err, data) => {
        const json = JSON.parse(data.toString("utf8"));
        const user = await UserService.getUser(json.id);
        if (!user) {
            res.end("Something went wrong!")
        } else res.render("real_estate", { user });
    });
});

router.post("/create_real/:id", upload.single("file"), (req, res, next) => {
    const reqData = req.body;
    base64Img.base64(req.file.path, async (err, data) => {
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
        const createdReal = await RealService.createReal(real);
        const removedImgFile = await fs.unlink(req.file.path, err =>{
            console.log("unlink: "+err);
        });
    })
    res.redirect("/");
})

module.exports = router;