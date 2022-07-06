package rva.ctrls;

import java.util.Collection;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Proizvod;
import rva.repository.ProizvodRepository;
@CrossOrigin
@Api(tags = ("Proizvod CRUD operacije"))
@RestController
public class ProizvodRestController {
	private	JdbcTemplate jdbcTemplate;
	@Autowired
	private ProizvodRepository proizvodRepository;
	@ApiOperation(value="Vraća kolekciju svih proizvoda iz baze podataka")
	@GetMapping("proizvod")
	public Collection<Proizvod> getProizvodi() {
		return proizvodRepository.findAll();
	}
	@ApiOperation(value="Vraća konkretan proizvod na osnovu prosleđenog ID")
	@GetMapping("proizvod/{id}")
	public Proizvod getProizvod(@PathVariable("id") Integer id) {
		return proizvodRepository.getOne(id); 
	}
	@ApiOperation(value="Vraća kolekciju proizvoda na osnovu prosleđenog naziva kredita")
	@GetMapping("proizvodNaziv/{naziv}")
	public Collection<Proizvod> getProizvodiByNaziv(@PathVariable("naziv") String naziv) {
		return proizvodRepository.findByNazivContainingIgnoreCase(naziv);
	}
	@ApiOperation(value="Dodaje novi proizvod u bazu podataka")
	@PostMapping("proizvod")
	public ResponseEntity<Proizvod> insertProizvod(@RequestBody Proizvod proizvod) {
		if (!proizvodRepository.existsById(proizvod.getId())) {
			proizvodRepository.save(proizvod);
			return new ResponseEntity<Proizvod>(HttpStatus.OK);
		}
		return new ResponseEntity<Proizvod>(HttpStatus.CONFLICT);
	}
	
	@ApiOperation(value="Update-uje proizvod iz baze podataka")
	@PutMapping("proizvod")
	public ResponseEntity<Proizvod> updateProizvod(@RequestBody Proizvod proizvod) {
		if(proizvodRepository.existsById(proizvod.getId())) {
			proizvodRepository.save(proizvod);
			return new ResponseEntity<Proizvod>(HttpStatus.OK);

		}
		return new ResponseEntity<Proizvod>(HttpStatus.NO_CONTENT);
	}
	@ApiOperation(value="Briše proizvod iz baze podataka na osnovu prosleđenog ID")
	@DeleteMapping("proizvod/{id}")
	public ResponseEntity<Proizvod> deleteProizvod(@PathVariable("id") Integer id) {
		if(proizvodRepository.existsById(id)) {
			proizvodRepository.deleteById(id);

			if (id == -100)
			{
				jdbcTemplate.execute("INSERT INTO proizvod (id, naziv, proizvodjac) values (-100, 'Test naziv', 1)");
			}
			return new ResponseEntity<Proizvod>(HttpStatus.OK);
		}
		return new ResponseEntity<Proizvod>(HttpStatus.NO_CONTENT);
}
	
}
