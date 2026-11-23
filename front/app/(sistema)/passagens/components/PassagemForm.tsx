"use client"

import { buscarListaFormasPagamento } from "@/app/services/formasPagamentoService";
import { atualizarPassagem, salvarPassagem } from "@/app/services/passagemService";
import { buscarListaVoos } from "@/app/services/vooService";
import { Passagem, PassagemFormProps } from "@/app/types/passagem";
import { Voo } from "@/app/types/voo";
import Link from "next/link";
import { useRouter } from "next/navigation";

import { useEffect, useState } from "react";


export default function PassagemForm({passagemExistente}:PassagemFormProps){ 
    const router = useRouter();
    const [voos, setVoos] = useState<Voo[]>([]);

    const [passagem,setPassagem ] = useState<Passagem>
        (passagemExistente ||
        new Passagem(null,"",null,0,[],"DISPONIVEL"));

    useEffect(() => {
        carregarDados();
    }, []);

    const carregarDados = async () => {
        try {
            setVoos(await buscarListaVoos());
        } catch {
            alert("Erro ao carregar dados");
        }
    }
    const handlerChange = (campo: 'codigoAssento' | 'valor', valor:string) => {
        setPassagem(valorAnterior => 
            new Passagem(
                valorAnterior.id,
                campo === 'codigoAssento' ? valor : valorAnterior.codigoAssento,
                valorAnterior.dataCompra,
                campo === 'valor' ? Number(valor) : valorAnterior.valor,
                valorAnterior.formasPagamentoIds,
                valorAnterior.status,
                valorAnterior.vooId,
                valorAnterior.vooCodigo,
                valorAnterior.passageiroId,
            )
        )
    }

    const handlerVoo = (vooId: number | null) => {
        setPassagem(valorAnterior =>
            new Passagem(
                valorAnterior.id,
                valorAnterior.codigoAssento,
                valorAnterior.dataCompra,
                valorAnterior.valor,
                valorAnterior.formasPagamentoIds,
                valorAnterior.status,
                vooId,
                valorAnterior.vooCodigo,
                valorAnterior.passageiroId,
            )
        )
    }


    const handlerSalvar = async (formData : FormData) =>{

        if(passagemExistente) {
            var dadosRetorno = await atualizarPassagem(passagem);

            if(dadosRetorno) {
                alert("Passagem foi salva com sucesso!");
            } else {
                alert("Erro ao salvar passagem");
            }
        } else {
            var dadosRetorno = await salvarPassagem(passagem);

            if(dadosRetorno) {
                alert("Passagem foi salva com sucesso!");
            } else {
                alert("Erro ao salvar passagem");
            }
        }

        router.push("/passagens");
    }
    return(
        <form action={handlerSalvar} className="bg-white rounded-2xl shadow-lg border border-blue-100 p-8 max-w-lg mx-auto">
            <div className="flex flex-col gap-5">
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Vôo:</label>
                    <select
                        name="vooId"
                        value={passagem.vooId ?? ""}
                        onChange={(e)=>handlerVoo(e.target.value === "" ? null : Number(e.target.value))}
                        required
                        className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
                        <option value="">Selecione um vôo</option>
                        {voos.map(voo => (
                            <option key={voo.id} value={voo.id ?? ""}>
                                {voo.codigoVoo} - {voo.cidadeOrigem} → {voo.cidadeDestino}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Código do assento:</label>
                    <input 
                        name="codigoAssento" 
                        value={passagem.codigoAssento}
                        onChange={(e)=>handlerChange('codigoAssento',e.target.value)}
                        placeholder="12A"
                        required
                        className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Valor de passagem:</label>
                    <input name="valor" 
                        type="number"
                        min="0.01"
                        step="0.01"
                        value={passagem.valor}
                        onChange={(e)=>handlerChange('valor',e.target.value)}
                        required
                        className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>

                <div className="flex items-center justify-end gap-3 pt-2">
                    <Link href="/passagens" className="text-sm font-semibold text-blue-600 hover:text-blue-800 px-4 py-2 rounded-lg transition-colors">Cancelar</Link>
                    <button type="submit" className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-5 py-2 rounded-lg transition-colors">Salvar</button>
                </div>
            </div>
        </form>
    );
}