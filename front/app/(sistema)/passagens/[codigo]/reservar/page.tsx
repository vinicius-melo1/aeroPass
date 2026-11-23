"use client"

import { buscarListaFormasPagamento } from "@/app/services/formasPagamentoService";
import { consultaPassagemPorId, reservarPassagem } from "@/app/services/passagemService";
import { FormaPagamento } from "@/app/types/formaPagamento";
import { Passagem, ReservaRequest } from "@/app/types/passagem";
import Link from "next/link";
import { useParams, useRouter } from "next/navigation";
import { useState, useEffect } from "react";

export default function ReservarPassagem() {
    const { codigo } = useParams<{ codigo: string }>();
    const router = useRouter();

    const [passagem, setPassagem] = useState<Passagem | null>(null);
    const [formas, setFormas] = useState<FormaPagamento[]>([]);
    const [formasSelecionadas, setFormasSelecionadas] = useState<number[]>([]);

    useEffect(() => {
        carregarDados();
    }, []);

    const carregarDados = async () => {
        try {
            setPassagem(await consultaPassagemPorId(Number(codigo)));
            setFormas(await buscarListaFormasPagamento());
        } catch {
            alert("Erro ao carregar dados");
        }
    }

    const alternarForma = (id: number) => {
        setFormasSelecionadas(atual =>
            atual.includes(id) ? atual.filter(f => f !== id) : [...atual, id]
        );
    }

    const handlerReservar = async () => {
        if (formasSelecionadas.length === 0) {
            alert("Selecione ao menos uma forma de pagamento");
            return;
        }

        const request: ReservaRequest = { formasPagamentoIds: formasSelecionadas };

        try {
            await reservarPassagem(Number(codigo), request);
            alert("Passagem reservada com sucesso!");
            router.push(`/voos/${passagem?.vooId}/passagens`);
        } catch {
            alert("Erro ao reservar passagem");
        }
    }

    if (!passagem) {
        return <div className="px-4 py-8 text-blue-800">Carregando...</div>;
    }

    return (
        <div className="bg-blue-50 px-4 py-8">
            <div className="max-w-2xl mx-auto bg-white rounded-2xl shadow-lg border border-blue-100 p-6">
                <h1 className="text-2xl font-bold text-blue-900 mb-4">Reservar passagem</h1>

                <div className="mb-6 text-blue-800 space-y-1">
                    <p><span className="font-semibold">Voo:</span> {passagem.vooCodigo}</p>
                    <p><span className="font-semibold">Assento:</span> {passagem.codigoAssento}</p>
                    <p><span className="font-semibold">Valor:</span> R$ {passagem.valor}</p>
                </div>

                <p className="text-sm font-semibold text-blue-900 mb-2">Formas de pagamento</p>
                <div className="flex flex-col gap-2 mb-6">
                    {formas.map(forma => (
                        <label key={forma.id} className="flex items-center gap-2 text-blue-800">
                            <input
                                type="checkbox"
                                checked={formasSelecionadas.includes(forma.id!)}
                                onChange={() => alternarForma(forma.id!)}
                            />
                            {forma.tipoPagamento}
                        </label>
                    ))}
                </div>

                <div className="flex gap-4">
                    <button onClick={handlerReservar} className="bg-green-600 hover:bg-green-800 text-white font-semibold px-4 py-2 rounded-lg transition-colors">
                        Confirmar reserva
                    </button>
                    <Link href={`/voos/${passagem.vooId}/passagens`} className="bg-gray-500 hover:bg-gray-600 text-white font-semibold px-4 py-2 rounded-lg transition-colors">
                        Cancelar
                    </Link>
                </div>
            </div>
        </div>
    );
}