import { Component, OnInit } from '@angular/core';
import {MatTabsModule} from '@angular/material/tabs';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {PaymentServiceService} from "../services/payment-service.service";

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

  paymentForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private paymentService : PaymentServiceService) { }

  ngOnInit() {
   this.paymentForm = this.formBuilder.group({
        price: ['', [Validators.required, Validators.pattern('[0-9]*')]],
      });
  }

  onSubmit(){

    this.paymentService.pay(this.paymentForm.value.price).subscribe(data=>{
      window.location.href=data.url;

    });


  }


}
