import { FormaPagamento } from "../types/formaPagamento";
import api from "./api";

export async function buscarListaFormasPagamento(): Promise<FormaPagamento[]> {
    const dados = await api.get<FormaPagamento[]>("/forma-pagamento/listar");
    if (dados.status == 200) {
        return dados.data;
    }
    return [];
}