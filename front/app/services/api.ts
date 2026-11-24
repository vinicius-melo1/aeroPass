'use client'

import axios from "axios"
import Cookies from "js-cookie";


const api = axios.create({
    baseURL:'http://localhost:8080'
});

api.interceptors.request.use(
    (config) => {
        const token = Cookies.get("token");

        if(token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    }
);

api.interceptors.response.use(
    (response) => response,
    (error) => {
        const status = error.response?.status;
        const isLoginRequest = error.config?.url?.includes("/auth/login");

        if ((status === 401 || status === 403) && !isLoginRequest) {
            Cookies.remove("token");
            Cookies.remove("usuario");
            window.location.href = "/auth/login";
        }

        return Promise.reject(error);
    }
);
export default api;