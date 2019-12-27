import { Component, OnInit } from '@angular/core';
import { PaymentType } from '../model'
import { RegisterClientService } from '../services/register-client.service'
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  types : PaymentType[];
  constructor(private service : RegisterClientService) { }

  ngOnInit() {
    this.service.getPaymentTypes().subscribe(data =>{
      this.types = data;
    })
  }


}
