"use client"

import { redefinirSenha } from "@/app/services/authService";
import { isAxiosError } from "axios";
import Link from "next/link";
import { useRouter, useSearchParams } from "next/navigation";
import { Suspense } from "react";

function RedefinirSenhaForm(){
    const router = useRouter();
    const searchParams = useSearchParams();
    const token = searchParams.get("token") ?? "";

    const handlerRedefinirSenha = async(formData:FormData) => {
        const novaSenha = formData.get("novaSenha")?.toString() ?? "";
        const confirmarSenha = formData.get("confirmarSenha")?.toString() ?? "";

        if(novaSenha !== confirmarSenha) {
            alert("As senhas não conferem!");
            return;
        }

        try {
            await redefinirSenha(token, novaSenha);
            alert("Senha redefinida com sucesso!");
            router.push("/auth/login");
        } catch (error) {
            if(isAxiosError(error) && typeof error.response?.data === "string") {
                alert(error.response.data);
            } else {
                alert("Não foi possível redefinir a senha. Solicite um novo link!");
            }
        }
    }

    return(<>
        <div className="min-h-screen flex items-center justify-center bg-blue-50 px-4">
            <div className="w-full max-w-md bg-white rounded-2xl shadow-lg p-8 border border-blue-100">
                <div className="mb-8 text-center">
                    <h1 className="text-2xl font-bold text-blue-900">Redefinir senha</h1>
                    <p className="mt-2 text-sm text-blue-800">Digite sua nova senha.</p>
                </div>
                {token === "" ? (
                    <div className="space-y-5">
                        <div className="rounded-lg bg-blue-50 border border-blue-200 px-4 py-3 text-sm text-blue-900">
                            Link inválido. Solicite um novo link para redefinir sua senha.
                        </div>
                        <Link href="/auth/esqueci-senha" className="block text-center text-blue-800 hover:text-blue-900">Solicitar novo link</Link>
                    </div>
                ) : (
                    <form action={handlerRedefinirSenha} className="space-y-5">
                        <div className="flex flex-col gap-1">
                            <label className="text-sm font-medium text-blue-800">Nova senha</label>
                            <input name="novaSenha" type="password" required className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                        </div>
                        <div className="flex flex-col gap-1">
                            <label className="text-sm font-medium text-blue-800">Confirmar nova senha</label>
                            <input name="confirmarSenha" type="password" required className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                        </div>
                        <button type="submit" className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2.5 rounded-lg transition-colors">Redefinir senha</button>
                        <div className="flex justify-between w-full">
                            <Link href="/auth/login" className="text-blue-800 hover:text-blue-900">Voltar para o login</Link>
                        </div>
                    </form>
                )}
            </div>
        </div>
    </>);
}

export default function RedefinirSenha(){
    return(
        <Suspense>
            <RedefinirSenhaForm/>
        </Suspense>
    );
}