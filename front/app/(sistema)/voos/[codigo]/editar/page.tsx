"use client"

import Link from "next/link";
import { useParams, useRouter } from "next/navigation";
import VooForm from "../../components/VooForm";
import { Voo } from "@/app/types/voo";
import { useEffect, useState } from "react";
import axios from "axios";

export default function EditarVoo(){

    const parametro = useParams();
    const codigo = Number(parametro.codigo);
    const [voo,setVoo] = useState<Voo|null>(null)
    const router = useRouter();
    useEffect(()=>{
        buscarDados();
    },[]);

    const buscarDados = async() => {
        const valorVooBack = await axios.get<Voo>('http://localhost:8080/voos/'+codigo);

        if(valorVooBack.status == 200){
            setVoo(valorVooBack.data);
        } else {
            router.push("/voos");
            return
        }
    }

    if(!voo) return(<div className="p-8"> Carregando dados...</div>)
    
    return(
        <div className="min-h-screen bg-blue-50 px-4 py-8">
            <div className="max-w-lg mx-auto">
                <div className="mb-6">
                    <Link href="/voos" className="text-sm font-semibold text-blue-600 hover:text-blue-800 transition-colors">← Voltar</Link>
                    <div className="mt-3">
                        <h1 className="text-2xl font-bold text-blue-900">Editar vôo {codigo}</h1>
                        <p className="text-sm text-blue-700 mt-1">Preencha os dados para editar o vôo</p>
                    </div>
                </div>
                <div>
                    <VooForm vooExistente={voo}></VooForm>
                </div>
            </div>
        </div>
    );
}