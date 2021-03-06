import { Component, OnInit } from '@angular/core';
import { PaymentType } from '../model'
import { RegisterClientService } from '../services/register-client.service'
import {ActivatedRoute} from "@angular/router";
import {FormBuilder, FormGroup} from "@angular/forms";
import {PaymentServiceService} from "../services/payment-service.service";
import {AuthService} from "../security/auth.service";
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  paymentTypes : PaymentType[];
  paymentForm: FormGroup;
  request: Request;
  username;
  token;
  constructor(private paymentService : PaymentServiceService,
              private route: ActivatedRoute,
              private authService:AuthService) { }

  ngOnInit() {





  }

  onSubmit(){





  }


  logout() {
    this.authService.logout();
    window.location.href = '';
  }
}
