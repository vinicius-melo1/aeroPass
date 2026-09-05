'use client'

import { useRouter } from "next/navigation";

export default function Login(){
    const router = useRouter();
    const handlerLogin = async(formData:FormData) => {
        router.push("/home")
    }
    return(<>
        <div className="min-h-screen flex items-center justify-center bg-blue-50 px-4">
            <div className="w-full max-w-md bg-white rounded-2xl shadow-lg p-8 border border-blue-100">
                <div className="mb-8 text-center">
                    <h1 className="text-2xl font-bold text-blue-900">Entrar no sistema</h1>
                </div>
                <form action={handlerLogin} className="space-y-5">    
                    <div className="flex flex-col gap-1">
                        <label className="text-sm font-medium text-blue-800">E-mail</label>
                        <input name="email" className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                    </div>
                    <div className="flex flex-col gap-1">
                        <label className="text-sm font-medium text-blue-800">Senha</label>
                        <input name="senha" type="password" className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                    </div>
                    <button type="submit" className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2.5 rounded-lg transition-colors">Entrar</button>
                </form>
            </div>
        </div>
    </>);
}