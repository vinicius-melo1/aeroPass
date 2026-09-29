"use client"

import { Passageiro, PassageiroFormProps } from "@/app/types/passageiro";
import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";


export default function PassageirosForm({passageiroExistente}:PassageiroFormProps){ 
    const router = useRouter();

    const [passageiro,setPassageiro] = useState<Passageiro>
        (passageiroExistente ||
        new Passageiro(null,"","","","","","ATIVO"));

    const handlerChange = (campo: 'nome' | 'cpf' | 'passaporte' | 'telefone' | 'email', valor:string) => {
        setPassageiro(valorAnterior => 
            new Passageiro(
                valorAnterior.id,
                campo === 'nome' ? valor : valorAnterior.nome,
                campo === 'cpf' ? valor : valorAnterior.cpf,
                campo === 'passaporte' ? valor : valorAnterior.passaporte,
                campo === 'telefone' ? valor : valorAnterior.telefone,
                campo === 'email' ? valor : valorAnterior.email,
                valorAnterior.status,
            )
        )
    }

    const handlerSalvar = async (formData : FormData) =>{

        if(passageiroExistente) {
            var dadosRetorno = await axios.put<number>('http://localhost:8080/passageiros/'+passageiro.id,passageiro);

            if(dadosRetorno.status == 200) {
                alert("Passageiro foi salvo com sucesso!");
            } else {
                alert(dadosRetorno.data);
            }
        } else {
            var dadosRetorno = await axios.post<number>('http://localhost:8080/passageiros',passageiro);

            if(dadosRetorno.status == 200) {
                alert("Passageiro foi salvo com sucesso!");
            } else {
                alert(dadosRetorno.data);
            }
        }

        router.push("/passageiros");
    }
    return(
        <form action={handlerSalvar} className="bg-white rounded-2xl shadow-lg border border-blue-100 p-8 max-w-lg mx-auto">
            <div className="flex flex-col gap-5">
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Nome:</label>
                    <input name="nome" 
                        value={passageiro.nome}
                        onChange={(e)=>handlerChange('nome',e.target.value)}
                        placeholder="João da Silva"
                        required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">CPF:</label>
                    <input name="cpf" 
                        value={passageiro.cpf}
                        onChange={(e)=>handlerChange('cpf',e.target.value)}
                        placeholder="000.000.000-00"
                        required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Passaporte:</label>
                    <input name="passaporte" 
                        value={passageiro.passaporte}
                        onChange={(e)=>handlerChange('passaporte',e.target.value)}
                        placeholder="BR123456"
                        required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Telefone:</label>
                    <input name="telefone" 
                    value={passageiro.telefone}
                    onChange={(e)=>handlerChange('telefone',e.target.value)}
                    placeholder="+55 48 90000-0000"
                    required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">E-mail:</label>
                    <input name="email" 
                        value={passageiro.email}
                        onChange={(e)=>handlerChange('email',e.target.value)}
                        placeholder="email@gmail.com"
                        required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>

                <div className="flex items-center justify-end gap-3 pt-2">
                    <Link href="/passageiros" className="text-sm font-semibold text-blue-600 hover:text-blue-800 px-4 py-2 rounded-lg transition-colors">Cancelar</Link>
                    <button type="submit" className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-5 py-2 rounded-lg transition-colors">Salvar</button>
                </div>
            </div>
        </form>
    );
}