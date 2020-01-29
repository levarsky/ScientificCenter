import { Component, OnInit } from '@angular/core';
import { FormBuilder,FormGroup } from '@angular/forms';
import { TransferServiceService } from '../service/transfer-service.service';
import { TransactionDao } from '../model';

@Component({
  selector: 'app-transfer',
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.css']
})
export class TransferComponent implements OnInit {

  form: FormGroup;
  transaction: TransactionDao;

  constructor(private formBuilder:FormBuilder, private transferService: TransferServiceService) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
          seedSender:[''],
          seedReceiver:[''],
          amount:['']
    });
  }

  onSubmit(){
    this.transaction = this.form.value;
    this.transferService.transfer(this.transaction).subscribe( data =>{
      if(data.isSuccessful == true){
        alert("Transaction is successful");
      }
      else{
        alert("Transaction is failed. Insufficient money.");
      }
    });
  }

}
