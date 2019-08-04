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
});

const realModel = mongoose.model("realEstates", realSchema); 

module.exports = realModel;