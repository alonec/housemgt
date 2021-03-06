package com.housemgt.service.sweeney;


import com.housemgt.dao.BuildingDao;
import com.housemgt.dao.ElementDao;
import com.housemgt.model.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingService {
    @Autowired
    BuildingDao buildingDao;
    @Autowired
    ElementDao elementDao;

    public void addBuilding(Building building){
        buildingDao.addBuilding(building);
    }

    public void deleteBuilding(String buildingNumber,String buildingName ){
        elementDao.deleteAllElement(buildingNumber);
        buildingDao.deleteBuilding(buildingNumber,buildingName);
    }

    public void updateBuilding(Building building){
        buildingDao.updateBuilding(building);
    }
}
