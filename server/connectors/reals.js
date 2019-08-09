'use strict';
const realModel = require("../models/real");

module.exports = class RealApi {
    constructor(res) {
        this.res = res;
    }
    createReal(real) {
        realModel.create(real).then((data) => {
            this.res.json(data)
        })
    }
    static getAllReals() {
        realModel.find({}, (err, data) => {
            this.res.json(data)
        })
    }
    sortByPrice() {
        // this.res.json(data)
        realModel.find({}).sort({ price: -1 }).exec(function (err, allListings) {
            console.log(err);
           this.res.json(allListings)
        })
    }
}
