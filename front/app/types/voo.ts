export class Voo{
    constructor(
        public id:number | null,
        public codigoVoo:string,
        public capacidade:number,
        public assentosDisponiveis:number,
        public cidadeOrigem:string,
        public cidadeDestino:string,
        public dataHoraSaida:string,
        public dataHoraChegada:string,
        public status:string
    ){}
}

export interface VooFormProps{
    vooExistente?:Voo
}