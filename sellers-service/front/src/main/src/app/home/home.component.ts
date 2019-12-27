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
  constructor(private paymentService : PaymentServiceService,
              private route: ActivatedRoute,
              private formBuilder: FormBuilder) { }

  ngOnInit() {

    this.paymentForm = this.formBuilder.group({
      amount:0,
      payment: "",
    });

    this.route.queryParams.subscribe(params => {console.log(params);

        this.paymentService.getPaymentTypes(params.token).subscribe(data=>{
          this.paymentTypes = data;
        });

        this.paymentService.getTokenRequest(params.token).subscribe(data=>{
          this.request = data;
          this.paymentForm.patchValue({amount: data.amount });
        });

    });



  }

  onSubmit(){


  }


}
