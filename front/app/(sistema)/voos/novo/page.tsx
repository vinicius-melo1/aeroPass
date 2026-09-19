import Link from "next/link";
import VooForm from "../components/VooForm";


export default function CadastroVoo(){

    return(
        <div className="min-h-screen bg-blue-50 px-4 py-8">
            <div className="max-w-lg mx-auto">
                <div className="mb-6">
                    <Link href="/voos" className="text-sm font-semibold text-blue-600 hover:text-blue-800 transition-colors">← Voltar</Link>
                    <div className="mt-3">
                        <h1 className="text-2xl font-bold text-blue-900">Novo usuário</h1>
                        <p className="text-sm text-blue-700 mt-1">Preencha os dados para registrar um novo usuário</p>
                    </div>
                </div>
                <div>
                    <VooForm></VooForm>
                </div>
            </div>
        </div>
    );
}