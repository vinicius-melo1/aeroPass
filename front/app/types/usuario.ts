import { Role } from "./auth";

export class Usuario{
    constructor(
        public id: number | null,
        public nome: string,
        public email: string,
        public cpf: string,
        public status: string,
        public senha: string,
        public role: Role | "" = "",
        public empresaId: number | null = null,
        public empresaNome: string = "",
    ){}
}

export interface UsuarioFormProps{
    usuarioExistente?:Usuario
    empresaId?:number
}