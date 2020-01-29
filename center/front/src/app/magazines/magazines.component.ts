import { Component, OnInit } from '@angular/core';
import {MagazineService} from "../services/magazine.service";
import {Magazine} from "../model"

@Component({
  selector: 'app-magazines',
  templateUrl: './magazines.component.html',
  styleUrls: ['./magazines.component.css']
})
export class MagazinesComponent implements OnInit {

  magazines : Magazine[];

  constructor(private magazineService : MagazineService) { }

  ngOnInit() {
    this.magazineService.getAll().subscribe(data => {
      console.log("AAAAAAAAAAAAA");
      this.magazines = data;
    })
  }



}
