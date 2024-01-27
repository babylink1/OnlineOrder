package com.laioffer.onlineorder.controller;


import com.laioffer.onlineorder.model.AddToCartBody;
import com.laioffer.onlineorder.model.CartDto;
import com.laioffer.onlineorder.service.CartService;
import org.springframework.web.bind.annotation.*;


@RestController
public class CartController {


    private final CartService cartService;


    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

//    这是get
    @GetMapping("/cart")
    public CartDto getCart() {
        return cartService.getCart(1L);//zhun对1号用户

    }

    // 这是post
    @PostMapping("/cart")
    public void addToCart(@RequestBody AddToCartBody body) {//改变状态的操作 用 post 操作
        cartService.addMenuItemToCart(1L, body.menuId());
//        return cartService.getCart(1L);//zhun对1号用户 这儿需要返回请求结果，你代码肯定没有写完

    }


    @PostMapping("/cart/checkout")
    public void checkout() {
        cartService.clearCart(1L);
    }
}
