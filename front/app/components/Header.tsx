

export default function Header(){

    return(
        <header className="w-full bg-white border-b border-blue-100 shadow-sm">
            <div className="flex items-center justify-between px-4 py-3">
                <div className="flex items-center gap-3">
                    <div className="w-9 h-9 flex items-center justify-center rounded-full bg-blue-100 text-blue-700">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="white" className="w-5 h-5 -rotate-45">
                            <path d="M21 16v-2l-8-5V3.5a1.5 1.5 0 0 0-3 0V9l-8 5v2l8-2.5V19l-2.5 1.8V22l3.5-1 3.5 1v-1.2L12 19v-5.5Z"/>
                        </svg>
                    </div>
                    <span className="text-sm font-medium text-blue-900">Usuário Vinícius N. de Melo</span>
                </div>
                <button className="text-sm font-semibold text-blue-600 hover:text-blue-800 hover:bg-blue-50 px-3 py-1.5 rounded-lg transition-colors">Sair</button>
            </div>
        </header>
    );
}