import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Payment} from "../model"
import { PaymentService} from '../services/payment.service'

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

  paymentForm: FormGroup;
  payment : Payment;

  constructor(private formBuilder: FormBuilder, private paymentService : PaymentService) { }

  ngOnInit() {
      this.paymentForm = this.formBuilder.group({
              pan: [''],
              securityCode: [''],
              cardHolderName: [''],
              expirationDate: ['']
       });
  }

  onSubmit(){
    this.payment = this.paymentForm.value;
    this.paymentService.pay(this.payment).subscribe(data => {
      console.log("Stigao response");
    })
  }

}
