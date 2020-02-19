import { Component, OnInit } from '@angular/core';
import {UserService} from "../services/user.service"
import {Publication} from "../model"
import {PaymentServiceService} from "../services/payment-service.service";

@Component({
  selector: 'app-purchased',
  templateUrl: './purchased.component.html',
  styleUrls: ['./purchased.component.css']
})
export class PurchasedComponent implements OnInit {

  publications : Publication[];

  constructor(private userService : UserService,private paymentService:PaymentServiceService) {
    this.paymentService.checkTransaction().subscribe(data=>{

    });
    this.userService.purchased().subscribe(data => {
      this.publications = data;
      console.log("DOBIO")
      console.log(data);
    })
  }

  ngOnInit() {
  }

}
