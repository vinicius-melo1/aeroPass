import { Aviao } from "../types/aviao";
import api from "./api";

export async function buscarListaAvioes() : Promise<Aviao[]> {
    const dados = await api.get<Aviao[]>("http://localhost:8080/avioes/listar");
    if(dados.status == 200) {
        return dados.data;
    }
    return [];
}

export async function deletarAviao(id:number) : Promise<number> {
    var dados = await api.delete<number>('http://localhost:8080/avioes/'+id+'/excluir');
    return dados.status;
}

export async function atualizarStatusAviao(aviao:Aviao) : Promise<void> {
    var novoStatus = {};
    if(aviao.status === "ATIVO") {
        novoStatus = {status:"BLOQUEADO"}
    } else {
        novoStatus = {status:"ATIVO"}
    }

    var dadosRetorno = await api.patch<number>('http://localhost:8080/avioes/'+aviao.id+'/status',novoStatus);

    if(dadosRetorno.status == 200) {
        alert("Status foi atualizado com sucesso!");
    } else {
        alert(dadosRetorno.data);
        return;
    }

}

export async function consultaAviaoPorId(id:number) : Promise<Aviao> {
    const valorAviaoBack = await api.get<Aviao>('http://localhost:8080/avioes/'+id);
    return valorAviaoBack.data;
}

export async function atualizarAviao(aviao:Aviao) : Promise<number> {
    var dadosRetorno = await api.put<number>('http://localhost:8080/avioes/'+aviao.id,aviao);
    return dadosRetorno.data
}

export async function salvarAviao(aviao:Aviao) : Promise<number> {
    var dadosRetorno = await api.post<number>('http://localhost:8080/avioes'+aviao,aviao);
    return dadosRetorno.data;
}