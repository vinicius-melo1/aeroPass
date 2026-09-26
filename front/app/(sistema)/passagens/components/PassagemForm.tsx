"use client"

import { Passagem, PassagemFormProps } from "@/app/types/passagem";
import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";


export default function PassagemForm({passagemExistente}:PassagemFormProps){ 
    const router = useRouter();

    const [passagem,setPassagem ] = useState<Passagem>
        (passagemExistente ||
        new Passagem(null,"","",0,"","ATIVO"));

    const handlerChange = (campo: 'codigoAssento' | 'dataCompra' | 'valor' | 'formaPagamento', valor:string) => {
        setPassagem(valorAnterior => 
            new Passagem(
                valorAnterior.id,
                campo === 'codigoAssento' ? valor : valorAnterior.codigoAssento,
                campo === 'dataCompra' ? valor : valorAnterior.dataCompra,
                campo === 'valor' ? Number(valor) : valorAnterior.valor,
                campo === 'formaPagamento' ? valor : valorAnterior.formaPagamento,
                valorAnterior.status,
            )
        )
    }

    const handlerSalvar = async (formData : FormData) =>{

        if(passagemExistente) {
            var dadosRetorno = await axios.put<number>('http://localhost:8080/passagens'+passagem.id,passagem);

            if(dadosRetorno.status == 200) {
                alert("Passagem foi salva com sucesso!");
            } else {
                alert(dadosRetorno.data);
            }
        } else {
            var dadosRetorno = await axios.post<number>('http://localhost:8080/passagens/cadastro',passagem);

            if(dadosRetorno.status == 200) {
                alert("Passagem foi salva com sucesso!");
            } else {
                alert(dadosRetorno.data);
            }
        }

        router.push("/passagens");
    }
    return(
        <form action={handlerSalvar} className="bg-white rounded-2xl shadow-lg border border-blue-100 p-8 max-w-lg mx-auto">
            <div className="flex flex-col gap-5">
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Código do assento:</label>
                    <input 
                    value={passagem.codigoAssento}
                    onChange={(e)=>handlerChange('codigoAssento',e.target.value)}
                    name="codigoAssento" 
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Data de compra:</label>
                    <input name="dataCompra"
                    value={passagem.dataCompra}
                    onChange={(e)=>handlerChange('dataCompra',e.target.value)} 
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Valor de passagem:</label>
                    <input name="valor" 
                    value={passagem.valor}
                    onChange={(e)=>handlerChange('valor',e.target.value)} 
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Forma de pagamento:</label>
                    <input name="formaPagamento" 
                    value={passagem.formaPagamento}
                    onChange={(e)=>handlerChange('formaPagamento',e.target.value)} 
                    type="password" 
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