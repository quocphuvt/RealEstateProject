'use strict';
const RealModel = require("../models/Real");
const mongoose = require("mongoose");
const ObjectId = mongoose.Types.ObjectId;

exports.createReal = async (real) => {
    const createdReal = await RealModel.create(real);
    return createdReal;
}
exports.getRealList = async () => {
    const reals = await RealModel.find({}).exec();
    return reals
}

exports.getRealListByCity = async (cityName) => {
    const reals = await RealModel.find({city: cityName}).exec();
    return reals
}

exports.getRealListForLease = async () => {
    const realsForLease = await RealModel.find({ type: 'LEASE' }).exec();
    return realsForLease;
}

exports.getRealListForSale = async () => {
    const realsForSale = await RealModel.find({ type: 'SALE' }).exec();
    return realsForSale;
}

exports.getReal = async (id) => {
    const real = await RealModel.findById({_id: id}).exec();
    return real;
}

exports.getRealByLocation = async (location) => {
    const real = await RealModel.findOne({ location }).exec();
    return real;
}

exports.getAvailableRealsOfUser = async (id) => {
    const reals = await RealModel.find({ status: "AVAILABLE", _idUser: id }).exec();
    return reals;
}

exports.sortByPrice = async () => {
    const reals = await RealModel.find({}).sort({ price: -1 }).exec();
    return reals;
}

exports.filterRealByPrice = async (min, max) => {
    const reals = await RealModel.find({ price: { $gte: min, $lte: max } }).exec();
    return reals;
}

exports.getPostHistory = async (userId) => {
    const reals = await RealModel.find({ _idUser: userId }).exec();
    return reals;
}

exports.getAllAvailableReals = async (userId) => {
    const reals = RealModel.find({ _idUser: userId, status: "AVAILABLE" }).exec();
    return reals;
}

exports.deleteReal = async (id) => {
    const deletedReal = await  RealModel.deleteOne({ _id: new ObjectId(id) }).exec();
    return deletedReal;
}

exports.updateReal = async (real) => {
    const updatedReal = await RealModel.findOneAndUpdate({ _id: new ObjectId(real.id) }, real, {new: true}).exec();
    return updatedReal;
}
