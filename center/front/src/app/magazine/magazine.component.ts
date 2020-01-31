import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {MagazineService} from "../services/magazine.service";
import {Magazine, ScentificArea, User, Cart} from "../model"
import {PaymentServiceService} from "../services/payment-service.service";

@Component({
  selector: 'app-magazine',
  templateUrl: './magazine.component.html',
  styleUrls: ['./magazine.component.css']
})
export class MagazineComponent implements OnInit {

  id : number;
  magazine = new Magazine(1,"","",false,0 );
  scientificAreas : ScentificArea[];
  redactors: User[];
  reviewers: User[];
  magazines : Magazine[];
  cart : Cart;

  constructor(private router: ActivatedRoute, private magazineService : MagazineService, private paymentService : PaymentServiceService) {
   this.router.queryParams.subscribe(data => {
            this.id =  data.id;
            console.log(this.id);
            this.magazineService.getById(this.id).subscribe(data => {
                  this.magazine = data;
                  console.log(this.magazine);
            });
            this.magazineService.getAreas(this.id).subscribe(data =>{
              this.scientificAreas = data;
            });
            this.magazineService.getRedactors(this.id).subscribe(data =>{
              this.redactors = data;
            });
            this.magazineService.getReviewers(this.id).subscribe(data =>{
              this.reviewers = data;
            });
      });
  }

  ngOnInit() {

  }

  subscribe(){
    this.paymentService.subscribe(this.magazine.price, this.magazine.price).subscribe(data=>{
         window.location.href=data.url;
    });
  }

}
