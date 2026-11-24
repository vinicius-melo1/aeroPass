"use client"

import { useAuth } from "@/app/hooks/useAuth";
import { buscarListaFormasPagamento } from "@/app/services/formasPagamentoService";
import { buscarListaPassageiros } from "@/app/services/passageiroService";
import { atualizarStatusPassagem, buscarListaPassagens, cancelarPassagem, deletarPassagem } from "@/app/services/passagemService";
import { buscarListaVoos } from "@/app/services/vooService";
import { FormaPagamento } from "@/app/types/formaPagamento";
import { Passageiro } from "@/app/types/passageiro";
import { Passagem } from "@/app/types/passagem";
import { Voo } from "@/app/types/voo";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function passagens() {
    const [passagens,setPassagens] = useState<Passagem[]>([]);
    const [passageiros, setPassageiros] = useState<Passageiro[]>([]);
    
    const { ehCliente } = useAuth();
    useEffect(()=> {
        carregarDados();
    },[]);
    const [voos, setVoos] = useState<Voo[]>([]);
    const [formasPagamento, setFormasPagamento] = useState<FormaPagamento[]>([]);

    const carregarDados = async () => {
        try {
            const dados = await buscarListaPassagens();
            setVoos(await buscarListaVoos());
            setFormasPagamento(await buscarListaFormasPagamento());
            setPassageiros(await buscarListaPassageiros());
            setPassagens(dados);
        } catch {
            alert("Erro ao carregar dados");
        }
    }

    const formatarVoo = (passagem: Passagem) => {
        const voo = voos.find(v => v.id === passagem.vooId);
        return voo
        ? `${voo.codigoVoo} - ${voo.cidadeOrigem} → ${voo.cidadeDestino}`
        : "-";
    }

    const formatarFormasPagamento = (passagem: Passagem) => {
        const nomes = (passagem.formasPagamentoIds ?? [])
            .map(id => formasPagamento.find(f => f.id === id)?.tipoPagamento)
            .filter(Boolean);
        return nomes.length > 0 ? nomes.join(", ") : "-";
    }

    const formatarPassageiro = (passagem: Passagem) => {
        const passageiro = passageiros.find(p => p.id === passagem.passageiroId);
        return passageiro ? passageiro.nome : "-";
    }

    const handlerDeletarPassagem = async(passagem:Passagem) => {
        var dadosRetorno = await deletarPassagem(passagem.id||0);

        if(dadosRetorno == 200) {
            alert("A passagem foi deletada com sucesso!");
        } else {
            alert(dadosRetorno);
            return;
        }

        carregarDados();
    }

    const handlerCancelarPassagem = async(passagem:Passagem) => {
        var dadosRetorno = await cancelarPassagem(passagem.id||0);

        if(dadosRetorno == 200) {
            alert("A passagem foi cancelada com sucesso!");
        } else {
            alert(dadosRetorno);
            return;
        }

        carregarDados();
    }

    // const formatarFormasPagamento = (passagem:Passagem) => {
    //     const nomes = passagem.formasPagamentoNomes;
    //     return nomes && nomes.length > 0 ? nomes.join(", ") : "-";
    // }

    const handlerAlterarPassagem = async(passagem:Passagem) => {
        await atualizarStatusPassagem(passagem);
        carregarDados();
    }

    return(
        <div className="bg-blue-50 px-4 py-8">
            <div className="max-w-6xl mx-auto flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold text-blue-900">
                    Gestão de passagens
                </h1>
                {!ehCliente && (
                    <Link href="/passagens/novo" className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-4 py-2 rounded-lg transition-colors">Novo</Link>
                )}
            </div>
            <div className="max-w-6xl mx-auto">
                <div className="bg-white rounded-2xl shadow-lg border border-blue-100 overflow-x-auto">
                    <table className="w-full text-left">
                        <thead className="bg-blue-100">
                            <tr>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">Código</th>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">Vôo</th>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">Passageiro</th>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">Código do assento</th>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">Data de compra</th>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">Valor da passagem</th>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">Formas de pagamento</th>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">Status</th>
                                <th className="px-6 py-3 text-sm text-nowrap text-center font-semibold text-blue-900">Ações</th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-blue-50">
                            {passagens.map((passagem) => (
                                <tr key={passagem.id}>
                                    <td className="text-nowrap text-center px-6 py-3 text-blue-800">{passagem.id}</td>
                                    <td className="text-nowrap text-center px-6 py-3 text-blue-800">{formatarVoo(passagem)}</td>
                                    <td className="text-nowrap text-center px-6 py-3 text-blue-800">{formatarPassageiro(passagem)}</td>
                                    <td className="text-nowrap text-center px-6 py-3 text-blue-800">{passagem.codigoAssento}</td>
                                    <td className="text-nowrap text-center px-6 py-3 text-blue-800">{passagem.dataCompra ?? "-"}</td>
                                    <td className="text-nowrap text-center px-6 py-3 text-blue-800">{passagem.valor}</td>
                                    <td className="text-nowrap text-center px-6 py-3 text-blue-800">{formatarFormasPagamento(passagem)}</td>
                                    <td className="text-nowrap text-center px-6 py-3 text-blue-800">{passagem.status}</td>
                                    <td className="flex flex-center gap-4 px-6 py-3 text-blue-800">
                                        {!ehCliente && (
                                            <Link href={`/passagens/${passagem.id}/editar`} className="text-gray-50 font-semibold bg-yellow-500 p-2 rounded-sm fs-12px">Editar</Link>
                                        )}
                                        {!ehCliente && (
                                            <button onClick= {()=> handlerDeletarPassagem(passagem)} className="text-gray-50 font-semibold bg-red-500 hover:bg-red-600 transition-color p-2 rounded-sm fs-12px">DELETAR</button>    
                                        )}
                                        {passagem.status === 'VENDIDO' && ehCliente && (
                                            <button onClick= {()=> handlerCancelarPassagem(passagem)} 
                                                className="text-gray-50 font-semibold bg-orange-600 hover:bg-orange-800 transition-colors p-2 rounded-sm fs-12px">CANCELAR
                                            </button>
                                        )}
                                        {!ehCliente && (
                                            <button onClick= {()=> handlerAlterarPassagem(passagem)} 
                                                className= {`text-gray-50 font-semibold font-semibold transition-colors p-2 rounded-sm fs-12px ${passagem.status ==='BLOQUEADO'
                                                ?'bg-orange-600 hover:bg-orange-800' 
                                                :' bg-green-600 hover:bg-green-800' }`
                                                }>{passagem.status}
                                            </button>
                                        )}
                                    </td>
                                </tr>
                            ))}
                            {
                                passagens.length === 0 && 
                                (
                                    <tr>
                                        <td colSpan={8} className="px-6 py-12 text-center text-blue-800 italic">
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