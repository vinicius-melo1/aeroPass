"use client"

import { atualizarPiloto, salvarPiloto } from "@/app/services/pilotoService";
import { PilotoFormProps, Piloto } from "@/app/types/piloto";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function PilotosForm({pilotoExistente}:PilotoFormProps){ 
    const router = useRouter();

    const [piloto,setPiloto ] = useState<Piloto>
        (pilotoExistente ||
        new Piloto(null,"","","","ATIVO"));

    const handlerChange = (campo: 'nome' | 'canac' | 'tipoPiloto', valor:string) => {
        setPiloto(valorAnterior => 
            new Piloto(
                valorAnterior.id,
                campo === 'nome' ? valor : valorAnterior.nome,
                campo === 'canac' ? valor : valorAnterior.canac,
                campo === 'tipoPiloto' ? valor : valorAnterior.tipoPiloto,
                valorAnterior.status,
            )
        )
    }

    const handlerSalvar = async (formData : FormData) =>{

        if(pilotoExistente) {
            var dadosRetorno = await atualizarPiloto(piloto);

            if(dadosRetorno) {
                alert("Piloto foi salvo com sucesso!");
            } else {
                alert("Erro ao salvar piloto");
            }
        } else {
            var dadosRetorno = await salvarPiloto(piloto);

            if(dadosRetorno) {
                alert("Piloto foi salvo com sucesso!");
            } else {
                alert("Erro ao salvar piloto");
            }
        }

        router.push("/pilotos");
    }
    
    return(
        <form action={handlerSalvar} className="bg-white rounded-2xl shadow-lg border border-blue-100 p-8 max-w-lg mx-auto">
            <div className="flex flex-col gap-5">
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Nome:</label>
                    <input name="nome" 
                        value={piloto.nome}
                        onChange={(e)=>handlerChange('nome',e.target.value)}
                        placeholder="João da Silva"
                        required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">CANAC:</label>
                    <input name="canac" 
                        value={piloto.canac}
                        onChange={(e)=>handlerChange('canac',e.target.value)}
                        placeholder="123456"
                        required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Tipo de piloto:</label>
                    <input name="tipoPiloto" 
                        value={piloto.tipoPiloto}
                        onChange={(e)=>handlerChange('tipoPiloto',e.target.value)}
                        placeholder="Comandante"
                        required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>

                <div className="flex items-center justify-end gap-3 pt-2">
                    <Link href="/pilotos" className="text-sm font-semibold text-blue-600 hover:text-blue-800 px-4 py-2 rounded-lg transition-colors">Cancelar</Link>
                    <button type="submit" className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-5 py-2 rounded-lg transition-colors">Salvar</button>
                </div>
            </div>
        </form>
    );
}