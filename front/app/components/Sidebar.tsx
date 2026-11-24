import Link from "next/link";
import { useAuth } from "../hooks/useAuth";


export default function Sidebar() {

    const { ehCliente } = useAuth();
    
    return(
        <aside className="w-64 shrink-0 min-h-screen sticky top-0 bg-white border-r border-blue-100 flex flex-col">
            <div className="text-xl font-bold text-blue-900 px-6 py-4 border-b border-blue-100 shadow-sm">
                AeroPass
            </div>
            <nav className="flex flex-col gap-1 px-3 py-4">
                <Link href="/home" className="px-3 py-2 rounded-lg text-sm font-medium text-blue-800 hover:bg-blue-50 transition-colors">Home</Link>
                <Link href="/usuarios" className="px-3 py-2 rounded-lg text-sm font-medium text-blue-800 hover:bg-blue-50 transition-colors">Usuários</Link>
                <Link href="/empresas" className="px-3 py-2 rounded-lg text-sm font-medium text-blue-800 hover:bg-blue-50 transition-colors">Empresas</Link>
                {!ehCliente && (
                    <Link href="/pilotos" className="px-3 py-2 rounded-lg text-sm font-medium text-blue-800 hover:bg-blue-50 transition-colors">Pilotos</Link>
                )}
                {!ehCliente && (
                    <Link href="/avioes" className="px-3 py-2 rounded-lg text-sm font-medium text-blue-800 hover:bg-blue-50 transition-colors">Aviões</Link>
                )}
                <Link href="/voos" className="px-3 py-2 rounded-lg text-sm font-medium text-blue-800 hover:bg-blue-50 transition-colors">Vôos</Link>
                <Link href="/voos/vistos" className="px-3 py-2 rounded-lg text-sm font-medium text-blue-800 hover:bg-blue-50 transition-colors">Ultimos Vôos visualizados</Link>
                <Link href="/passagens" className="px-3 py-2 rounded-lg text-sm font-medium text-blue-800 hover:bg-blue-50 transition-colors">Passagens</Link>
                {!ehCliente && (
                    <Link href="/passageiros" className="px-3 py-2 rounded-lg text-sm font-medium text-blue-800 hover:bg-blue-50 transition-colors">Passageiros</Link>
                )}
            </nav>
        </aside>
    );
}