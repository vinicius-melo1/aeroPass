import { useSelector } from "react-redux";
import { Role } from "../types/auth";

export function useAuth() {
    const usuario = useSelector((state: any) => state.auth.usuario);
    const role = usuario?.role as Role | undefined;

    return {
        usuario,
        role,
        ehAdmin: role === "ROLE_ADMIN",
        ehUser: role === "ROLE_USER",
        ehCliente: role === "ROLE_CLIENTE",
        temRole: (...roles: Role[]) => !!role && roles.includes(role),
    };
}