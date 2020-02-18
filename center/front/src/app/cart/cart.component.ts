import { Component, OnInit } from '@angular/core';
import {Magazine, ScentificArea, User, Cart} from "../model"
import {PaymentServiceService} from "../services/payment-service.service";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  cart : Cart;

  sum : number;
  constructor(private paymentService : PaymentServiceService) { }

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
      this.cart.products = this.cart.products.filter(product => product.id != id );
      localStorage.setItem("cart",JSON.stringify(this.cart));
       this.sum = 0;
       this.cart = JSON.parse(localStorage.getItem("cart"));
            let list = [];
            for (let product of this.cart.products) {
                    this.sum += product.price;
       }
  }

  checkout(){
    if(this.sum == 0)
      return;
    if(localStorage.getItem("cart") == null){
      alert("Cart is empty!");
    }else{
      this.cart = JSON.parse(localStorage.getItem("cart"));
      let list = [];
      for (let product of this.cart.products) {
              list.push(product.id);
      }
      this.paymentService.pay(this.sum, list).subscribe(data=>{
          window.location.href=data.url;
      });
       this.cart.products = [];
       this.sum = 0;
       localStorage.setItem("cart",JSON.stringify(this.cart));
    }

  }

}
