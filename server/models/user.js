const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const userSchema = new Schema({
    id: String,
    password: String,
    fullName: String,
    birthday: String,
    city: String,
    phoneNumber: String,
    gender: Number,
    avatar: String
}); 

const userModel = mongoose.model("users", userSchema); //users: collection

module.exports = userModel;