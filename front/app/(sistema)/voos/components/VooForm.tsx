"use client"

import { buscarListaAvioes } from "@/app/services/aviaoService";
import { buscarListaPilotos } from "@/app/services/pilotoService";
import { atualizarVoo, salvarVoo } from "@/app/services/vooService";
import { Aviao } from "@/app/types/aviao";
import { Piloto } from "@/app/types/piloto";
import { Voo, VooFormProps } from "@/app/types/voo";
import { mascararDataHora } from "@/app/utils/mascaras";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";


export default function VooForm({vooExistente}:VooFormProps){ 
    const router = useRouter();
    const [avioes, setAvioes] = useState<Aviao[]>([]);
    const [pilotos, setPilotos] = useState<Piloto[]>([]);

    const [voo,setVoo ] = useState<Voo>
        (vooExistente ||
        new Voo(null,"",0,0,"","","","","","ATIVO"));

    useEffect(() => {
        const carregarOpcoes = async () => {
            try {
                const [listaAvioes, listaPilotos] = await Promise.all([
                    buscarListaAvioes(),
                    buscarListaPilotos(),
                ]);
                setAvioes(listaAvioes);
                setPilotos(listaPilotos);
            } catch {
                alert("Erro ao carregar aviões e pilotos");
            }
        };

    carregarOpcoes();
}, []);

    const handlerChange = (campo: 'codigoVoo' | 'capacidade' | 'assentosDisponiveis' | 'cidadeOrigem' | 'cidadeDestino' | 'dataHoraSaida' | 'dataHoraChegadaPrevista' | 'dataHoraChegada', valor:string) => {
        setVoo(valorAnterior => 
            new Voo(
                valorAnterior.id,
                campo === 'codigoVoo' ? valor : valorAnterior.codigoVoo,
                campo === 'capacidade' ? Number(valor) : valorAnterior.capacidade,
                campo === 'assentosDisponiveis' ? Number(valor) : valorAnterior.assentosDisponiveis,
                campo === 'cidadeOrigem' ? valor : valorAnterior.cidadeOrigem,
                campo === 'cidadeDestino' ? valor : valorAnterior.cidadeDestino,
                campo === 'dataHoraSaida' ? valor : valorAnterior.dataHoraSaida,
                campo === 'dataHoraChegadaPrevista' ? valor : valorAnterior.dataHoraChegadaPrevista,
                campo === 'dataHoraChegada' ? valor : valorAnterior.dataHoraChegada,
                valorAnterior.status,
                valorAnterior.aviaoId,
                valorAnterior.pilotosIds,
                valorAnterior.aviaoModelo,
                valorAnterior.pilotosCanac,
            )
        )
    }

    const handlerAviao = (aviaoId: number | null) => {
        setVoo(valorAnterior =>
            new Voo(
                valorAnterior.id,
                valorAnterior.codigoVoo,
                valorAnterior.capacidade,
                valorAnterior.assentosDisponiveis,
                valorAnterior.cidadeOrigem,
                valorAnterior.cidadeDestino,
                valorAnterior.dataHoraSaida,
                valorAnterior.dataHoraChegadaPrevista,
                valorAnterior.dataHoraChegada,
                valorAnterior.status,
                aviaoId,
                valorAnterior.pilotosIds,
                valorAnterior.aviaoModelo,
                valorAnterior.pilotosCanac,
            )
        )
    }

    const handlerPilotos = (pilotosIds: number[]) => {
        setVoo(valorAnterior =>
            new Voo(
                valorAnterior.id,
                valorAnterior.codigoVoo,
                valorAnterior.capacidade,
                valorAnterior.assentosDisponiveis,
                valorAnterior.cidadeOrigem,
                valorAnterior.cidadeDestino,
                valorAnterior.dataHoraSaida,
                valorAnterior.dataHoraChegadaPrevista,
                valorAnterior.dataHoraChegada,
                valorAnterior.status,
                valorAnterior.aviaoId,
                pilotosIds,
                valorAnterior.aviaoModelo,
                valorAnterior.pilotosCanac,
            )
        )
    }

    const handlerSalvar = async (formData : FormData) =>{
        if(vooExistente) {
            var dadosRetorno = await atualizarVoo(voo);

            if(dadosRetorno) {
                alert("Vôo foi salvo com sucesso!");
            } else {
                alert("Erro ao salvar vôo");
            }
        } else {
            var dadosRetorno = await salvarVoo(voo);

            if(dadosRetorno) {
                alert("Vôo foi salvo com sucesso!");
            } else {
                alert("Erro ao salvar vôo");
            }
        }

        router.push("/voos");
    }
    
    return(
        <form action={handlerSalvar} className="bg-white rounded-2xl shadow-lg border border-blue-100 p-8 max-w-lg mx-auto">
            <div className="flex flex-col gap-5">
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Código do Vôo:</label>
                    <input name="codigoVoo" 
                    value={voo.codigoVoo}
                    onChange={(e)=> handlerChange('codigoVoo',e.target.value)}
                    placeholder="AP1000"
                    required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Avião:</label>
                    <select
                        name="aviaoId"
                        value={voo.aviaoId ?? ""}
                        onChange={(e)=> handlerAviao(e.target.value === "" ? null : Number(e.target.value))}
                        required
                        className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
                        <option value="">Selecione um avião</option>
                        {avioes.map(aviao => (
                            <option key={aviao.id} value={aviao.id ?? ""}>
                                {aviao.modelo} - {aviao.numeroSerie}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="flex flex-col gap-2">
                    <label className="text-sm font-medium text-blue-800">Pilotos:</label>
                    {pilotos.map(piloto => (
                        <label key={piloto.id} className="flex items-center gap-2 text-blue-900">
                            <input
                                type="checkbox"
                                checked={voo.pilotosIds.includes(piloto.id ?? 0)}
                                onChange={(e)=> handlerPilotos(
                                    e.target.checked
                                        ? [...voo.pilotosIds, piloto.id ?? 0]
                                        : voo.pilotosIds.filter(id => id !== piloto.id)
                                )}
                                className="h-4 w-4 rounded border-blue-300 text-blue-600 focus:ring-blue-500"/>
                            {piloto.canac} - {piloto.tipoPiloto}
                        </label>
                    ))}
                    {pilotos.length === 0 && (
                        <span className="text-sm text-blue-700 italic">Nenhum piloto cadastrado!</span>
                    )}
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Capacidade:</label>
                    <input name="capacidade"
                    type="number"
                    min="1"
                    value={voo.capacidade}
                    onChange={(e)=> handlerChange('capacidade',e.target.value)}
                    placeholder="100"
                    required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Assentos Disponíveis:</label>
                    <input name="assentosDisponiveis" 
                    type="number"
                    min="0"
                    max={voo.capacidade}
                    value={voo.assentosDisponiveis}
                    onChange={(e)=> handlerChange('assentosDisponiveis',e.target.value)}
                    placeholder="20"
                    required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Cidade de origem:</label>
                    <input name="cidadeOrigem" 
                    value={voo.cidadeOrigem}
                    onChange={(e)=> handlerChange('cidadeOrigem',e.target.value)}
                    placeholder="Floripa"
                    required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Cidade de destino:</label>
                    <input name="cidadeDestino"
                    value={voo.cidadeDestino}
                    onChange={(e)=> handlerChange('cidadeDestino',e.target.value)}
                    placeholder="São Paulo"
                    required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Data hora saída:</label>
                    <input name="dataHoraSaida"
                    value={voo.dataHoraSaida}
                    type="text" 
                    maxLength={19}
                    pattern="\d{2}/\d{2}/\d{4} \d{2}:\d{2}:\d{2}"
                    title="Use o formato dd/mm/aaaa hh:mm:ss"
                    onChange={(e)=> handlerChange('dataHoraSaida', mascararDataHora(e.target.value))}
                    placeholder="30/09/2026 13:00:00 (dd/mm/aaaa hh:mm:ss)"
                    required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Data hora chegada prevista:</label>
                    <input name="dataHoraChegadaPrevista"
                    value={voo.dataHoraChegadaPrevista}
                    type="text" 
                    maxLength={19}
                    pattern="\d{2}/\d{2}/\d{4} \d{2}:\d{2}:\d{2}"
                    title="Use o formato dd/mm/aaaa hh:mm:ss"
                    onChange={(e)=> handlerChange('dataHoraChegadaPrevista', mascararDataHora(e.target.value))}
                    placeholder="30/09/2026 15:00:00 (dd/mm/aaaa hh:mm:ss)"
                    required
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-blue-800">Data hora chegada:</label>
                    <input name="dataHoraChegada"
                    value={voo.dataHoraChegada}
                    type="text" 
                    maxLength={19}
                    pattern="\d{2}/\d{2}/\d{4} \d{2}:\d{2}:\d{2}"
                    title="Use o formato dd/mm/aaaa hh:mm:ss"
                    onChange={(e)=> handlerChange('dataHoraChegada', mascararDataHora(e.target.value))}
                    placeholder="30/09/2026 15:00:00 (dd/mm/aaaa hh:mm:ss)"
                    className="w-full rounded-lg border border-blue-200 px-4 py-2 text-blue-900 placeholder-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                </div>

                <div className="flex items-center justify-end gap-3 pt-2">
                    <Link href="/voos" className="text-sm font-semibold text-blue-600 hover:text-blue-800 px-4 py-2 rounded-lg transition-colors">Cancelar</Link>
                    <button type="submit" className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-5 py-2 rounded-lg transition-colors">Salvar</button>
                </div>
            </div>
        </form>
    );
}