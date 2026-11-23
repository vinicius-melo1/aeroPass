export function mascararDataHora(valor: string): string {
    const d = valor.replace(/\D/g, "").slice(0, 14);

    let r = d.slice(0, 2);
    if (d.length > 2)  r += "/" + d.slice(2, 4);
    if (d.length > 4)  r += "/" + d.slice(4, 8);
    if (d.length > 8)  r += " " + d.slice(8, 10);
    if (d.length > 10) r += ":" + d.slice(10, 12);
    if (d.length > 12) r += ":" + d.slice(12, 14);

    return r;
}