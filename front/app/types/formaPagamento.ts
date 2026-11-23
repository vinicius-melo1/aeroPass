export class FormaPagamento {
    constructor(
        public id: number | null,
        public tipoPagamento: string,
        public status: string,
    ) {}
}