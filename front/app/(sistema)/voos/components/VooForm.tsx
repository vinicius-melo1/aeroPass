"use client"

import { Voo, VooFormProps } from "@/app/types/voo";
import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";


export default function VooForm({vooExistente}:VooFormProps){ 
    const router = useRouter();

    const [voo,setVoo ] = useState<Voo>
        (vooExistente ||
        new Voo(null,"",0,0,"","","","","ATIVO"));

    const handlerChange = (campo: 'codigoVoo' | 'capacidade' | 'assentosDisponiveis' | 'cidadeOrigem' | 'cidadeDestino' | 'dataHoraSaida' | 'dataHoraChegada', valor:string) => {
        setVoo(valorAnterior => 
            new Voo(
                valorAnterior.id,
                campo === 'codigoVoo' ? valor : valorAnterior.codigoVoo,
                campo === 'capacidade' ? Number(valor) : valorAnterior.capacidade,
                campo === 'assentosDisponiveis' ? Number(valor) : valorAnterior.assentosDisponiveis,
                campo === 'cidadeOrigem' ? valor : valorAnterior.cidadeOrigem,
                campo === 'cidadeDestino' ? valor : valorAnterior.cidadeDestino,
                campo === 'dataHoraSaida' ? valor : valorAnterior.dataHoraSaida,
                campo === 'dataHoraChegada' ? valor : valorAnterior.dataHoraChegada,
                valorAnterior.status,
            )
        )
    }

    const handlerSalvar = async (formData : FormData) =>{
        if(vooExistente) {
            var dadosRetorno = await axios.put<number>('http://localhost:8080/voos/'+voo.id,voo);

            if(dadosRetorno.status == 200) {
                alert("Vôo foi salvo com sucesso!");
            } else {
                alert(dadosRetorno.data);
            }
        } else {
            var dadosRetorno = await axios.post<number>('http://localhost:8080/voos',voo);

            if(dadosRetorno.status == 200) {
                alert("Vôo foi salvo com sucesso!");
            } else {
                alert(dadosRetorno.data);
            }
        }

        router.push("/voos");
    }
    
    return(
        <form action={handlerSalvar} className="bg-white rounded-2xl shadow-lg border border-blue-100 p-8 max-w-lg mx-auto">
            <div className="flex flex-col gap-5">
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Código do Vôo:</label>
                    <input name="codigoVoo" 
                    value={voo.codigoVoo}
                    onChange={(e)=> handlerChange('codigoVoo',e.target.value)}
                    placeholder="AP1000"
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Capacidade:</label>
                    <input name="capacidade"
                    value={voo.capacidade}
                    onChange={(e)=> handlerChange('capacidade',e.target.value)}
                    placeholder="100"
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Assentos Disponíveis:</label>
                    <input name="assentosDisponiveis" 
                    value={voo.assentosDisponiveis}
                    onChange={(e)=> handlerChange('assentosDisponiveis',e.target.value)}
                    placeholder="20"
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Cidade de origem:</label>
                    <input name="cidadeOrigem" 
                    value={voo.cidadeOrigem}
                    onChange={(e)=> handlerChange('cidadeOrigem',e.target.value)}
                    placeholder="Floripa"
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Cidade de destino:</label>
                    <input name="cidadeDestino"
                    value={voo.cidadeDestino}
                    onChange={(e)=> handlerChange('cidadeDestino',e.target.value)}
                    placeholder="São Paulo"
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Data hora saída:</label>
                    <input name="dataHoraSaida"
                    value={voo.dataHoraSaida}
                    // type="datetime-local"
                    type="text" 
                    onChange={(e)=> handlerChange('dataHoraSaida',e.target.value)}
                    placeholder="30/09/2026 13:00:00"
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Data hora chegada:</label>
                    <input name="dataHoraChegada"
                    value={voo.dataHoraChegada}
                    // type="datetime-local"
                    type="text" 
                    onChange={(e)=> handlerChange('dataHoraChegada',e.target.value)}
                    placeholder="30/09/2026 15:00:00"
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>

                <div className="flex items-center justify-end gap-3 pt-2">
                    <Link href="/voos" className="text-sm font-semibold text-blue-600 hover:text-blue-800 px-4 py-2 rounded-lg transition-colors">Cancelar</Link>
                    <button type="submit" className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-5 py-2 rounded-lg transition-colors">Salvar</button>
                </div>
            </div>
        </form>
    );
}