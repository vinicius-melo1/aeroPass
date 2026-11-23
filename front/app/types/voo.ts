export class Voo{
    constructor(
        public id: number | null,
        public codigoVoo: string,
        public capacidade: number,
        public assentosDisponiveis: number,
        public cidadeOrigem: string,
        public cidadeDestino: string,
        public dataHoraSaida: string,
        public dataHoraChegadaPrevista: string,
        public dataHoraChegada: string,
        public status: string,
        public aviaoId: number | null = null,
        public pilotosIds: number[] = [],
        public aviaoModelo: string = "",     
        public pilotosCanac: string[] = [],   
    ){}
}

export interface VooFormProps{
    vooExistente?:Voo
}