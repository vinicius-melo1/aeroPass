import { Voo } from "@/app/types/voo";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { logout } from "./authSlice";

const STORAGE_KEY = "voosVistos";
const MAX_VOOS_VISTOS = 10;

const carregarVoosVistos = (): Voo[] => {
    if (typeof window === "undefined") return [];
    try {
        const salvo = localStorage.getItem(STORAGE_KEY);
        return salvo ? (JSON.parse(salvo) as Voo[]) : [];
    } catch {
        return [];
    }
};

const salvarVoosVistos = (voos: Voo[]) => {
    try {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(voos));
    } catch {
        // storage cheio ou indisponível: ignora
    }
};

interface VoosVistosState {
    voos: Voo[];
}

const initialState: VoosVistosState = {
    voos: carregarVoosVistos(),
};

const voosVistosSlice = createSlice({
    name: "voosVistos",
    initialState,
    reducers: {
        registrarVooVisto: (state, action: PayloadAction<Voo>) => {
            const voo = action.payload;

            // remove o voo se já estiver na lista e coloca no topo
            state.voos = [
                voo,
                ...state.voos.filter((v) => v.id !== voo.id),
            ].slice(0, MAX_VOOS_VISTOS);

            salvarVoosVistos(state.voos);
        },
        removerVooVisto: (state, action: PayloadAction<number>) => {
            state.voos = state.voos.filter((v) => v.id !== action.payload);
            salvarVoosVistos(state.voos);
        },
        limparVoosVistos: (state) => {
            state.voos = [];
            localStorage.removeItem(STORAGE_KEY);
        },
    },
    extraReducers: (builder) => {
        builder.addCase(logout, (state) => {
            state.voos = [];
            localStorage.removeItem(STORAGE_KEY);
        });
    },
});

export const { registrarVooVisto, removerVooVisto, limparVoosVistos } =
    voosVistosSlice.actions;
export default voosVistosSlice.reducer;