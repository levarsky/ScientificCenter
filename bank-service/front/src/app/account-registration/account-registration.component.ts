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
  merchantForm: FormGroup;
  redirectUrl;
  failed: boolean;
  error: any;
  private viewMerch = false;
  add = false;

  constructor(private formBuilder: FormBuilder,
              private registrationService : RegistrationService,
              private route: ActivatedRoute) { }

  ngOnInit() {


    this.route.queryParams.subscribe(params => {console.log(params)
      this.tokenId = params.tokenId;

      this.registrationService.checkUrl(this.tokenId).subscribe(data=>{

        if(params.show){
          this.viewMerch = true;
          this.add=false;

          this.merchantForm.patchValue({
            merchantId: data.merchantId,
            merchantPassword: data.merchantPassword,
          });

        }else{
          this.add=true;
        }

      },error=>{
        console.log(error.error.message);
        //window.location.href = 'https://localhost:4201/home'
      });

      ;},error => {
      console.log(error.erros.message);
    });

    this.merchantForm = this.formBuilder.group({
      merchantId: {value:'',disabled:true},
      merchantPassword: {value:'',disabled:true}
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

      this.merchantForm.patchValue({
        merchantId: data.client.merchantId,
        merchantPassword: data.client.merchantPassword,
      });

      this.redirectUrl = data.redirectUrl;

      this.viewMerch = true;

    },error=>{
      console.log(error.error.message);

      this.error= error.error.message;
      this.failed = true;

      setTimeout(() => {
        this.error = '';
        this.failed = false;
      }, 4000);

    });
  }

  onCancel() {
    window.location.href = 'https://localhost:4201/home'
  }

  back() {
    window.location.href = this.redirectUrl;
  }
}
