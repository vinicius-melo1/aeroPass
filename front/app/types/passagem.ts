export class Passagem {
    constructor(
        public id: number | null,
        public codigoAssento: string,
        public dataCompra: string | null,
        public valor: number,
        public formasPagamentoIds: number[] = [],
        public status: string,
        public vooId: number | null = null,
        public vooCodigo: string = "",            
        public passageiroId: number | null = null,
    ) {}
}

export interface ReservaRequest {
    formasPagamentoIds: number[]
}

export interface PassagemFormProps {
    passagemExistente?: Passagem
}