'use client'

import { esqueciSenha } from "@/app/services/authService";
import Link from "next/link";
import { useState } from "react";

export default function EsqueciSenha(){
    const [enviado, setEnviado] = useState(false);

    const handlerEsqueciSenha = async(formData:FormData) => {
        try {
            const emailTela = formData.get("email")?.toString() ?? "";

            await esqueciSenha(emailTela);
            setEnviado(true);
        } catch (error) {
            alert("Não foi possível enviar o e-mail. Tente novamente!");
        }
    }

    return(<>
        <div className="min-h-screen flex items-center justify-center bg-blue-50 px-4">
            <div className="w-full max-w-md bg-white rounded-2xl shadow-lg p-8 border border-blue-100">
                <div className="mb-8 text-center">
                    <h1 className="text-2xl font-bold text-blue-900">Esqueceu sua senha?</h1>
                    <p className="mt-2 text-sm text-blue-800">Informe seu e-mail e enviaremos um link para você criar uma nova senha.</p>
                </div>
                {enviado ? (
                    <div className="space-y-5">
                        <div className="rounded-lg bg-blue-50 border border-blue-200 px-4 py-3 text-sm text-blue-900">
                            Se o e-mail estiver cadastrado, você receberá um link para redefinir sua senha. O link é válido por 30 minutos.
                        </div>
                        <Link href="/auth/login" className="block text-center text-blue-800 hover:text-blue-900">Voltar para o login</Link>
                    </div>
                ) : (
                    <form action={handlerEsqueciSenha} className="space-y-5">
                        <div className="flex flex-col gap-1">
                            <label className="text-sm font-medium text-blue-800">E-mail</label>
                            <input name="email" type="email" required className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                        </div>
                        <button type="submit" className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2.5 rounded-lg transition-colors">Enviar link</button>
                        <div className="flex justify-between w-full">
                            <Link href="/auth/login" className="text-blue-800 hover:text-blue-900">Voltar para o login</Link>
                            <Link href="/auth/cadastre-se" className="text-blue-800 hover:text-blue-900">Cadastre-se</Link>
                        </div>
                    </form>
                )}
            </div>
        </div>
    </>);
}