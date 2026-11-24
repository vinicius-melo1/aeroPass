"use client"

import { cadastreSe } from "@/app/services/usuarioService";
import { Passageiro } from "@/app/types/passageiro";
import { Usuario } from "@/app/types/usuario";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function Cadastrese(){
    const router = useRouter();


    const handlerSalvar = async (formData : FormData) => {
        const nome = formData.get("nome")?.toString() ?? "";
        const email = formData.get("email")?.toString() ?? "";
        const cpf = formData.get("cpf")?.toString() ?? "";
        const telefone = formData.get("telefone")?.toString() ?? "";
        const passaporte = formData.get("passaporte")?.toString() ?? "";
        const senha = formData.get("senha")?.toString() ?? "";

        const usuario = new Usuario(null, nome, email, cpf, "ATIVO", senha);
        const passageiro = new Passageiro(null, nome, cpf, passaporte, telefone, email, "ATIVO");

        var dadosRetorno = await cadastreSe(usuario, passageiro);

        if(dadosRetorno) {
            alert("Cadastro realizado com sucesso!");
            router.push("/auth/login");
        } else {
            alert("Erro ao realizar cadastro");
        }
    }

    return(<>
        <div className="min-h-screen flex items-center justify-center bg-blue-50 px-4 py-8">
            <div className="w-full max-w-md bg-white rounded-2xl shadow-lg p-8 border border-blue-100">
                <div className="mb-8 text-center">
                    <h1 className="text-2xl font-bold text-blue-900">Cadastre-se</h1>
                </div>
                <form action={handlerSalvar} className="space-y-5">
                    <div className="flex flex-col gap-1">
                        <label className="text-sm font-medium text-blue-800">Nome</label>
                        <input name="nome" type="text" className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                    </div>
                    <div className="flex flex-col gap-1">
                        <label className="text-sm font-medium text-blue-800">E-mail</label>
                        <input name="email" type="email" className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                    </div>
                    <div className="flex flex-col gap-1">
                        <label className="text-sm font-medium text-blue-800">CPF</label>
                        <input name="cpf" type="text" className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                    </div>
                    <div className="flex flex-col gap-1">
                        <label className="text-sm font-medium text-blue-800">Telefone</label>
                        <input name="telefone" type="tel" className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                    </div>
                    <div className="flex flex-col gap-1">
                        <label className="text-sm font-medium text-blue-800">Passaporte</label>
                        <input name="passaporte" type="text" className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                    </div>
                    <div className="flex flex-col gap-1">
                        <label className="text-sm font-medium text-blue-800">Senha</label>
                        <input name="senha" type="password" className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                    </div>
                    <button type="submit" className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2.5 rounded-lg transition-colors">Cadastrar</button>
                    <div className="flex justify-center w-full">
                        <Link href="/auth/login" className="text-blue-800 hover:text-blue-900">Já tem conta? Entrar</Link>
                    </div>
                </form>
            </div>
        </div>
    </>);

   
}