"use client"

import { registrarVooVisto } from "@/app/redux/slices/voosVistosSlice";
import { buscarPassagensPorVoo } from "@/app/services/passagemService";
import { consultaVooPorId } from "@/app/services/vooService";
import { Passagem } from "@/app/types/passagem";
import { Voo } from "@/app/types/voo";
import Link from "next/link";
import { useParams } from "next/navigation";
import { useState, useEffect } from "react";
import { useDispatch } from "react-redux";

export default function PassagensDoVoo() {
    const { codigo } = useParams<{ codigo: string }>();
    const vooId = Number(codigo);
    const dispatch = useDispatch();
    
    const [voo, setVoo] = useState<Voo | null>(null);
    const [passagens, setPassagens] = useState<Passagem[]>([]);

    
    useEffect(() => {
        carregarDados();
    }, []);

    useEffect(() => {
        if (voo) dispatch(registrarVooVisto(voo));
    }, [voo, dispatch]);

    const carregarDados = async () => {
        try {
            setVoo(await consultaVooPorId(vooId));
            setPassagens(await buscarPassagensPorVoo(vooId));
        } catch {
            alert("Erro ao carregar dados");
        }
    }
    

    return (
        <div className="bg-blue-50 px-4 py-8">
            <div className="max-w-6xl mx-auto flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold text-blue-900">Passagens do voo</h1>
                <Link href="/voos" className="bg-gray-500 hover:bg-gray-600 text-white font-semibold px-4 py-2 rounded-lg transition-colors">Voltar</Link>
            </div>
            <div className="max-w-6xl mx-auto">
                <div className="bg-white rounded-2xl shadow-lg border border-blue-100 overflow-x-auto">
                    <table className="w-full text-left">
                        <thead className="bg-blue-100">
                            <tr>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Código</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Assento</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Valor</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Status</th>
                                <th className="px-6 py-3 text-sm text-nowrap font-semibold text-blue-900">Ações</th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-blue-50">
                            {passagens.map(passagem => (
                                <tr key={passagem.id}>
                                    <td className="px-6 py-3 text-blue-800">{passagem.id}</td>
                                    <td className="px-6 py-3 text-blue-800">{passagem.codigoAssento}</td>
                                    <td className="px-6 py-3 text-blue-800">R$ {passagem.valor}</td>
                                    <td className="px-6 py-3 text-blue-800">{passagem.status}</td>
                                    <td className="px-6 py-3">
                                        {passagem.status === "DISPONIVEL" && (
                                            <Link
                                                href={`/passagens/${passagem.id}/reservar`}
                                                className="text-gray-50 font-semibold bg-green-600 hover:bg-green-800 transition-colors p-2 rounded-sm fs-12px">
                                                Reservar
                                            </Link>
                                        )}
                                    </td>
                                </tr>
                            ))}
                            {passagens.length === 0 && (
                                <tr>
                                    <td colSpan={5} className="px-6 py-12 text-center text-blue-800 italic">
                                        Nenhuma passagem encontrada para este voo!
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}