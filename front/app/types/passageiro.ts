export class Passageiro{
    constructor(
        public id:number | null,
        public nome:string,
        public cpf:string,
        public passaporte:number,
        public telefone :string,
        public email :string,
        public status:string
    ){}
}

export interface PassageiroFormProps{
    passageiroExistente?:Passageiro
}