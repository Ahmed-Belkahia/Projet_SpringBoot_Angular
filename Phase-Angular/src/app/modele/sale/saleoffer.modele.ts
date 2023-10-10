import { StatutOffre } from "./statutoffre";
import { TypeOffer } from "./typeoffer";




export class Saleoffer {
    

    sellid!:number;
    description!: string;
    title!:string;
    picture!: string;
    price!: DoubleRange;
    address!: string;
    datesell!: Date;
    surface!: DoubleRange;
    
    
    
    favorite!: number;
    sold!: boolean;
    typeoffer!: TypeOffer;
    statut !: StatutOffre;
    rating!:number;
    
    
      [key: string]: any;
    }
    

