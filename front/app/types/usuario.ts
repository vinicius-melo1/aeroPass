export class Usuario{
    constructor(
        public id:number | null,
        public nome:string,
        public email:string,
        public cpf:string,
        public status:string,
        public senha:string
    ){}
}

export interface UsuarioFormProps{
    usuarioExistente?:Usuario
}