import Footer from "../components/Footer";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";

export default function SistemaLayout({children}) {
    return( 
    <div className="min-h-screen flex bg-blue-50">
        <Sidebar/> 

        <div className="flex-1 flex flex-col min-w-0">
            <Header/>
            <main className="flex-1 px-4 py-6 md:px-8 md:py-8">
                {children}
            </main>
            <Footer/>
        </div>
    </div>);
}