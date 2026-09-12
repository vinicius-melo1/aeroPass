export class Passagem{
    constructor(
        public id:number | null,
        public codigoAssento:string,
        public dataCompra:string,
        public valor:number,
        public formaPagamento:string,
        public status:string
    ){}
}