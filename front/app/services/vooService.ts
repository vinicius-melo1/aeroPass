import { Voo } from "../types/voo";
import api from "./api";


export async function buscarListaVoos() : Promise<Voo[]> {
    const dados = await api.get<Voo[]>("http://localhost:8080/voos/listar");
    if(dados.status == 200) {
        return dados.data;
    }
    return [];
}

export async function deletarVoo(id:number) : Promise<number> {
    var dadosRetorno = await api.delete<number>('http://localhost:8080/voos/'+id+'/excluir');
    return dadosRetorno.status;
}
export async function atualizarStatusVoo(voo:Voo) : Promise<void> {
    var novoStatus = {};
    if(voo.status === "ATIVO") {
        novoStatus = {status:"BLOQUEADO"}
    } else {
        novoStatus = {status:"ATIVO"}
    }

    var dadosRetorno = await api.patch<number>('http://localhost:8080/voos/'+voo.id+'/status',novoStatus);

    if(dadosRetorno.status == 200) {
        alert("Status foi atualizado com sucesso!");
    } else {
        alert(dadosRetorno.data);
        return;
    }
}

export async function consultaVooPorId(id:number) : Promise<Voo> {
    const valorVooBack = await api.get<Voo>('http://localhost:8080/voos/'+id);
    return valorVooBack.data;

}
export async function atualizarVoo(voo:Voo) : Promise<number> {
    var dadosRetorno = await api.put<number>('http://localhost:8080/voos/'+voo.id,voo);
    return dadosRetorno.data;
}

export async function salvarVoo(voo:Voo) : Promise<number> {
    var dadosRetorno = await api.post<number>('http://localhost:8080/voos/'+voo.id,voo);
    return dadosRetorno.data;
}