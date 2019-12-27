export class Payment{
  pan : string;
  securityCode : string;
  cardHolderName : string;
  expirationDate : Date;

  constructor(pan : string, securityCode : string, cardHolderName : string, expirationDate: Date){
    this.pan = pan;
    this.securityCode = securityCode;
    this.cardHolderName = cardHolderName;
    this.expirationDate = expirationDate;
  }
}
