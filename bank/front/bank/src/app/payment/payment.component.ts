import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Payment} from "../model"
import { PaymentService} from '../services/payment.service'
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

  paymentForm: FormGroup;
  payment : Payment;
  paymentId;

  constructor(private formBuilder: FormBuilder,
              private paymentService : PaymentService,
              private route: ActivatedRoute) { }

  ngOnInit() {

    this.route.queryParams.subscribe(params => {console.log(params)
        this.paymentId = params.paymentId;
    ;})

      this.paymentForm = this.formBuilder.group({
              pan: [''],
              securityCode: [''],
              cardHolderName: [''],
              expirationDate: ['']
       });
  }

  onSubmit(){
    this.payment = this.paymentForm.value;
    this.paymentService.pay(this.payment,this.paymentId).subscribe(data => {
      console.log(data);
      window.location.href = data.redirectUrl;
    })
  }

}
