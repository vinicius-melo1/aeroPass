"use client"

import Link from "next/link";
import { useParams } from "next/navigation";
import PassagemForm from "../../components/PassagemForm";

export default function EditarPassagem(){

    const parametro = useParams();
    const codigo = Number(parametro.codigo);
    return(
        <div className="min-h-screen bg-blue-50 px-4 py-8">
            <div className="max-w-lg mx-auto">
                <div className="mb-6">
                    <Link href="/passagens" className="text-sm font-semibold text-blue-600 hover:text-blue-800 transition-colors">← Voltar</Link>
                    <div className="mt-3">
                        <h1 className="text-2xl font-bold text-blue-900">Editar passagem {codigo}</h1>
                        <p className="text-sm text-blue-700 mt-1">Preencha os dados para editar a passagem</p>
                    </div>
                </div>
                <div>
                    <PassagemForm></PassagemForm>
                </div>
            </div>
        </div>
    );
}