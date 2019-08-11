const mongoose = require("mongoose");
const Schema = mongoose.Schema;


const idRealSchema = new Schema({
    _idReal: String
})

const favoriteSchema = new Schema({
    _idUser: String,
    listReals: [
        idRealSchema
    ]
}); 

const favoriteModel = mongoose.model("favorites", favoriteSchema); //users: collection

module.exports = favoriteModel;