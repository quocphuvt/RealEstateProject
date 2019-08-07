'use strict';
const userModel = require("../models/user");

module.exports = class UserApi {
    constructor(res){
        this.res = res;
    }
    registerUser (user) {
        userModel.create(user).then((data) => {
            this.res.json(data)
        }).catch(err => console.log("Register err: " + err));
    }
    checkIdExisted (id) {
        return userModel.findOne({ id }, (err, data) => {
            return data;
        })
    }
    checkUserLogin (id, password) {
        userModel.findOne({ id: id }, (err, data) => {
            if (data) {
                if(data.password === password) {
                    this.res.json({"status": "success", "message": "Login success"});
                }
                else this.res.json({"status": "password failed", "message": "Uncorrect password"});
            }
            else {
                this.res.json({"status": "id failed","message": "Uncorrect ID"});
            }
        });
    }
    getAllUsers(){
        userModel.find({}, (err, data) => {
            return data;
        })
    }

    updateUserData(user){
        userModel.findOneAndUpdate({id: user.id}, user, (err,data) =>  {
                this.res.json(data); 
        })
    }
}
