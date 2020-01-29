import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {MagazineService} from "../services/magazine.service";
import {PublicationService} from "../services/publication.service";
import {Publication} from "../model"
import {Magazine, ScentificArea, User, Cart} from "../model"

@Component({
  selector: 'app-publications',
  templateUrl: './publications.component.html',
  styleUrls: ['./publications.component.css']
})
export class PublicationsComponent implements OnInit {

  id : number;
  publications : Publication[];
  cart : Cart;
  publicationToAdd : Publication;

  constructor(private router: ActivatedRoute, private publicationService : PublicationService, private magazineService : MagazineService) {
     this.router.queryParams.subscribe(data => {
        this.id =  data.id;
        this.magazineService.getPublications(this.id).subscribe(data => {
          this.publications = data;
        })
     })
  }

  ngOnInit() {
  }

  add(id :number){
      this.publicationService.getById(id).subscribe(data => {
              this.publicationToAdd = data;
              if(localStorage.getItem("cart") != null){
                this.cart = JSON.parse(localStorage.getItem("cart"));
                for (let product of this.cart.products) {
                    if(product.id == this.publicationToAdd.id){
                      alert("Publication is already in cart!")
                      return;
                    }
                }
                this.cart.products.push(this.publicationToAdd);
                localStorage.setItem("cart",JSON.stringify(this.cart));
                alert("Added!")
              }else{
                this.cart = new Cart();
                this.cart.products = [];
                this.cart.products.push(this.publicationToAdd);
                localStorage.setItem("cart",JSON.stringify(this.cart));
                alert("Added!")
              }
           })

  }

}
