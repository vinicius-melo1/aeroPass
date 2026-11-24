import { LoginResponse } from "../types/auth";
import api from "./api";

export async function loginBack(email:string, senha:string) : Promise <LoginResponse>{
    var loginResposta = await api.post<LoginResponse>("/auth/login",{email:email, senha:senha});

    if(loginResposta.status!= 200) {
        alert("Login ou senha Inválidos");
    }
    return loginResposta.data;
}

export async function esqueciSenha(email: string): Promise<void> {
    await api.post("/auth/esqueci-senha", { email });
}

export async function redefinirSenha(token: string, novaSenha: string): Promise<void> {
    await api.post("/auth/redefinir-senha", { token, novaSenha });
}