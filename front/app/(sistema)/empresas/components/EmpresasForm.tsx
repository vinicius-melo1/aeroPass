"use client"

import { atualizarEmpresa, salvarEmpresa } from "@/app/services/empresaService";
import { EmpresaFormProps, Empresa } from "@/app/types/empresa";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function EmpresasForm({empresaExistente}:EmpresaFormProps){ 
    const router = useRouter();

    const [empresa,setEmpresa ] = useState<Empresa>
        (empresaExistente ||
        new Empresa(null,"","","", "ATIVO"));

    const handlerChange = (campo: 'razaoSocial' | 'cnpj' | 'nomeFantasia', valor:string) => {
        setEmpresa(valorAnterior => 
            new Empresa(
                valorAnterior.id,
                campo === 'razaoSocial' ? valor : valorAnterior.razaoSocial,
                campo === 'cnpj' ? valor : valorAnterior.cnpj,
                campo === 'nomeFantasia' ? valor : valorAnterior.nomeFantasia,
                valorAnterior.status,
            )
        )
    }

    const handlerSalvar = async (formData : FormData) =>{

        if(empresaExistente) {
            var dadosRetorno = await atualizarEmpresa(empresa);

            if(dadosRetorno) {
                alert("Empresa foi salva com sucesso!");
            } else {
                alert("Erro ao salvar empresa");
            }
        } else {
            var dadosRetorno = await salvarEmpresa(empresa);

            if(dadosRetorno) {
                alert("Empresa foi salva com sucesso!");
            } else {
                alert("Erro ao salvar empresa");
            }
        }

        router.push("/empresas");
    }
    
    return(
        <form action={handlerSalvar} className="bg-white rounded-2xl shadow-lg border border-blue-100 p-8 max-w-lg mx-auto">
            <div className="flex flex-col gap-5">
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Razão Social:</label>
                    <input name="razaoSocial" 
                        value={empresa.razaoSocial}
                        onChange={(e)=>handlerChange('razaoSocial',e.target.value)}
                        placeholder="Razão social da empresa"
                        required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">CNPJ:</label>
                    <input name="cnpj" 
                        value={empresa.cnpj}
                        onChange={(e)=>handlerChange('cnpj',e.target.value)}
                        placeholder="00.000.000/0000-00"
                        required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Nome Fantasia:</label>
                    <input name="nomeFantasia" 
                        value={empresa.nomeFantasia}
                        onChange={(e)=>handlerChange('nomeFantasia',e.target.value)}
                        placeholder="Nome Fantasia da empresa"
                        required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>

                <div className="flex items-center justify-end gap-3 pt-2">
                    <Link href="/empresas" className="text-sm font-semibold text-blue-600 hover:text-blue-800 px-4 py-2 rounded-lg transition-colors">Cancelar</Link>
                    <button type="submit" className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-5 py-2 rounded-lg transition-colors">Salvar</button>
                </div>
            </div>
        </form>
    );
}