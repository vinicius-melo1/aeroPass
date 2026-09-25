export class Aviao{
    constructor(
        public id:number | null,
        public modelo:string,
        public fabricante:string,
        public numeroSerie:number,
        public status:string,
    ){}
}

export interface AviaoFormProps{
    aviaoExistente?:Aviao
}