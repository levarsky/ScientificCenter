import { Component, OnInit } from '@angular/core';
import { FormBuilder,FormGroup } from '@angular/forms';

@Component({
  selector: 'app-transfer',
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.css']
})
export class TransferComponent implements OnInit {

  form: FormGroup;

  constructor(private formBuilder:FormBuilder) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
          idSender:[''],
          idReceiver:[''],
          amount:['']
    });
  }

}
