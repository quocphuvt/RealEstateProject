const express = require('express');
const router = express.Router();
const UserService = require("../services/UserService");

router.post("/register", async (request, response) => {
    const userData = request.body;
    const newUser = {
        id: userData.id,
        password: userData.password,
        fullName: userData.fullName,
        birthday: userData.birthday,
        city: userData.city,
        phoneNumber: userData.phoneNumber,
        gender: userData.gender,
        avatar: userData.avatar
    }
    const existedUser = await UserService.getUser(userData.id);
    if (existedUser != null) {
        return response.json({ status: 0, message: "Id was existed" });
    }
    else {
        const registeredUser = await UserService.registerUser(newUser);
        if (registeredUser) {
            return response.json({ status: 1, message: "Account has been created" });
        }
    };

});

router.get("/signIn", async (request, response) => {
    const userData = request.query;
    const id = userData.id;
    const password = userData.password;
    const user = await UserService.getUser(id);
    if (user) {
        if (user.password === password) {
            return response.json({ status: 1, message: "Sign In successfully"});
        }
        return response.json({ status: -1, message: "Uncorrect password" });
    }
    else {
        return response.json({ status: -2, message: "Uncorrect ID" });
    }
});

router.get("/:id", async (request, response) => {
    const idUser = request.params.id;
    const user = await UserService.getUser(idUser);
    if (user) {
        return response.json({ status: 1, userModel: user });
    }
    else {
        return response.json({ status: 0 });
    }
})

router.put("/", async (request, response) => {
    const userData = request.body;
    const updatedUser = await UserService.updateUser(userData);
    if (updatedUser) {
        return response.json({ status: 1, message: "User has been updated"});
    }
    return response.json({ status: 0, message: "Can not update your profile"});
});

router.post("/saveFavorite", async (request, response) => {
    const reqData = request.body;
    const savedFavorite = await UserService.getFavoriteReal(reqData);
    if (!savedFavorite) {
        UserService.createFavoriteReal(reqData);
    } else {
        UserService.updateFavoriteReal(reqData._idUser, reqData);
    }
})

router.get("/favoriteList", async (request, response) => {
    const favoriteList = await UserService.getFavoritedReals();
    if(favoriteList) {
        return response.json({ status: 1, favorites: favoriteList});
    }
    return response.json({ status: 0 });
})

module.exports = router;