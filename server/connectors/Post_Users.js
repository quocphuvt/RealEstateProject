const express = require ('express');
const router = express.Router();
const UserModel = require('../models/user');
//Get back all the posts
router.get ('/', async (req,res)=>{
    try {
        const posts = await UserModel.find();
        res.json(posts);
    }catch{
        res.json ({message:err});
    }
});
//Submit a post
router.post('/',async (req,res)=>{
    const post =new UserModel({
        id: req.body.id,
        password: req.body.password,
        fullName: req.body.fullName,
        birthday: req.body.birthday,
        address: req.body.address,
        phoneNumber: req.body.phoneNumber,
        gender: req.body.gender
    });
    try{
  const savePost = await post.save();
    res.json(savePost);
    }catch(err){
        res.json ({message: err});
    }
});
    
module.exports =router;