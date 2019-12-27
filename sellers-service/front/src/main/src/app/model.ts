export class PaymentType{
  id: number;
  name: string;
  constructor(id:number, name:string){
    this.id = id;
    this.name = name;
  }
}

export class Request{
  amount: number;
  token: string;
  constructor(amount:number, token:string){
    this.amount = amount;
    this.token = token;
  }

}
