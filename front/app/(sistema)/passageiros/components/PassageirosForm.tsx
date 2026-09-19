import Link from "next/link";


export default function PassageirosForm(){ 
    return(
        <form className="bg-white rounded-2xl shadow-lg border border-blue-100 p-8 max-w-lg mx-auto">
            <div className="flex flex-col gap-5">
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Nome:</label>
                    <input name="nome" className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">CPF:</label>
                    <input name="cpf" className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Passaporte:</label>
                    <input name="passaporte" className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Telefone:</label>
                    <input name="telefone" className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">E-mail:</label>
                    <input name="email" className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>

                <div className="flex items-center justify-end gap-3 pt-2">
                    <Link href="/passageiros" className="text-sm font-semibold text-blue-600 hover:text-blue-800 px-4 py-2 rounded-lg transition-colors">Cancelar</Link>
                    <button type="submit" className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-5 py-2 rounded-lg transition-colors">Salvar</button>
                </div>
            </div>
        </form>
    );
}