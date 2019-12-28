export class PaymentType{
  id: number;
  name: string;
  constructor(id:number, name:string){
    this.id = id;
    this.name = name;
  }
}

export class Request{
  private _amount: number;
  private _token: string;
  constructor(amount:number, token:string){
    this._amount = amount;
    this._token = token;
  }


  get amount(): number {
    return this._amount;
  }

  set amount(value: number) {
    this._amount = value;
  }

  get token(): string {
    return this._token;
  }

  set token(value: string) {
    this._token = value;
  }
}
