'use strict';
const realModel = require("../models/real");
const mongoose = require("mongoose");
const ObjectId = mongoose.Types.ObjectId;

module.exports = class RealApi {
    constructor(res) {
        this.res = res;
    }
    createReal(real) {
        realModel.create(real).then((data) => {
            this.res.json(data)
        })
    }
    getAllReals() {
        realModel.find({}, (err, data) => {
            this.res.json(data)
        })
    }

    getAllRealsForLease() {
        realModel.find({ type: 'LEASE' }, (err, data) => {
            this.res.json(data);
        })
    }

    getAllRealsForSale() {
        realModel.find({ type: 'SALE' }, (err, data) => {
            this.res.json(data);
        })
    }

    getRealById(id) {
        realModel.findOne({ _id: new ObjectId(id) }, (err, data) => {
            this.res.json(data);
        })
    }

    getRealByLocation(location) {
        realModel.findOne({ location }, (err, data) => {
            this.res.json(data);
        })
    }

    countNumReal(id) {
        realModel.find({ status: "AVAILABLE", _idUser: id }, (err, data) => {
            this.res.json(data.length);
        })
    }


    sortByPrice() {
        realModel.find({}).sort({ price: -1 }).exec((err, allListings) => {
            this.res.json(allListings)
        })
    }

    filterRealByPrice(min, max) {
        realModel.find({ price: { $gte: min, $lte: max } }, (err, data) => {
            this.res.json(data)
        });
    }

    getHistoryByUserId(userId) {
        realModel.find({_idUser: userId}, (err, data) => {
            this.res.json(data);
        })
    }

    getAvailableReals() {
        realModel.find({status: "AVAILABLE"}, (err, data) => {
            this.res.json(data);
        })
    }

    deleteRealById(id) {
        realModel.deleteOne({_id: new ObjectId(id)}, (err, data) => {
            if(err) {
                this.res.json(0)
            } else this.res.json(1)
        })
    }

    updateReal(real) {
        realModel.findOneAndUpdate({_id: new ObjectId(real.id)}, real, (err, data) => {
            if(err) {
                this.res.json(0)
            } else this.res.json(1)
        })
    }

}
