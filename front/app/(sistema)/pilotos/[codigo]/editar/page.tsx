"use client"

import { consultaPilotoPorId } from "@/app/services/pilotoService";
import { Piloto } from "@/app/types/piloto";
import Link from "next/link";
import { useParams, useRouter } from "next/navigation";
import { useState, useEffect } from "react";
import PilotosForm from "../../components/PilotosForm";

export default function EditarPiloto(){

    const parametro = useParams();
    const codigo = Number(parametro.codigo);
    const [piloto,setPiloto] = useState<Piloto|null>(null)
    const router = useRouter();
    useEffect(()=>{
        buscarDados();
    },[]);

    const buscarDados = async() => {
        const valorPilotoBack = await consultaPilotoPorId(codigo);

        if(valorPilotoBack){
            setPiloto(valorPilotoBack);
        } else {
            router.push("/pilotos");
            return
        }
    }

    if(!piloto) return(<div className="p-8"> Carregando dados...</div>)

    return(
        <div className="min-h-screen bg-blue-50 px-4 py-8">
            <div className="max-w-lg mx-auto">
                <div className="mb-6">
                    <Link href="/pilotos" className="text-sm font-semibold text-blue-600 hover:text-blue-800 transition-colors">← Voltar</Link>
                    <div className="mt-3">
                        <h1 className="text-2xl font-bold text-blue-900">Editar piloto {codigo}</h1>
                        <p className="text-sm text-blue-700 mt-1">Preencha os dados para editar o piloto</p>
                    </div>
                </div>
                <div>
                    <PilotosForm pilotoExistente={piloto}></PilotosForm>
                </div>
            </div>
        </div>
    );
}