const mongoose = require("mongoose");
const Schema = mongoose.Schema;


const idRealSchema = new Schema({
    _idReal: String,
    isLike: Boolean
})

const favoriteSchema = new Schema({
    _idUser: String,
    favoritedReals: [
        idRealSchema
    ]
}); 

const favoriteModel = mongoose.model("favorites", favoriteSchema); //users: collection

module.exports = favoriteModel;