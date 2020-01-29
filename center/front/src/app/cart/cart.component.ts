import { Component, OnInit } from '@angular/core';
import {Magazine, ScentificArea, User, Cart} from "../model"

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  cart : Cart;

  sum : number;
  constructor() { }

  ngOnInit() {
     if(localStorage.getItem("cart") != null){
          this.cart = JSON.parse(localStorage.getItem("cart"));
     }else{
          this.cart = new Cart();
          this.cart.products = [];
     }
     var sumPom = 0.0;
     for (let product of this.cart.products) {
        sumPom += Number(product.price);

     }
     console.log(sumPom);
     this.sum = sumPom;
  }

  remove(id : number) {
      this.cart.products = this.cart.products.filter(magazine => magazine.id != id );
      localStorage.setItem("cart",JSON.stringify(this.cart));
  }

}
