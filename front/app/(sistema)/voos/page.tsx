"use client"

import { buscarListaVoos } from "@/app/services/vooService";
import { Voo } from "@/app/types/voo";
import { useEffect, useState } from "react";
import VoosTable from "./components/VoosTable";

export default function Voos() {
    const [voos, setVoos] = useState<Voo[]>([]);

    useEffect(() => {
        carregarDados();
    }, []);

    const carregarDados = async () => {
        try {
            const dados = await buscarListaVoos();
            setVoos(dados);
        } catch {
            alert("Erro ao carregar dados");
        }
    }

    return <VoosTable voos={voos} />;
}