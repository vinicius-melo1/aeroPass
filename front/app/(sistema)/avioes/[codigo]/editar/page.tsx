"use client"

import Link from "next/link";
import { useParams, useRouter } from "next/navigation";
import AvioesForm from "../../components/AvioesForm";
import { Aviao } from "@/app/types/aviao";
import { useEffect, useState } from "react";
import axios from "axios";

export default function EditarAviao(){

    const parametro = useParams();
    const codigo = Number(parametro.codigo);
    const [aviao,setAviao] = useState<Aviao|null>(null)
    const router = useRouter();
    useEffect(()=>{
        buscarDados();
    },[]);

    const buscarDados = async() => {
        const valorAviaoBack = await axios.get<Aviao>('http://localhost:8080/avioes/'+codigo);

        if(valorAviaoBack.status == 200){
            setAviao(valorAviaoBack.data);
        } else {
            router.push("/avioes");
            return
        }
    }

    if(!aviao) return(<div className="p-8"> Carregando dados...</div>)

    return(
        <div className="min-h-screen bg-blue-50 px-4 py-8">
            <div className="max-w-lg mx-auto">
                <div className="mb-6">
                    <Link href="/avioes" className="text-sm font-semibold text-blue-600 hover:text-blue-800 transition-colors">← Voltar</Link>
                    <div className="mt-3">
                        <h1 className="text-2xl font-bold text-blue-900">Editar avião {codigo}</h1>
                        <p className="text-sm text-blue-700 mt-1">Preencha os dados para editar o avião</p>
                    </div>
                </div>
                <div>
                    <AvioesForm aviaoExistente={aviao}></AvioesForm>
                </div>
            </div>
        </div>
    );
}