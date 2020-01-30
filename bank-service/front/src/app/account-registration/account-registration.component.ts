import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {RegistrationService} from "../services/registration.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-account-registration',
  templateUrl: './account-registration.component.html',
  styleUrls: ['./account-registration.component.css']
})
export class AccountRegistrationComponent implements OnInit {

  paymentForm: FormGroup;
  payment ;
  paymentId;

  constructor(private formBuilder: FormBuilder,
              private registrationService : RegistrationService,
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
