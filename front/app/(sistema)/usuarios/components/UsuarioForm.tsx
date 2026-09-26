"use client"

import { Usuario, UsuarioFormProps } from "@/app/types/usuario";
import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";


export default function UsuarioForm({usuarioExistente}:UsuarioFormProps){ 
    const router = useRouter();

    const [usuario,setUsuario ] = useState<Usuario>
        (usuarioExistente ||
        new Usuario(null,"","","","ATIVO",""));

    const handlerChange = (campo: 'nome' | 'email' | 'cpf' | 'senha', valor:string) => {
        setUsuario(valorAnterior => 
            new Usuario(
                valorAnterior.id,
                campo === 'nome' ? valor : valorAnterior.nome,
                campo === 'email' ? valor : valorAnterior.email,
                campo === 'cpf' ? valor : valorAnterior.cpf,
                valorAnterior.status,
                campo === 'senha' ? valor : valorAnterior.senha,
            )
        )
    }

    const handlerSalvar = async (formData : FormData) =>{

        if(usuarioExistente) {
            var dadosRetorno = await axios.put<number>('http://localhost:8080/usuarios'+usuario.id,usuario);

            if(dadosRetorno.status == 200) {
                alert("Usuário foi salvo com sucesso!");
            } else {
                alert(dadosRetorno.data);
            }
        } else {
            var dadosRetorno = await axios.post<number>('http://localhost:8080/usuarios/cadastro',usuario);

            if(dadosRetorno.status == 200) {
                alert("Usuário foi salvo com sucesso!");
            } else {
                alert(dadosRetorno.data);
            }
        }

        router.push("/usuarios");
    }
    return(
        <form action={handlerSalvar} className="bg-white rounded-2xl shadow-lg border border-blue-100 p-8 max-w-lg mx-auto">
            <div className="flex flex-col gap-5">
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Nome Completo:</label>
                    <input name="nome" 
                        value={usuario.nome}
                        onChange={(e)=>handlerChange('nome',e.target.value)}
                        placeholder="João da Silva"
                        required
                        className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                    />
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">CPF:</label>
                    <input name="cpf" 
                        value={usuario.cpf}
                        onChange={(e)=>handlerChange('cpf',e.target.value)}
                        placeholder="000.000.000-00"
                        required
                        className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                    />
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">E-mail:</label>
                    <input name="email" 
                        value={usuario.email}
                        onChange={(e)=>handlerChange('email',e.target.value)}
                        placeholder="email@gmail.com"
                        required
                        className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                    />
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Senha:</label>
                    <input name="senha" 
                        value={usuario.senha} 
                        type="password" 
                        onChange={(e)=>handlerChange('senha',e.target.value)}
                        placeholder="sua melhor senha"
                        required
                        className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                    />
                </div>

                <div className="flex items-center justify-end gap-3 pt-2">
                    <Link href="/usuarios" className="text-sm font-semibold text-blue-600 hover:text-blue-800 px-4 py-2 rounded-lg transition-colors">Cancelar</Link>
                    <button type="submit" className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-5 py-2 rounded-lg transition-colors">Salvar</button>
                </div>
            </div>
        </form>
    );
}