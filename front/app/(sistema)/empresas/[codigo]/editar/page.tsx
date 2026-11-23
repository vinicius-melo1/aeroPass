"use client"

import { consultaEmpresaPorId } from "@/app/services/empresaService";
import { Empresa } from "@/app/types/empresa";
import Link from "next/link";
import { useParams, useRouter } from "next/navigation";
import { useState, useEffect } from "react";
import EmpresaForm from "../../components/EmpresasForm";

export default function EditarEmpresa(){

    const parametro = useParams();
    const codigo = Number(parametro.codigo);
    const [empresa,setEmpresa] = useState<Empresa|null>(null)
    const router = useRouter();
    useEffect(()=>{
        buscarDados();
    },[]);

    const buscarDados = async() => {
        const valorEmpresaBack = await consultaEmpresaPorId(codigo);

        if(valorEmpresaBack){
            setEmpresa(valorEmpresaBack);
        } else {
            router.push("/empresas");
            return
        }
    }

    if(!empresa) return(<div className="p-8"> Carregando dados...</div>)

    return(
        <div className="min-h-screen bg-blue-50 px-4 py-8">
            <div className="max-w-lg mx-auto">
                <div className="mb-6">
                    <Link href="/empresas" className="text-sm font-semibold text-blue-600 hover:text-blue-800 transition-colors">← Voltar</Link>
                    <div className="mt-3">
                        <h1 className="text-2xl font-bold text-blue-900">Editar empresa {codigo}</h1>
                        <p className="text-sm text-blue-700 mt-1">Preencha os dados para editar a empresa</p>
                    </div>
                </div>
                <div>
                    <EmpresaForm  empresaExistente={empresa}></EmpresaForm>
                </div>
            </div>
        </div>
    );
}