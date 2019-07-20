const userModel = require("../models/user");

const UserApi = {
    registerUser: (user) => {
        userModel.create(user).then(() => console.log("User was created!")).catch(err => console.log("Register err: " + err));
    },
    checkIdExisted: (id) => {
        userModel.find({ id }, (err, data) => {
            if (data) {
                console.log("ID was exist!");
            }
        });
    },
    registerUser: (user) => {
        userModel.create(user).then(() => console.log("User was created!")).catch(err => console.log("Register err: " + err));
    },
    checkIdExisted: (id) => {
        userModel.find({ id }, (err, data) => {
            if (data) {
                console.log("ID was exist!");
            }
        })
    },
    checkUserLogin: (id, password) => {
        userModel.find({ id: id, password: password }, (err, data) => {
            if (data) {
                console.log("Login success!");
            }
            else {
                console.log("Login failed!")
            }
        });
    },
    getAllUser: () => {
        userModel.find({}, (err, data) => {
            return data;
        })
    }
}

module.exports = UserApi;