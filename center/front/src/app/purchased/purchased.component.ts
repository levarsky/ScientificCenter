import { Component, OnInit } from '@angular/core';
import {UserService} from "../services/user.service"
import {Publication} from "../model"

@Component({
  selector: 'app-purchased',
  templateUrl: './purchased.component.html',
  styleUrls: ['./purchased.component.css']
})
export class PurchasedComponent implements OnInit {

  publications : Publication[];

  constructor(private userService : UserService) {
    this.userService.purchased().subscribe(data => {
      this.publications = data;
    })
  }

  ngOnInit() {
  }

}
