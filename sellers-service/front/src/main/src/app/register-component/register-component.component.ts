import { Component, OnInit } from '@angular/core';
import { FormBuilder,FormGroup, ReactiveFormsModule } from '@angular/forms';
import {RegisterClientService} from "../services/register-client.service"
import {PaymentType} from "../model";

@Component({
  selector: 'app-register-component',
  templateUrl: './register-component.component.html',
  styleUrls: ['./register-component.component.css']
})
export class RegisterComponentComponent implements OnInit {

  form: FormGroup;
  paymentTypes : PaymentType[];


  constructor(private formBuilder:FormBuilder, private registerClientService : RegisterClientService) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
          clientName:[''],
          clientId:[''],
          clientSecret:['']
    });
  }

  onSubmit(){
    this.registerClientService.reg(this.form.get('clientName').value).subscribe( data =>{
      console.log(data)
    });
  }

}
