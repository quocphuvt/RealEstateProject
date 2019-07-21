'use strict';
const userModel = require("../models/user");

module.exports = class UserApi {
    constructor(res){
        this.res = res;
    }
    registerUser (user) {
        userModel.create(user).then(() => {
            this.res.json("OKE NE")
            console.log("User was created!")
        }).catch(err => console.log("Register err: " + err));
    }
    checkIdExisted (id) {
        userModel.find({ id }, (err, data) => {
            if (data != null) {
                console.log("ID was exist!");
            }
        })
    }
    checkUserLogin (id, password) {
        userModel.find({ id: id, password: password }, (err, data) => {
            if (data) {
                console.log(data)
                this.res.json(data);
                console.log("Login success!");
            }
            else {
                console.log("Login failed!")
            }
        });
    }
    getAllUsers(){
        userModel.find({}, (err, data) => {
            return data;
        })
    }
}
