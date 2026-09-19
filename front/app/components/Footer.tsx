

export default function Footer(){
    const anoAtual = new Date().getFullYear();
    return(
        <footer className="bg-white border-t border-blue-100 py-4 px-4">
            <div className="max-w-6xl mx-auto text-center">
                <p className="text-sm text-blue-800">&copy;{anoAtual} 
                    <span className="font-semibold text-blue-900"> AeroPass </span>
                    Todos os direitos reservados.
                </p>
            </div>
        </footer>
    );
}