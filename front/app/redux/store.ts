import { configureStore, UnknownAction } from "@reduxjs/toolkit";
import authReducer from "../redux/slices/authSlice";
import voosVistosReducer from "../redux/slices/voosVistosSlice"

export const store = configureStore({
    reducer:{
        auth: authReducer,
        voosVistos: voosVistosReducer,
    }
});



export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;


