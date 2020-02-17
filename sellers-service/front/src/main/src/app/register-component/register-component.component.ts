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

  message='';

  submit = false
  edit = false

  client;


  constructor(private formBuilder:FormBuilder, private registerService : RegisterClientService) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
          clientName:[''],
          errorUrl:[''],
          failedUrl:[''],
          successUrl:[''],
          clientId:[''],
          clientSecret:['']
    });

    this.registerService.getClient().subscribe(data=>{

      this.submit = false;
      this.edit = true;

      this.client = data;

      this.form.patchValue({
        clientName:data.clientName,
        errorUrl:data.errorUrl,
        failedUrl:data.failedUrl,
        successUrl:data.successUrl,
        clientId:data.clientId
      })

    },error => {

      this.submit = true;
      this.edit = false;

    });
  }

  onSubmit(){
    this.registerService.reg(this.form.value).subscribe( data =>{
      console.log(data)

      this.form.patchValue({clientId:data.clientId,clientSecret:data.clientSecret})

      this.submit = false;
      this.edit = true;

      this.message = 'Client password will be visible until refresh of the page , save it!';

      setTimeout(() => {
        this.message = '';


      }, 9000);

    });
  }

  onEdit() {

    console.log(this.form.value)

    this.registerService.edit(this.form.value).subscribe(data=>{
      this.ngOnInit();
    })
  }

  generate() {
    this.registerService.generate().subscribe(data=>{
      this.form.patchValue({clientId:data.clientId,clientSecret:data.clientSecret})

      this.message = 'Client password will be visible until refresh of the page , save it!';

      setTimeout(() => {
        this.message = '';


      }, 9000);

    })
  }
}
