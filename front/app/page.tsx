import Image from "next/image";
import Link from "next/link";

export default function Home() {
  return (
    <div className="min-h-screen flex flex-col bg-slate-50 text-slate-800 font-sans">
      
      {/* HEADER */}
      <header className="flex justify-between items-center py-6 px-8 md:px-16 bg-white shadow-sm sticky top-0 z-50">
        <a href="#" className="flex items-center gap-3 text-decoration-none">
          <div className="w-9 h-9 bg-sky-600 rounded-lg flex items-center justify-center text-white font-bold -rotate-45 shadow-md">
            ✈
          </div>
          <span className="text-2xl font-bold text-slate-900 tracking-tight">
            Aero<span className="text-sky-600">Pass</span>
          </span>
        </a>
        
        <div className="flex items-center gap-4">
          <Link href="/login" className="border-2 border-sky-600 text-sky-600 hover:bg-sky-600 hover:text-white px-6 py-2 rounded-md font-semibold transition-all duration-200 cursor-pointer">Entrar</Link>
        </div>
      </header>

      {/* HERO SECTION */}
      <section className="flex flex-col items-center text-center py-20 px-6 bg-gradient-to-br from-sky-600 to-sky-700 text-white">
        <h1 className="text-4xl md:text-5xl font-extrabold max-w-3xl mb-6 leading-tight">
          O futuro da aviação na palma da sua mão
        </h1>
        <p className="text-lg md:text-xl max-w-2xl mb-10 opacity-90 leading-relaxed">
          Desenvolvemos um sistema inteligente de companhias aéreas focado no seu conforto. 
          Agende seus voos sem dor de cabeça, escolha os melhores assentos e diga adeus ao overbooking.
        </p>
        <div>
          <a href="" 
            // onClick={() => alert('Em breve!')}
            className="bg-white text-sky-600 hover:bg-slate-100 px-8 py-3.5 rounded-md font-bold text-base shadow-lg transition-transform transform hover:-translate-y-0.5 cursor-pointer"
          >
            Conhecer o Sistema
          </a>
        </div>
      </section>

      {/* FEATURES / STORY SECTION */}
      <section className="py-20 px-6 max-w-7xl mx-auto w-full grid grid-cols-1 md:grid-cols-3 gap-8">
        
        <div className="bg-white p-8 rounded-xl border border-slate-200 shadow-sm hover:shadow-md transition-shadow">
          <h3 className="text-xl font-bold text-slate-900 mb-3">Voos sem Overbooking</h3>
          <p className="text-slate-600 leading-relaxed">
            Nossa tecnologia de ponta gerencia assentos e vagas em tempo real, garantindo 
            que o seu lugar reservado esteja sempre garantido no momento do embarque.
          </p>
        </div>

        <div className="bg-white p-8 rounded-xl border border-slate-200 shadow-sm hover:shadow-md transition-shadow">
          <h3 className="text-xl font-bold text-slate-900 mb-3">Seu Assento Favorito</h3>
          <p className="text-slate-600 leading-relaxed">
            Navegue pelo mapa de bordo interativo e selecione com facilidade o lugar perfeito 
            para aproveitar a sua viagem com o máximo de conforto.
          </p>
        </div>

        <div className="bg-white p-8 rounded-xl border border-slate-200 shadow-sm hover:shadow-md transition-shadow">
          <h3 className="text-xl font-bold text-slate-900 mb-3">Experiência Descomplicada</h3>
          <p className="text-slate-600 leading-relaxed">
            Um sistema feito para passageiros. Interface intuitiva, rápida e limpa que 
            conecta você ao seu destino com poucos cliques.
          </p>
        </div>

      </section>

      {/* FOOTER */}
      <footer className="text-center py-8 bg-slate-900 text-slate-400 text-sm mt-auto">
        <p>&copy; 2026 AeroPass. Todos os direitos reservados. Conectando você com conforto e segurança.</p>
      </footer>

    </div>
  );
}
