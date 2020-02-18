import { Component, OnInit } from '@angular/core';
import {FormGroup} from "@angular/forms";
import {AuthService} from "../security/auth.service";
import {RegisterClientService} from "../services/register-client.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  navBarForm: FormGroup;

  errorMessage = '';
  message = '';



  loginDiv = true;
  signupDiv = false;

  isAdmin = false;
  isEditor = false;
  isReviewer = false;


  isLoggedIn = false;


  payments = []

  isAdd:boolean;
  isView:boolean;

  name = 'Log in';

  username = '';
  title: any;

  constructor(private authService: AuthService,
              private registerService:RegisterClientService) {
  }

  ngOnInit() {

    this.registerService.getClient().subscribe(data=>{

    },error => {

      this.message = error.error.message;

      setTimeout(() => {
        this.message = '';

        window.location.href = 'home/client';
      }, 4000);

    });

    this.isView  = true;
    this.isAdd = false;

    this.initView()


      // this.authService.getGroups().subscribe(data=>{
      //     this.roles = data;
      //     console.log(data);
      //
      //   for (let role of this.adminRoles){
      //     if (this.roles.includes(role)){
      //       console.log(role);
      //       this.isAdmin = true;
      //     }
      //   };
      // });


  }

  initView(){

    this.isView  = true;
    this.isAdd = false;

    this.title = 'Client payment types'

    this.registerService.getClientPayments().subscribe(data=>{

      this.payments = data;

    })
  }

  initAdd(){

    this.isView  = false;
    this.isAdd = true;

    this.title = 'Add payment type'

    this.registerService.getAllPayments().subscribe(data=>{

      this.payments = data;

    })

  }

  onChange(event) {

    if (event.target.value == 'all') {
      this.initAdd();
    } else {
      this.initView();
    }

  }
  addPaymentType(paymentType) {

    this.registerService.registerPayment(paymentType.serviceName,'ADD').subscribe(data=>{
      window.location.href=data.redirectUrl;
    })

  }
}
