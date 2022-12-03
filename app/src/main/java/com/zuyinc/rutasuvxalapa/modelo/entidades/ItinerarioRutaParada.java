/*
* NOTA IMPORTANTE SOBRE ESTA CLASE SI SE LLEGARA A UTILIZAR GOOGLE MAPAS PARA TRAZAR TODAS LAS RUTAS
* Y SUS PARADAS MUY PROBABLEMENTE ESTA CLASE SERIA LA CLAVE Y ES LA QUE SE UTILIZARIA PARA HACER EL
* TRAZADO SOLO SE USARIA EL UIDRUTA PARA TRAER EL NUMERO DE RUTA Y DE AHI SE FILTRARIA TODOO EL REGISTRO
* DE ESTA CLASE QUE COINCIDAN CON ESE NUMERO POSTERIORMENTE SE TRAZARIAN TODOS LOS PUNTOS DE LAS PARADAS
* QUE COINCIDAN CON EL UIDRUTA DE LOS PUNTOS QUE SE FILTRARON Y SE MARCARIAN TODOS LAS PARADAS QUE ESTEN
* ASOCIADAS A ESE IDENTIFICADOR DE RUTA
* NO SE USARIA LOS PUNTOS DE INICIO Y FINAL DE LA CLASE DE RUTA (OJO SI ES QUE SE QUIERE TRAZAR TODOS
* LOS PUNTOS DE LAS PARADAS) (SINO SIMPLEMENTE UTILIZAR LOS PUNTOS DE INICIO Y FIN DE LA RUTA)
* (ES SOLO UNA IDEA DE MOMENTO SIN PROBAR, SINO SIMPLEMENTE USAR LA DIRECCION UIDIMAGENRUTA Y PEGAR EL MAPA
* SIN PARADAS NI RUTA)
*
* (CUANDO SE ESCRIBIERON ETAS NOTAS NO SE SABIAN TRAZAR VARIOS PUNTOS EN RUTA DE GOOGLE MAPS POR SI LLEGARA
* A SER SENCILLO Y APRENDIERA XD) 18/09/2022
*/

package com.zuyinc.rutasuvxalapa.modelo.entidades;

public class ItinerarioRutaParada {

    private String uidItinerarioRutaParada;
    private String uidRuta;
    private String uidParada;

    public ItinerarioRutaParada() {
    }

    public ItinerarioRutaParada(String uidRuta, String uidParada) {
        this.uidRuta = uidRuta;
        this.uidParada = uidParada;
    }

    public String getUidItinerarioRutaParada() {
        return uidItinerarioRutaParada;
    }

    public String getUidRuta() {
        return uidRuta;
    }

    public String getUidParada() {
        return uidParada;
    }

    public void setUidItinerarioRutaParada(String uidItinerarioRutaParada) {
        this.uidItinerarioRutaParada = uidItinerarioRutaParada;
    }

    public void setUidRuta(String uidRuta) {
        this.uidRuta = uidRuta;
    }

    public void setUidParada(String uidParada) {
        this.uidParada = uidParada;
    }
}
