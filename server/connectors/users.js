const userModel = require("../models/user");

class UserApi {
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
            if (data) {
                console.log("ID was exist!");
            }   
        });
    }
    // registerUser (user) {
    //     userModel.create(user).then(() => console.log("User was created!")).catch(err => console.log("Register err: " + err));
    // }
    checkIdExisted (id) {
        userModel.find({ id }, (err, data) => {
            if (data) {
                console.log("ID was exist!");
            }
        })
    }
    checkUserLogin (id, password) {
        userModel.find({ id: id, password: password }, (err, data) => {
            if (data) {
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

module.exports = UserApi;