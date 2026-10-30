import { Passagem } from "../types/passagem";
import api from "./api";


export async function buscarListaPassagens() : Promise<Passagem[]> {
    const dados = await api.get<Passagem[]>("http://localhost:8080/passagens/listar");
    if(dados.status == 200) {
        return dados.data;
    }
    return [];
}


export async function deletarPassagem(id:number) : Promise<number>{
    var dados = await api.delete<number>('http://localhost:8080/passagens/'+id+'/excluir');
    return dados.status;
}

export async function atualizarStatusPassagem(passagem:Passagem) : Promise<void> {
    var novoStatus = {};
    if(passagem.status === "ATIVO") {
        novoStatus = {status:"BLOQUEADO"}
    } else {
        novoStatus = {status:"ATIVO"}
    }

    var dadosRetorno = await api.patch<number>('http://localhost:8080/passagens/'+passagem.id+'/status',novoStatus);

    if(dadosRetorno.status == 200) {
        alert("Status foi atualizado com sucesso!");
    } else {
        alert(dadosRetorno.data);
        return;
    }
}

export async function consultaPassagemPorId(id:number) : Promise<Passagem> {
    const valorPassagemBack = await api.get<Passagem>('http://localhost:8080/passagens/'+id);
    return valorPassagemBack.data;
}
export async function atualizarPassagem(passagem:Passagem) : Promise<number> {
    var dadosRetorno = await api.put<number>('http://localhost:8080/passagens/'+passagem.id,passagem);
    return dadosRetorno.data
}

export async function salvarPassagem(passagem:Passagem) : Promise<number> {
    var dadosRetorno = await api.post<number>('http://localhost:8080/passagens/'+passagem.id,passagem);
    return dadosRetorno.data
}