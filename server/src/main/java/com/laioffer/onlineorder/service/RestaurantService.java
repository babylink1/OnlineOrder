
package com.laioffer.onlineorder.service;
import com.laioffer.onlineorder.entity.MenuItemEntity;
import com.laioffer.onlineorder.entity.RestaurantEntity;
import com.laioffer.onlineorder.model.MenuItemDto;
import com.laioffer.onlineorder.model.RestaurantDto;
import com.laioffer.onlineorder.repository.MenuItemRepository;
import com.laioffer.onlineorder.repository.RestaurantRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class RestaurantService {


    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;


    public RestaurantService(
            RestaurantRepository restaurantRepository,
            MenuItemRepository menuItemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }

@Cacheable("restaurants")
    public List<RestaurantDto> getRestaurants() {
        List<RestaurantEntity> restaurantEntities = restaurantRepository.findAll();//写数据
        List<MenuItemEntity> menuItemEntities = menuItemRepository.findAll();
        Map<Long, List<MenuItemDto>> groupedMenuItems = new HashMap<>();//构造DTO
        for (MenuItemEntity menuItemEntity : menuItemEntities) {//cankuan ID,

            //下面这行是排除了空的情况后，K 取值，对应建立一个LIST
            List<MenuItemDto> group = groupedMenuItems.computeIfAbsent(menuItemEntity.restaurantId(), k -> new ArrayList<>());//computeIfAbsent,map null ,

            MenuItemDto menuItemDto = new MenuItemDto(menuItemEntity);
            group.add(menuItemDto);
        }
        List<RestaurantDto> results = new ArrayList<>();
        for (RestaurantEntity restaurantEntity : restaurantEntities) {
            RestaurantDto restaurantDto = new RestaurantDto(restaurantEntity, groupedMenuItems.get(restaurantEntity.id()));
            results.add(restaurantDto);
        }
        return results;
    }
}
