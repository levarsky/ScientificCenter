import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {RegistrationService} from "../services/registration.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  form: FormGroup;
  tokenId;

  constructor(private formBuilder:FormBuilder,
              private registrationService : RegistrationService,
              private route: ActivatedRoute){ }

  ngOnInit() {

    this.route.queryParams.subscribe(params => {console.log(params)
      this.tokenId = params.tokenId;

      this.registrationService.checkUrl(this.tokenId).subscribe(data=>{

      },error=>{
        console.log(error.error.message);
        window.location.href = 'https://localhost:4201/home'
      });

      ;},error => {
      console.log(error.erros.message);
    });

    this.form = this.formBuilder.group({
      paypalClientId:[''],
      paypalSecret:['']
    });
  }

  onSubmit(){
    this.registrationService.register(this.form.value,this.tokenId).subscribe(data=>{
      console.log(data);
      window.location.href = data.redirectUrl
    },error=>{
      console.log(error.erros.message);
    });
  }


}
