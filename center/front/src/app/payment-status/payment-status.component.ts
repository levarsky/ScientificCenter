import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-payment-status',
  templateUrl: './payment-status.component.html',
  styleUrls: ['./payment-status.component.css']
})
export class PaymentStatusComponent implements OnInit {

  transactionId;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {

    this.route.queryParams.subscribe(data=>{
      this.transactionId = data.success;
      if(data.success)
        alert("Successful!");
      else if(data.fail)
        alert("Failed!");
      else if(data.error)
        alert("Error!");

    })
  }
}
