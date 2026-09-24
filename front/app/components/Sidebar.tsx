import Link from "next/link";


export default function Sidebar() {

    return(
        <aside className="w-64 shrink-0 min-h-screen sticky top-0 bg-white border-r border-blue-100 flex flex-col">
            <div className="text-xl font-bold text-blue-900 px-6 py-4 border-b border-blue-100 shadow-sm">
                AeroPass
            </div>
            <nav className="flex flex-col gap-1 px-3 py-4">
                <Link href="/home" className="px-3 py-2 rounded-lg text-sm font-medium text-blue-800 hover:bg-blue-50 transition-colors">Home</Link>
                <Link href="/usuarios" className="px-3 py-2 rounded-lg text-sm font-medium text-blue-800 hover:bg-blue-50 transition-colors">Usuários</Link>
                <Link href="/voos" className="px-3 py-2 rounded-lg text-sm font-medium text-blue-800 hover:bg-blue-50 transition-colors">Vôos</Link>
                <Link href="/passageiros" className="px-3 py-2 rounded-lg text-sm font-medium text-blue-800 hover:bg-blue-50 transition-colors">Passageiros</Link>
                <Link href="/passagens" className="px-3 py-2 rounded-lg text-sm font-medium text-blue-800 hover:bg-blue-50 transition-colors">Passagens</Link>
                <Link href="/avioes" className="px-3 py-2 rounded-lg text-sm font-medium text-blue-800 hover:bg-blue-50 transition-colors">Aviões</Link>
            </nav>
        </aside>
    );
}