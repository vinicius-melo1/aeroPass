"use client"

import { Aviao } from "@/app/types/aviao";
import axios from "axios";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function avioes() {
    const [avioes,setAvioes] = useState<Aviao[]>([]);
    useEffect(()=> {
        carregarDados();
    },[]);

    const carregarDados = async () => {
        try {
            const dados = await axios.get<Aviao[]>("http://localhost:8080/avioes/listar");
            setAvioes(dados.data);
        } catch {
            alert("Erro ao carregar dados");
        }
    }

    return(
        <div className="bg-blue-50 px-4 py-8">
            <div className="max-w-6xl mx-auto flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold text-blue-900">
                    Gestão de aviões
                </h1>
                <Link href="/avioes/novo" className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-4 py-2 rounded-lg transition-colors">Novo</Link>
            </div>
            <div className="max-w-6xl mx-auto">
                <div className="bg-white rounded-2xl shadow-lg border border-blue-100 overflow-x-auto">
                    <table className="w-full text-left">
                        <thead className="bg-blue-100">
                            <tr>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Código</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Modelo</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Fabricante</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Número de série</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Status</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Ações</th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-blue-50">
                            {avioes.map((aviao) => (
                                <tr key={aviao.id}>
                                    <td className="px-6 py-3 text-blue-800">{aviao.id}</td>
                                    <td className="px-6 py-3 text-blue-800">{aviao.modelo}</td>
                                    <td className="px-6 py-3 text-blue-800">{aviao.fabricante}</td>
                                    <td className="px-6 py-3 text-blue-800">{aviao.numeroSerie}</td>
                                    <td className="px-6 py-3 text-blue-800">{aviao.status }</td>
                                    <td className="px-6 py-3 text-blue-800"><Link href={`/avioes/${aviao.id}/editar`} className="text-gray-50 font-semibold bg-yellow-500 p-2 rounded-sm fs-12px">Editar</Link></td>
                                </tr>
                            ))}
                            {
                                avioes.length === 0 && 
                                (
                                    <tr>
                                        <td colSpan={5} className="px-6 py-12 text-center text-blue-800 italic">
                                            Nenhum avião encontrado!
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