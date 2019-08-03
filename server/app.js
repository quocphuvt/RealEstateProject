
const express = require('express');
const app = express();
const mongoose = require('mongoose');
const bodyParser =require ('body-parser');

app.use(bodyParser.json());

//import Routes
const postRouter =require ('./connectors/Post_Users');
app.use('/register', postRouter);


//Routes
app.get('/', (req,res)=>{
    res.send('');
});

app.get('/register',(req,res)=>{
    res.send('');
});
//Connect to DB
mongoose.connect('mongodb://localhost/RealEstateManager', { useNewUrlParser: true },()=>{
    console.log('Connect to DB!');
});
//Listen server
app.listen(8000);

