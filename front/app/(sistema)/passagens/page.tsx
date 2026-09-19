"use client"

import { Passagem } from "@/app/types/passagem";
import axios from "axios";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function passagens() {
    const [passagens,setPassagens] = useState<Passagem[]>([]);
    useEffect(()=> {
        carregarDados();
    },[]);

    const carregarDados = async () => {
        try {
            const dados = await axios.get<Passagem[]>("http://localhost:8080/passagens/listar");
            setPassagens(dados.data);
        } catch {
            alert("Erro ao carregar dados");
        }
    }

    return(
        <div className="bg-blue-50 px-4 py-8">
            <div className="max-w-6xl mx-auto flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold text-blue-900">
                    Gestão de passagens
                </h1>
                <Link href="/passagens/novo" className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-4 py-2 rounded-lg transition-colors">Novo</Link>
            </div>
            <div className="max-w-6xl mx-auto">
                <div className="bg-white rounded-2xl shadow-lg border border-blue-100 overflow-x-auto">
                    <table className="w-full text-left">
                        <thead className="bg-blue-100">
                            <tr>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Código</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Código do assento</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Data de compra</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Valor da passagem</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Forma de pagamento</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Status</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Ações</th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-blue-50">
                            {passagens.map((passagem) => (
                                <tr key={passagem.id}>
                                    <td className="px-6 py-3 text-blue-800">{passagem.id}</td>
                                    <td className="px-6 py-3 text-blue-800">{passagem.codigoAssento}</td>
                                    <td className="px-6 py-3 text-blue-800">{passagem.dataCompra}</td>
                                    <td className="px-6 py-3 text-blue-800">{passagem.valor}</td>
                                    <td className="px-6 py-3 text-blue-800">{passagem.formaPagamento }</td>
                                    <td className="px-6 py-3 text-blue-800">{passagem.status }</td>
                                    <td className="px-6 py-3 text-blue-800"><Link href={`/passagens/${passagem.id}/editar`} className="text-gray-50 font-semibold bg-yellow-500 p-2 rounded-sm fs-12px">Editar</Link></td>
                                </tr>
                            ))}
                            {
                                passagens.length === 0 && 
                                (
                                    <tr>
                                        <td colSpan={5} className="px-6 py-12 text-center text-blue-800 italic">
                                            Nenhuma passagem encontrada!
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