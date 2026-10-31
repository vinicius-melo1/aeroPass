'use client'

import Footer from "../components/Footer";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";
import { useSelector } from "react-redux";
import { RootState } from "../redux/store";
import { useRouter } from "next/navigation";
import { useEffect } from "react";
export default function SistemaLayout({children}) {
    const usuario = useSelector((state:RootState)=> state.auth.usuario);

    const router = useRouter();

    useEffect(()=> {
        if(usuario == null) {
            router.push("/login");
        }
    })

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