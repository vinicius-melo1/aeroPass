import { Passageiro } from "../types/passageiro";
import api from "./api";


export async function buscarListaPassageiros() : Promise<Passageiro[]> {
    const dados = await api.get<Passageiro[]>("/passageiros/listar");
    if(dados.status == 200) {
        return dados.data;
    }
    return [];
}

export async function deletarPassageiro(id:number) : Promise<number>{
    var dados = await api.delete<number>('/passageiros/'+id+'/excluir');
    return dados.status;
}

export async function atualizarStatusPassageiro(passageiro:Passageiro) : Promise<void> {
    var novoStatus = {};
    if(passageiro.status === "ATIVO") {
        novoStatus = {status:"BLOQUEADO"}
    } else {
        novoStatus = {status:"ATIVO"}
    }

    var dadosRetorno = await api.patch<number>('/passageiros/'+passageiro.id+'/status',novoStatus);

    if(dadosRetorno.status == 200) {
        alert("Status foi atualizado com sucesso!");
    } else {
        alert(dadosRetorno.data);
        return;
    }

}

export async function consultaPassageiroPorId(id:number) : Promise<Passageiro> {
    const valorPassageiroBack = await api.get<Passageiro>('/passageiros/'+id);
    return valorPassageiroBack.data;
}

export async function atualizarPassageiro(passageiro:Passageiro) : Promise<number> {
    var dadosRetorno = await api.put<number>('/passageiros/'+passageiro.id,passageiro);
    return dadosRetorno.data;
}

export async function salvarPassageiro(passageiro:Passageiro) : Promise<number> {
    var dadosRetorno = await api.post<number>('/passageiros/'+passageiro.id,passageiro);
    return dadosRetorno.data;
}