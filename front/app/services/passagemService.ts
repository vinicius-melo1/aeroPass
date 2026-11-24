import { Passagem, ReservaRequest } from "../types/passagem";
import api from "./api";


export async function buscarListaPassagens() : Promise<Passagem[]> {
    const dados = await api.get<Passagem[]>("/passagens/listar");
    if(dados.status == 200) {
        return dados.data;
    }
    return [];
}

export async function buscarPassagensPorVoo(vooId: number): Promise<Passagem[]> {
    const response = await api.get(`/passagens/voo/${vooId}`);
    return response.data;
}



export async function deletarPassagem(id:number) : Promise<number>{
    var dados = await api.delete<number>('/passagens/'+id+'/excluir');
    return dados.status;
}

export async function atualizarStatusPassagem(passagem:Passagem) : Promise<void> {
    var novoStatus = {};
    if(passagem.status === "DISPONIVEL") {
        novoStatus = {status:"BLOQUEADO"}
    } else {
        novoStatus = {status:"DISPONIVEL"}
    }

    var dadosRetorno = await api.patch<number>('/passagens/'+passagem.id+'/status',novoStatus);

    if(dadosRetorno.status == 200) {
        alert("Status foi atualizado com sucesso!");
    } else {
        alert(dadosRetorno.data);
        return;
    }
}

export async function reservarPassagem(id: number, reserva: ReservaRequest): Promise<number> {
    const dados = await api.post<number>('/passagens/' + id + '/reservar', reserva);
    return dados.status;
}

export async function cancelarPassagem(id: number): Promise<number> {
    const dados = await api.patch<number>('/passagens/' + id + '/cancelar');
    return dados.status;
}

export async function consultaPassagemPorId(id:number) : Promise<Passagem> {
    const valorPassagemBack = await api.get<Passagem>('/passagens/'+id);
    return valorPassagemBack.data;
}
export async function atualizarPassagem(passagem:Passagem) : Promise<number> {
    var dadosRetorno = await api.put<number>('/passagens/'+passagem.id,passagem);
    return dadosRetorno.data
}

export async function salvarPassagem(passagem:Passagem) : Promise<number> {
    var dadosRetorno = await api.post<number>('/passagens/criar',passagem);
    return dadosRetorno.data
}