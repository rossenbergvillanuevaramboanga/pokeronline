package it.prova.pokeronline.web.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/play")
public class PlayController {
	
	/*
	 * COMPRA CREDITO (ti invio di quanto aumentare il mio credito...
	 * lasciamo stare le considerazioni che emergono sul fatto che ci
	 *  vorrebbe un payment provider tipo Paypal).*/
	
	@PostMapping("/buy/{credito}")
	@ResponseStatus(HttpStatus.CREATED)
	public void compraCredito(@PathVariable(value ="credito", required = true) long credito) {
		
	}
	
	

}
