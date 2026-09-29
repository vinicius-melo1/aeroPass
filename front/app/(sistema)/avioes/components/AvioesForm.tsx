"use client"

import { Aviao, AviaoFormProps } from "@/app/types/aviao";
import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";


export default function AvioesForm({aviaoExistente}:AviaoFormProps){ 
    const router = useRouter();

    const [aviao,setAviao ] = useState<Aviao>
        (aviaoExistente ||
        new Aviao(null,"","","","ATIVO"));

    const handlerChange = (campo: 'modelo' | 'fabricante' | 'numeroSerie', valor:string) => {
        setAviao(valorAnterior => 
            new Aviao(
                valorAnterior.id,
                campo === 'modelo' ? valor : valorAnterior.modelo,
                campo === 'fabricante' ? valor : valorAnterior.fabricante,
                campo === 'numeroSerie' ? valor : valorAnterior.numeroSerie,
                valorAnterior.status,
            )
        )
    }

    const handlerSalvar = async (formData : FormData) =>{

        if(aviaoExistente) {
            var dadosRetorno = await axios.put<number>('http://localhost:8080/avioes/'+aviao.id,aviao);

            if(dadosRetorno.status == 200) {
                alert("Avião foi salvo com sucesso!");
            } else {
                alert(dadosRetorno.data);
            }
        } else {
            var dadosRetorno = await axios.post<number>('http://localhost:8080/avioes',aviao);

            if(dadosRetorno.status == 200) {
                alert("Avião foi salvo com sucesso!");
            } else {
                alert(dadosRetorno.data);
            }
        }

        router.push("/avioes");
    }
    
    return(
        <form action={handlerSalvar} className="bg-white rounded-2xl shadow-lg border border-blue-100 p-8 max-w-lg mx-auto">
            <div className="flex flex-col gap-5">
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Modelo:</label>
                    <input name="modelo" 
                        value={aviao.modelo}
                        onChange={(e)=>handlerChange('modelo',e.target.value)}
                        placeholder="Airbus A320"
                        required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Fabricante:</label>
                    <input name="fabricante" 
                        value={aviao.fabricante}
                        onChange={(e)=>handlerChange('fabricante',e.target.value)}
                        placeholder="Boeing"
                        required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Número de série:</label>
                    <input name="numeroSerie" 
                        value={aviao.numeroSerie}
                        onChange={(e)=>handlerChange('numeroSerie',e.target.value)}
                        placeholder="Digite o número de série de acordo com o fabricante"
                        required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>

                <div className="flex items-center justify-end gap-3 pt-2">
                    <Link href="/avioes" className="text-sm font-semibold text-blue-600 hover:text-blue-800 px-4 py-2 rounded-lg transition-colors">Cancelar</Link>
                    <button type="submit" className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-5 py-2 rounded-lg transition-colors">Salvar</button>
                </div>
            </div>
        </form>
    );
}