"use client"

import Link from "next/link";
import { useParams, useRouter } from "next/navigation";
import PassageirosForm from "../../components/PassageirosForm";
import { Passageiro } from "@/app/types/passageiro";
import { useEffect, useState } from "react";
import axios from "axios";

export default function EditarPassagem(){

    const parametro = useParams();
    const codigo = Number(parametro.codigo);
    const [passageiro,setPassageiro] = useState<Passageiro|null>(null)
    const router = useRouter();
    useEffect(()=>{
        buscarDados();
    },[]);

    const buscarDados = async() => {
        const valorPassageiroBack = await axios.get<Passageiro>('http://localhost:8080/passageiros/'+codigo);

        if(valorPassageiroBack.status == 200){
            setPassageiro(valorPassageiroBack.data);
        } else {
            router.push("/passageiros");
            return
        }
    }

    if(!passageiro) return(<div className="p-8"> Carregando dados...</div>)

    return(
        <div className="min-h-screen bg-blue-50 px-4 py-8">
            <div className="max-w-lg mx-auto">
                <div className="mb-6">
                    <Link href="/passageiros" className="text-sm font-semibold text-blue-600 hover:text-blue-800 transition-colors">← Voltar</Link>
                    <div className="mt-3">
                        <h1 className="text-2xl font-bold text-blue-900">Editar passageiro {codigo}</h1>
                        <p className="text-sm text-blue-700 mt-1">Preencha os dados para editar a passageiro</p>
                    </div>
                </div>
                <div>
                    <PassageirosForm passageiroExistente={passageiro}></PassageirosForm>
                </div>
            </div>
        </div>
    );
}