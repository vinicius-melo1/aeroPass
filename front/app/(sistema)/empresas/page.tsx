"use client"

import { useAuth } from "@/app/hooks/useAuth";
import { buscarListaEmpresas, deletarEmpresa, atualizarStatusEmpresa } from "@/app/services/empresaService";
import { Empresa } from "@/app/types/empresa";
import Link from "next/link";
import { useState, useEffect } from "react";

export default function Empresas() {
    const [empresas,setEmpresas] = useState<Empresa[]>([]);

    const { ehAdmin, ehCliente } = useAuth();
    
    useEffect(()=> {
        carregarDados();
    },[]);

    const carregarDados = async () => {
        try {
            const dados = await buscarListaEmpresas();
            setEmpresas(dados);
        } catch {
            alert("Erro ao carregar dados");
        }
    }

    const handlerDeletarEmpresa = async(empresa:Empresa) => {
        var dadosRetorno = await deletarEmpresa(empresa.id||0);

        if(dadosRetorno == 200) {
            alert("Empresa foi deletada com sucesso!");
        } else {
            alert(dadosRetorno);
            return;
        }

        carregarDados();
    }

    const handlerAlterarEmpresa = async(empresa:Empresa) => {
        await atualizarStatusEmpresa(empresa);
        carregarDados();
    }

    return(
        <div className="bg-blue-50 px-4 py-8">
            <div className="max-w-6xl mx-auto flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold text-blue-900">
                    Gestão de empresas
                </h1>
                {ehAdmin && (
                    <Link href="/empresas/novo" className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-4 py-2 rounded-lg transition-colors">Novo</Link>
                )}
            </div>
            <div className="max-w-6xl mx-auto">
                <div className="bg-white rounded-2xl shadow-lg border border-blue-100 overflow-x-auto">
                    <table className="w-full text-left">
                        <thead className="bg-blue-100">
                            <tr>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">Código</th>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">Razão Social</th>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">CNPJ</th>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">Nome Fantasia</th>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">Status</th>
                                {!ehCliente && (
                                    <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">Ações</th>
                                )}
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-blue-50">
                            {empresas.map((empresa) => (
                                <tr key={empresa.id}>
                                    <td className="text-nowrap text-center px-6 py-3 text-blue-800">{empresa.id}</td>
                                    <td className="text-nowrap text-center px-6 py-3 text-blue-800">{empresa.razaoSocial}</td>
                                    <td className="text-nowrap text-center px-6 py-3 text-blue-800">{empresa.cnpj}</td>
                                    <td className="text-nowrap text-center px-6 py-3 text-blue-800">{empresa.nomeFantasia}</td>
                                    <td className="text-nowrap text-center px-6 py-3 text-blue-800">{empresa.status}</td>
                                    {!ehCliente && (
                                        <td className="flex justify-center gap-4 px-6 py-3 text-blue-800">
                                            {ehAdmin && (
                                                <Link href={`/empresas/${empresa.id}/usuario`} className="text-gray-50 font-semibold bg-blue-600 hover:bg-blue-700 transition-colors p-2 rounded-sm fs-12px">CRIAR USUÁRIO</Link>
                                            )}
                                            <Link href={`/empresas/${empresa.id}/editar`} className="text-gray-50 font-semibold bg-yellow-500 p-2 rounded-sm fs-12px">Editar</Link>
                                            {ehAdmin && (
                                                <button onClick= {()=> handlerDeletarEmpresa(empresa)} className="text-gray-50 font-semibold bg-red-500 hover:bg-red-600 transition-color p-2 rounded-sm fs-12px">DELETAR</button>    
                                            )}
                                            {ehAdmin && (
                                                <button onClick= {()=> handlerAlterarEmpresa(empresa)} 
                                                    className= {`text-gray-50 font-semibold font-semibold transition-colors p-2 rounded-sm fs-12px ${empresa.status ==='BLOQUEADO'
                                                    ?'bg-orange-600 hover:bg-orange-800' 
                                                    :' bg-green-600 hover:bg-green-800' }`
                                                    }>{empresa.status}
                                                </button>    
                                            )}
                                        </td>
                                    )}
                                </tr>
                            ))}
                            {
                                empresas.length === 0 && 
                                (
                                    <tr>
                                        <td colSpan={5} className="px-6 py-12 text-center text-blue-800 italic">
                                            Nenhuma empresa encontrada!
                                        </td>
                                    </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}