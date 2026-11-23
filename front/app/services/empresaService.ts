import { Empresa } from "../types/empresa";
import api from "./api";

export async function buscarListaEmpresas(): Promise<Empresa[]> {
    const dados = await api.get<Empresa[]>("/empresas/listar");
    if (dados.status == 200) {
        return dados.data;
    }
    return [];
}

export async function deletarEmpresa(id: number): Promise<number> {
    const dados = await api.delete<number>('/empresas/' + id + '/excluir');
    return dados.status;
}

export async function atualizarStatusEmpresa(empresa: Empresa): Promise<void> {
    const novoStatus = { status: empresa.status === "ATIVO" ? "BLOQUEADO" : "ATIVO" };
    const dadosRetorno = await api.patch<number>('/empresas/' + empresa.id + '/status', novoStatus);

    if (dadosRetorno.status == 200) {
        alert("Status foi atualizado com sucesso!");
    } else {
        alert(dadosRetorno.data);
    }
}

export async function consultaEmpresaPorId(id: number): Promise<Empresa> {
    const valorEmpresaBack = await api.get<Empresa>('/empresas/' + id);
    return valorEmpresaBack.data;
}

export async function atualizarEmpresa(empresa: Empresa): Promise<number> {
    const dadosRetorno = await api.put<number>('/empresas/' + empresa.id, empresa);
    return dadosRetorno.data;
}

export async function salvarEmpresa(empresa: Empresa): Promise<number> {
    const dadosRetorno = await api.post<number>('/empresas/cadastro', empresa);
    return dadosRetorno.data;
}