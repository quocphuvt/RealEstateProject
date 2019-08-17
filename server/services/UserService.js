'use strict';
const UserModel = require("../models/User");
const FavoriteModel = require("../models/Favorite");

exports.registerUser = async (user) => {
    const registeredUser = await UserModel.create(user);
    return registeredUser;
}
exports.getUser = async (id) => {
    const user = await UserModel.findOne({ id }).exec();
    return user;
}

exports.getAllUsers = async () => {
    const users = await UserModel.find().exec();
    return users;
}

exports.updateUser = async (user) => {
    const updatedUser = await UserModel.findOneAndUpdate({ id: user.id }, user, { new: true });
    return updatedUser;
};

exports.createFavoriteReal = async (favoriteReal) => {
    const createdFavoriteReal = await FavoriteModel.create(favoriteReal);
    return createdFavoriteReal;
}

exports.updateFavoriteReal = async (idUser, newData) => {
    const updatedFavoriteReal = await FavoriteModel.updateOne({ _idUser: idUser}, newData, {new: true}).exec();
    return updatedFavoriteReal;
}

exports.getFavoriteReal = async (favoriteData) => {
    const favoritedReal = await FavoriteModel.findOne({ _idUser: favoriteData._idUser }).exec();
    return favoritedReal;
}

exports.getFavoritedReals = async (id) => {
    const favoritedReals = await FavoriteModel.findOne({_idUser: id}).exec();
    return favoritedReals;
}
