import { Usuario } from "./usuario"

export type Role = "ROLE_ADMIN" | "ROLE_USER" | "ROLE_CLIENTE"

export interface LoginResponse{
    token:string
}

export interface AuthState {
    usuario: Usuario | null,
    token: string
}