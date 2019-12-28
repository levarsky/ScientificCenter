export class TransactionDao{
    seedSender: string;
    seedReceiver: string;
    amount: number;

    constructor(seedSender: string, seedReceiver: string, amount: number){
        this.seedSender = seedSender;
        this.seedReceiver = seedReceiver;
        this.amount = amount;
    }
}