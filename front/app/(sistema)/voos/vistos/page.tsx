"use client"

import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import VoosTable from "../components/VoosTable";
import { RootState } from "@/app/redux/store";


export default function VoosVistos() {
    const voosVistos = useSelector((state: RootState) => state.voosVistos.voos);

    const [montado, setMontado] = useState(false);
    useEffect(() => setMontado(true), []);

    return <VoosTable voos={montado ? voosVistos : []} />;
}