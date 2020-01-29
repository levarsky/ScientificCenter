export class Magazine {
  id : number;
  name : string;
  issn : string;
  readerPays: boolean;
  price: number;

  constructor(id : number, name : string, issn : string, readerPays : boolean, price : number) {
    this.id = id;
    this.name = name;
    this.issn = issn;
    this.readerPays = readerPays;
    this.price = price;
  }
}

export class ScentificArea{
  id :number;
  name: string;

  constructor(id:  number, name: string) {
    this.id = id;
    this.name = name;
  }
}

export class User {
  id: number;
  firstName: string;
  lastName: string;
  city: string;
  country: string;
  title: string;
  email : string;

  constructor(){}

}

export class Cart {
  products : Publication[];
  constructor(){}
}

export class Publication{
  id: number;
  title : string;
  description : string;
  no : number;
  price : number;

  constructor(){}
}
