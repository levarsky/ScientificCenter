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
  tokenId;

  constructor(private formBuilder: FormBuilder,
              private registrationService : RegistrationService,
              private route: ActivatedRoute) { }

  ngOnInit() {

    this.route.queryParams.subscribe(params => {console.log(params)
      this.tokenId = params.tokenId;

      this.registrationService.checkUrl(this.tokenId).subscribe(data=>{

      },error=>{
        console.log(error.error.message);
      });

      ;},error => {
      console.log(error.erros.message);
    });

    this.paymentForm = this.formBuilder.group({
      cardNumber: [''],
      cvv: [''],
      cardHolderName: [''],
      expirationDate: ['']
    });
  }

  onSubmit(){
    console.log(this.paymentForm.value);
    //this.payment = this.paymentForm.value;
    this.registrationService.register(this.paymentForm.value,this.tokenId).subscribe(data=>{
      console.log(data);
    },error=>{
      console.log(error.erros.message);
    });
  }

}
