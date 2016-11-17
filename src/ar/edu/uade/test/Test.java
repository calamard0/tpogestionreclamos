package ar.edu.uade.test;

import java.util.List;
import java.util.Map;

import ar.edu.uade.dto.ClienteDTO;
import ar.edu.uade.dto.ReclamoTPromXOperadorDTO;
import ar.edu.uade.model.Cliente;
import ar.edu.uade.model.Reclamo;

public class Test {

	public static void main(String[] args) {
	
		/*Ranking clientes por cant reclamos*/
		System.out.println("Ranking clientes por cantidad de reclamos");
		List<ClienteDTO> listview = Cliente.getRankingClientesPorCantidadReclamos();
		for(ClienteDTO v:listview){
			System.out.println(v.toString());
		}
		System.out.println();
		
		/*cantidad de reclamos ingresados por mes*/
		System.out.println("Cantidad de reclamos ingresados por mes");
		for(int i=1;i<=12;i++){
			System.out.println("Mes "+ i + " cantidad de reclamos ingresados: " +Reclamo.getCantidadReclamosPorMes(i));
			
		}
		System.out.println();
		
		/*ranking tratamiento de reclamos*/
		System.out.println("Ranking tratamiento de reclamos");
		Map<String, Long> map = Reclamo.getRankingTratamientoReclamos();
		for (Map.Entry<String, Long> entry : map.entrySet()) {
			System.out.println("Estado " + entry.getKey() + ":  " + entry.getValue());
		}
		System.out.println();
		
		/*Tiempo promedio respuesta por responsable*/
		System.out.println("Tiempo promedio respuesta por responsable");
		List<ReclamoTPromXOperadorDTO> listaView = Reclamo.getTiempoPromedioRespuestaPorResp();
		for(ReclamoTPromXOperadorDTO v:listaView){
			System.out.println(v.toString());
		}
	}
}
