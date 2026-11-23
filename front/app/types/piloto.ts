export class Piloto{
    constructor(
        public id:number | null,
        public nome:string,
        public canac:string,
        public tipoPiloto:string,
        public status:string,
    ){}
}

export interface PilotoFormProps{
    pilotoExistente?:Piloto
}