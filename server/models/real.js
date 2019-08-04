const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const realSchema = new Schema({
    id: String,
    name: String,
    address: String,
    contactNumber: String,
    description: String,
    price: Number,
    area: Number,
    city: String,
    type: String,
    status: String,
    _idUser: String
});

const realModel = mongoose.model("realEstates", realSchema); 

module.exports = realModel;