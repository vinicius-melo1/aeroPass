"use client"

import { buscarListaPilotos, deletarPiloto, atualizarStatusPiloto } from "@/app/services/pilotoService";
import { Piloto } from "@/app/types/piloto";
import Link from "next/link";
import { useState, useEffect } from "react";

export default function Pilotos() {
    const [pilotos,setPilotos] = useState<Piloto[]>([]);
    useEffect(()=> {
        carregarDados();
    },[]);

    const carregarDados = async () => {
        try {
            const dados = await buscarListaPilotos();
            setPilotos(dados);
        } catch {
            alert("Erro ao carregar dados");
        }
    }

    const handlerDeletarPiloto = async(piloto:Piloto) => {
        var dadosRetorno = await deletarPiloto(piloto.id||0);

        if(dadosRetorno == 200) {
            alert("Piloto foi deletado com sucesso!");
        } else {
            alert(dadosRetorno);
            return;
        }

        carregarDados();
    }

    const handlerAlterarPiloto = async(piloto:Piloto) => {
        await atualizarStatusPiloto(piloto);
        carregarDados();
    }

    return(
        <div className="bg-blue-50 px-4 py-8">
            <div className="max-w-6xl mx-auto flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold text-blue-900">
                    Gestão de pilotos
                </h1>
                <Link href="/pilotos/novo" className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-4 py-2 rounded-lg transition-colors">Novo</Link>
            </div>
            <div className="max-w-6xl mx-auto">
                <div className="bg-white rounded-2xl shadow-lg border border-blue-100 overflow-x-auto">
                    <table className="w-full text-left">
                        <thead className="bg-blue-100">
                            <tr>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">Código</th>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">Nome</th>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">CANAC</th>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">Tipo de piloto</th>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">Status</th>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">Ações</th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-blue-50">
                            {pilotos.map((piloto) => (
                                <tr key={piloto.id}>
                                    <td className="text-nowrap text-center px-6 py-3 text-blue-800">{piloto.id}</td>
                                    <td className="text-nowrap text-center px-6 py-3 text-blue-800">{piloto.nome}</td>
                                    <td className="text-nowrap text-center px-6 py-3 text-blue-800">{piloto.canac}</td>
                                    <td className="text-nowrap text-center px-6 py-3 text-blue-800">{piloto.tipoPiloto}</td>
                                    <td className="text-nowrap text-center px-6 py-3 text-blue-800">{piloto.status}</td>
                                    <td className="flex justify-center gap-4 px-6 py-3 text-blue-800">
                                        <Link href={`/pilotos/${piloto.id}/editar`} className="text-gray-50 font-semibold bg-yellow-500 p-2 rounded-sm fs-12px">Editar</Link>
                                        <button onClick= {()=> handlerDeletarPiloto(piloto)} className="text-gray-50 font-semibold bg-red-500 hover:bg-red-600 transition-color p-2 rounded-sm fs-12px">DELETAR</button>    
                                        <button onClick= {()=> handlerAlterarPiloto(piloto)} 
                                            className= {`text-gray-50 font-semibold font-semibold transition-colors p-2 rounded-sm fs-12px ${piloto.status ==='BLOQUEADO'
                                            ?'bg-orange-600 hover:bg-orange-800' 
                                            :' bg-green-600 hover:bg-green-800' }`
                                            }>{piloto.status}
                                        </button>    
                                    </td>
                                </tr>
                            ))}
                            {
                                pilotos.length === 0 && 
                                (
                                    <tr>
                                        <td colSpan={6} className="px-6 py-12 text-center text-blue-800 italic">
                                            Nenhum piloto encontrado!
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