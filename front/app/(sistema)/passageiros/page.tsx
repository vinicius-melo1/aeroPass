"use client"

import { Passageiro } from "@/app/types/passageiro";
import axios from "axios";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function passageiros() {
    const [passageiros,setPassageiros] = useState<Passageiro[]>([]);
    useEffect(()=> {
        carregarDados();
    },[]);

    const carregarDados = async () => {
        try {
            const dados = await axios.get<Passageiro[]>("http://localhost:8080/passageiros/listar");
            setPassageiros(dados.data);
        } catch {
            alert("Erro ao carregar dados");
        }
    }

    return(
        <div className="bg-blue-50 px-4 py-8">
            <div className="max-w-6xl mx-auto flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold text-blue-900">
                    Gestão de passageiros
                </h1>
                <Link href="/passageiros/novo" className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-4 py-2 rounded-lg transition-colors">Novo</Link>
            </div>
            <div className="max-w-6xl mx-auto">
                <div className="bg-white rounded-2xl shadow-lg border border-blue-100 overflow-x-auto">
                    <table className="w-full text-left">
                        <thead className="bg-blue-100">
                            <tr>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Código</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Nome</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">CPF</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Passaporte</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Telefone</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">E-mail</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Status</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Ações</th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-blue-50">
                            {passageiros.map((passageiro) => (
                                <tr key={passageiro.id}>
                                    <td className="px-6 py-3 text-blue-800">{passageiro.id}</td>
                                    <td className="px-6 py-3 text-blue-800">{passageiro.nome}</td>
                                    <td className="px-6 py-3 text-blue-800">{passageiro.cpf}</td>
                                    <td className="px-6 py-3 text-blue-800">{passageiro.passaporte}</td>
                                    <td className="px-6 py-3 text-blue-800">{passageiro.telefone}</td>
                                    <td className="px-6 py-3 text-blue-800">{passageiro.email}</td>
                                    <td className="px-6 py-3 text-blue-800">{passageiro.status }</td>
                                    <td className="px-6 py-3 text-blue-800"><Link href={`/passageiros/${passageiro.id}/editar`} className="text-gray-50 font-semibold bg-yellow-500 p-2 rounded-sm fs-12px">Editar</Link></td>
                                </tr>
                            ))}
                            {
                                passageiros.length === 0 && 
                                (
                                    <tr>
                                        <td colSpan={5} className="px-6 py-12 text-center text-blue-800 italic">
                                            Nenhum passageiro encontrado!
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