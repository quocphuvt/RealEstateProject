'use strict';
const realModel = require("../models/real");
const mongoose = require("mongoose");
const ObjectId = mongoose.Types.ObjectId;

module.exports = class RealApi {
    constructor(res){
        this.res = res;
    }
    createReal (real) {
        realModel.create(real).then((data)=>{
            this.res.json(data)
        })
    }
    getAllReals (){
        realModel.find({}, (err,data)=>{
            this.res.json(data)
        })
    }

    getAllRealsForLease(){
        realModel.find({type: 'LEASE'}, (err, data)=>{
            this.res.json(data);
        })
    }

    getAllRealsForSale(){
        realModel.find({type: 'SALE'}, (err, data)=>{
            this.res.json(data);
        })
    }

    getRealById(id){
        realModel.findOne({_id: new ObjectId(id)}, (err, data) => {
            this.res.json(data);
        })
    }

    getRealByLocation(location){
        realModel.findOne({location}, (err, data) => {
            this.res.json(data);
        })
    }
}
