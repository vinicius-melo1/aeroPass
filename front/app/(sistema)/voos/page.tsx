"use client"

import { Voo } from "@/app/types/voo";
import axios from "axios";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function Voos() {
    const [voos,setVoos] = useState<Voo[]>([]);
    useEffect(()=> {
        carregarDados();
    },[]);

    const carregarDados = async () => {
        debugger
        try {
            const dados = await axios.get<Voo[]>("http://localhost:8080/voos/listar");
            setVoos(dados.data);
        } catch {
            alert("Erro ao carregar dados");
        }
    }

    return (<>
        <div className="bg-blue-50 px-4 py-8">
            <div className="max-w-6xl mx-auto flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold text-blue-900">
                    Gestão de vôos
                </h1>
                <Link href="/voos/novo" className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-4 py-2 rounded-lg transition-colors">Novo</Link>
            </div>
            <div className="max-w-6xl mx-auto">
                <div className="bg-white rounded-2xl shadow-lg border border-blue-100 overflow-x-auto">
                    <table className="w-full text-left">
                        <thead className="bg-blue-100">
                            <tr>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Id</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Código do vôo</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Capacidade</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Assentos Disponíveis</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Cidade de Origem</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Cidade de Destino</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Data e hora de saída</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Data e hora de chegada</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Status</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Ações</th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-blue-50">
                            {voos.map((voo) => (
                                <tr key={voo.id}>
                                    <td className="px-6 py-3 text-blue-800">{voo.id}</td>
                                    <td className="px-6 py-3 text-blue-800">{voo.codigoVoo}</td>
                                    <td className="px-6 py-3 text-blue-800">{voo.capacidade}</td>
                                    <td className="px-6 py-3 text-blue-800">{voo.assentosDisponiveis}</td>
                                    <td className="px-6 py-3 text-blue-800">{voo.cidadeOrigem}</td>
                                    <td className="px-6 py-3 text-blue-800">{voo.cidadeDestino}</td>
                                    <td className="px-6 py-3 text-blue-800">{voo.dataHoraSaida}</td>
                                    <td className="px-6 py-3 text-blue-800">{voo.dataHoraChegada}</td>
                                    <td className="px-6 py-3 text-blue-800">{voo.status}</td>
                                    <td className="px-6 py-3 text-blue-800"><Link href={`/voos/${voo.id}/editar`} className="text-gray-50 font-semibold bg-yellow-500 p-2 rounded-sm fs-12px">Editar</Link></td>
                                </tr>
                            ))}
                            {
                                voos.length === 0 && 
                                (
                                    <tr>
                                        <td colSpan={5} className="px-6 py-12 text-center text-blue-800 italic">
                                            Nenhum vôo encontrado!
                                        </td>
                                    </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </>);
}