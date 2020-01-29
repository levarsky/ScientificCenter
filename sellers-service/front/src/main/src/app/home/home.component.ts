import { Component, OnInit } from '@angular/core';
import { PaymentType } from '../model'
import { RegisterClientService } from '../services/register-client.service'
import {ActivatedRoute} from "@angular/router";
import {FormBuilder, FormGroup} from "@angular/forms";
import {PaymentServiceService} from "../services/payment-service.service";
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  paymentTypes : PaymentType[];
  paymentForm: FormGroup;
  request: Request;
  token;
  constructor(private paymentService : PaymentServiceService,
              private route: ActivatedRoute,
              private formBuilder: FormBuilder) { }

  ngOnInit() {

    this.paymentForm = this.formBuilder.group({
      amount:[{value:0,disabled: true}],
      payment: "",
    });

    this.route.queryParams.subscribe(params => {console.log(params);

        this.paymentService.getPaymentTypes(params.token).subscribe(data=>{
          this.paymentTypes = data;
        });

        this.paymentService.getTokenRequest(params.token).subscribe(data=>{
          this.request = data;
          this.token = data.token;
          this.paymentForm.patchValue({amount: data.amount });
        });

    });



  }

  onSubmit(){

    console.log(this.paymentForm.get("payment").value);

    this.paymentService.sendRequest(this.token,this.paymentForm.get("payment").value).subscribe(data=>{
      console.log(data);
      window.location.href=data.url;
      }
    );

  }


}
