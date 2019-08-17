const express = require('express');
const router = express.Router();
const RealService = require("../services/RealService");

router.post("/create", async (request, response, next) => {
    const realData = request.body;
    const createdReal = await RealService.createReal(realData);
    if (createdReal) {
        return response.json({ status: 1, message: "Your post has been upload"});
    }
    return response.json({ status: 0, message: "Uploading unsuccessfully"});
})

router.get("/:id", async (request, response) => {
    const id = request.params.id;
    const real = await RealService.getReal(id);
    if (real) {
       return response.json({ status: 1, realEstate: real });
    }
    response.json({ status: 0 });
})

router.get("/realList/all", async (request, response, next) => {
    const reals = await RealService.getRealList();
    return response.json({ status: 1, realList: reals });
})

router.get("/realList/sort", async (request, response, next) => {
    const sortedReals = await RealService.sortByPrice();
    if (sortedReals) {
        return response.json({status: 1, realList: sortedReals})
    } 
    return response.json({ status: 0})
});

router.get("/realList/filter", async (request, response, next) => {
    const minPrice = request.query.minPrice;
    const maxPrice = request.query.maxPrice;
    const filteredReals = await RealService.filterRealByPrice(minPrice, maxPrice);
    if (filteredReals) {
        return response.json({ status: 1, realList: filteredReals});
    }
    return response.json({ status: 0 });
});

router.get("/realList/lease", async (request, response) => {
    const reals = await RealService.getRealListForLease();
    if(reals) {
        return response.json({ status: 1, realList: reals});
    }
    return response.json({ status: 0 });
})

router.get("/realList/sale", async (request, response) => {
    const reals = await RealService.getRealListForSale();
    if(reals) {
        return response.json({ status: 1, realList: reals});
    }
    return response.json({ status: 0 });
})



router.get("/location/:location", async (request, response) => {
    const location = request.params.location
    const real = await RealService.getRealByLocation(location);
    if (real) {
        return response.json({ status: 1, realEstate: real });
    }
    return response.json({ status: 0, message: "Location was undefined" });
})

router.get("/:city/realList", async (request, response) => {
    const cityName = request.params.city;
    const reals = await RealService.getRealListByCity(cityName);
    if (reals) {
        return response.json({ status: 1, realList: reals });
    }
    return response.json({ status: 0 })
})

router.get("/:userId/totalReals", async (request, response) => {
    const userId = request.params.userId
    const reals = await RealService.getAvailableRealsOfUser(userId);
    if (reals) {
        return response.json({ status: 1, realList: reals });
    }
    else {
        return response.json({ status: 0, message: "Please check your connection" });
    }
})

router.get("/:userId/history", async (request, response) => {
    const userId = request.params.userId;
    const reals = await RealService.getPostHistory(userId);
    if (reals) {
        return response.json({ status: 1, realList: reals});
    }
    return response.json({ status: 0, message: "Invalid user id" });
})

router.get("/:userId/realList/available", async (request, response) => {
    const userId = request.params.userId;
    const reals = await RealService.getAllAvailableReals(userId);
    if (reals) {
        return response.json({ status: 1, realList: reals}); 
    }
    return response.json({ status: 0 });
})

router.delete("/:realId", async (request, response) => {
    const realId = request.params.realId;
    const deletedReal = await RealService.deleteReal(realId);
    if (deletedReal) {
       return response.json({ status: 1, message: "Real has been deleted"});
    }
    return response.json({ status: 0, message: "Invalid real id"});
})

router.put("/", async (request, response) => {
    const realData = request.body;
    const newReal = {
        id: realData._id,
        name: realData.name,
        address: realData.address,
        contactNumber: realData.contactNumber,
        description: realData.description,
        price: realData.price,
        area: realData.area,
        city: realData.city,
        type: realData.type,
        status: "AVAILABLE",
        location: realData.location,
        img: realData.img,
        _idUser: realData._idUser,
    }
    const updatedReal = await RealService.updateReal(newReal);
    if (updatedReal) {
        return response.json({ status: 1, message: "Your post has been updated"});
    }
    return response.json({ status: 0, message: "Can not update yout post. Check again"});
})

module.exports = router;