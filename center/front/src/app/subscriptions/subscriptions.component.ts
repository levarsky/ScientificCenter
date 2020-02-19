import { Component, OnInit } from '@angular/core';
import {UserService} from "../services/user.service"
import {Magazine} from "../model"
import {PaymentServiceService} from "../services/payment-service.service";

@Component({
  selector: 'app-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.css']
})
export class SubscriptionsComponent implements OnInit {

  magazines : Magazine[];
  constructor(private userService : UserService,private paymentService:PaymentServiceService) {

    this.paymentService.checkTransaction().subscribe(data=>{

    });

    this.userService.subscriptions().subscribe(data => {
      this.magazines = data;
    })
  }

  ngOnInit() {
  }

}
