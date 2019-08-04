'use strict';
const realModel = require("../models/real");

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
}
