import { Piloto } from "../types/piloto";
import api from "./api";

export async function buscarListaPilotos() : Promise<Piloto[]> {
    const dados = await api.get<Piloto[]>("/pilotos/listar");
    if(dados.status == 200) {
        return dados.data;
    }
    return [];
}

export async function deletarPiloto(id:number) : Promise<number> {
    var dados = await api.delete<number>('/pilotos/'+id+'/excluir');
    return dados.status;
}

export async function atualizarStatusPiloto(piloto:Piloto) : Promise<void> {
    var novoStatus = {};
    if(piloto.status === "ATIVO") {
        novoStatus = {status:"BLOQUEADO"}
    } else {
        novoStatus = {status:"ATIVO"}
    }

    var dadosRetorno = await api.patch<number>('/pilotos/'+piloto.id+'/status',novoStatus);

    if(dadosRetorno.status == 200) {
        alert("Status foi atualizado com sucesso!");
    } else {
        alert(dadosRetorno.data);
        return;
    }

}

export async function consultaPilotoPorId(id:number) : Promise<Piloto> {
    const valorPilotoBack = await api.get<Piloto>('/pilotos/'+id);
    return valorPilotoBack.data;
}

export async function atualizarPiloto(piloto:Piloto) : Promise<number> {
    var dadosRetorno = await api.put<number>('/pilotos/'+piloto.id,piloto);
    return dadosRetorno.data
}

export async function salvarPiloto(piloto:Piloto) : Promise<number> {
    var dadosRetorno = await api.post<number>('/pilotos',piloto);
    return dadosRetorno.data;
}