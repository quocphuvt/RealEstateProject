'use strict';
const userModel = require("../models/user");

module.exports = class UserApi {
    constructor(res){
        this.res = res;
    }
    registerUser (user) {
        userModel.create(user).then(() => {
            this.res.json({"status": "success", "user_id": user.id})
        }).catch(err => console.log("Register err: " + err));
    }
    checkIdExisted (id) {
        return userModel.find({ id }, (err, data) => {
            if (data != null) {
                return 1;
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
